from api.BaseApiCall import BaseApiCall
from flask import request
from utils.DBConnection import DBConnection
from utils.ApiUtils import ApiUtils


class GetItem(BaseApiCall):
    PARAMS = {"id": None}

    id = None

    def get_parameters(self):
        self.id = ApiUtils.get_param(request.args.get("id"), self.PARAMS["id"])

    def get_results(self):
        if self.id is None:
            return {"message": "Some of the mandatory parameters are missing. Please consult our API documentation"}

        id_clause = ""
        if self.id is not None:
            id_clause = " and id=" + self.id

        return DBConnection.execute_query("select id, title, description, DATE_FORMAT(published_date, \"%M %d %Y\") "
                                          + "as published_date" + " from incidents where 1 " + id_clause)

