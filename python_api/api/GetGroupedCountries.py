from api.BaseApiCall import BaseApiCall
from utils.DBConnectionSingleton import DBConnectionSingleton
import threading


class GetGroupedCountries(BaseApiCall):
    MAX_THREADS_NO = 5

    def get_parameters(self):
        pass

    def run(self):
        results = []
        to_return = dict()
        threads = []

        results = DBConnectionSingleton.execute_query("select id, title from incidents")

        limit = int(len(results) / self.MAX_THREADS_NO)
        for i in range(0, self.MAX_THREADS_NO):
            thread_res = results[i * limit: limit * (i + 1)]

            th = threading.Thread(target=self.group_results, args=(thread_res, to_return))
            th.start()
            threads.append(th)
        for thread in threads:
            thread.join()

        return to_return

    def group_results(self, results, to_return):
        for r in results:
            country = r['title'].split(",")[-1].strip()

            if (country in to_return) is False:
                to_return[country] = 1
            else:
                to_return[country] = to_return[country] + 1