from api.BaseApiCall import BaseApiCall
from flask import request
from utils.DBConnection import DBConnection
from utils.ApiUtils import ApiUtils


class GetIncidents(BaseApiCall):
    PARAMS = {"limit": 10, "filter": None, "min_lat": None, "max_lat": None, "min_lng": None, "max_lng": None}

    limit = 10
    filter = None
    min_lat = None
    max_lat = None
    min_lng = None
    max_lng = None

    def get_parameters(self):
        self.limit = ApiUtils.get_param(request.args.get("limit"), self.PARAMS["limit"])
        self.filter = ApiUtils.get_param(request.args.get("filter"), self.PARAMS["filter"])
        self.min_lat = ApiUtils.get_param(request.args.get("min_lat"), self.PARAMS["min_lat"])
        self.max_lat = ApiUtils.get_param(request.args.get("max_lat"), self.PARAMS["max_lat"])
        self.min_lng = ApiUtils.get_param(request.args.get("min_lng"), self.PARAMS["min_lng"])
        self.max_lng = ApiUtils.get_param(request.args.get("max_lng"), self.PARAMS["max_lng"])

    def get_results(self):
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

        return DBConnection.execute_query("select id, title, description, DATE_FORMAT(published_date, \"%M %d %Y\") "
                                          + "as published_date from incidents where 1 " + filter_clause + min_lat_clause
                                          + max_lat_clause + min_lng_clause + max_lng_clause + limit_clause)

