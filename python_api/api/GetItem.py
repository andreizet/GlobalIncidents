from api.BaseApiCall import BaseApiCall
from flask import request
from utils.DBConnectionSingleton import DBConnectionSingleton
from utils.ApiUtils import ApiUtils
from utils.Constants import Constants


class GetItem(BaseApiCall):
    id = None

    def get_parameters(self):
        self.id = ApiUtils.get_param(request.args, Constants.API_ID)

    def get_results(self):
        if self.id is None:
            return {"message": "Some of the mandatory parameters are missing. Please consult our API documentation"}

        id_clause = ""
        if self.id is not None:
            id_clause = " and id=" + str(self.id)

        return DBConnectionSingleton.execute_query("select id, title, description, DATE_FORMAT(published_date, \"%M %d %Y\") "
                                          + "as published_date" + " from incidents where 1 " + id_clause)

