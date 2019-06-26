from api.BaseApiCall import BaseApiCall
from utils.DBConnectionSingleton import DBConnectionSingleton
import threading


class GetGroupedDescriptions(BaseApiCall):
    MAX_THREADS_NO = 5

    def get_parameters(self):
        pass

    def get_results(self):
        results = []
        to_return = dict()
        threads = []

        results = DBConnectionSingleton.execute_query("select id, description from incidents")

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
            if (r['description'] in to_return) is False:
                to_return[r['description']] = 1
            else:
                to_return[r['description']] = to_return[r['description']] + 1