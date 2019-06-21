from api.BaseApiCall import BaseApiCall
from flask import request
from utils.DBConnectionSingleton import DBConnection
from utils.DbConnection import DBConnection2
from utils.ApiUtils import ApiUtils
from utils.Constants import Constants
import threading
import time

class GetIncidents(BaseApiCall):
    MAX_THREADS_NO = 5

    limit = Constants.API_LIMIT['default']
    filter = Constants.API_FILTER['default']
    min_lat = Constants.API_MIN_LAT['default']
    max_lat = Constants.API_MAX_LAT['default']
    min_lng = Constants.API_MIN_LNG['default']
    max_lng = Constants.API_MAX_LNG['default']

    def get_parameters(self):
        self.limit = ApiUtils.get_param(request.args, Constants.API_LIMIT)
        self.filter = ApiUtils.get_param(request.args, Constants.API_FILTER)
        self.min_lat = ApiUtils.get_param(request.args, Constants.API_MIN_LAT)
        self.max_lat = ApiUtils.get_param(request.args, Constants.API_MAX_LAT)
        self.min_lng = ApiUtils.get_param(request.args, Constants.API_MIN_LNG)
        self.max_lng = ApiUtils.get_param(request.args, Constants.API_MAX_LNG)

    def get_results(self):
        start = time.time()
        limit_clause = ""
        if self.limit is not None:
            limit_clause = "limit " + self.limit

        filter_clause = ""
        if self.filter is not None:
            filter_clause = " and title like '%" + self.filter + "%' or description like '%" + self.filter + "%'"

        min_lat_clause = ""
        if self.min_lat is not None:
            min_lat_clause = " and lat >= " + self.min_lat

        max_lat_clause = ""
        if self.max_lat is not None:
            max_lat_clause = " and lat <= " + self.max_lat

        min_lng_clause = ""
        if self.min_lng is not None:
            min_lng_clause = " and lng >= " + self.min_lng

        max_lng_clause = ""
        if self.max_lng is not None:
            max_lng_clause = " and lng <= " + self.max_lng

        query = "select id, title, description, DATE_FORMAT(published_date, \"%M %d %Y\") as published_date, " \
                "CAST(lat AS CHAR) as lat, CAST(lng AS CHAR) as lng, status, confirmations, priority from incidents " \
                "where 1 "

        self.limit = int(self.limit)
        if self.limit >= 1000:
            results = []
            threads = []
            new_limit = int(self.limit/self.MAX_THREADS_NO)
            for i in range(0, self.MAX_THREADS_NO):
                if i is not 0:
                    offset = i * new_limit
                else:
                    offset = 0
                limit_clause = "limit " + str(new_limit) + " offset " + str(offset)
                print(query + filter_clause + min_lat_clause + max_lat_clause + min_lng_clause
                                                                              + max_lng_clause + limit_clause)
                th = threading.Thread(target=self.get_res, args=(query + filter_clause + min_lat_clause
                                                                              + max_lat_clause + min_lng_clause
                                                                              + max_lng_clause + limit_clause, results))
                th.start()
                threads.append(th)
            for thread in threads:
                thread.join()

            end = time.time()
            print(end - start)
            return results
        else:
            return DBConnection.execute_query(query + filter_clause + min_lat_clause
                                              + max_lat_clause + min_lng_clause + max_lng_clause + limit_clause)

    def get_res(self, query, results):
        connection = DBConnection2()
        res = connection.execute_query(query)
        results.extend(res)
