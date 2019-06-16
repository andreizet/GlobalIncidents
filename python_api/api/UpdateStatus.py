from api.BaseApiCall import BaseApiCall
from flask import request
from utils.DBConnection import DBConnection
from utils.ApiUtils import ApiUtils


class UpdateStatus(BaseApiCall):
    PARAMS = {"id": None, "status": None}

    id = None
    status = None

    def get_parameters(self):
        self.id = ApiUtils.get_param(request.args.get("id"), self.PARAMS["id"])
        self.status = ApiUtils.get_param(request.args.get("status"), self.PARAMS["status"])

    def get_results(self):
        if self.id is None or self.status is None:
            return {"message": "Some of the mandatory parameters are missing. Please consult our API documentation"}

        id_clause = ""
        if self.id is not None:
            id_clause = " and id=" + self.id

        status_clause = ""
        if self.status is not None:
            status_clause = " status=" + self.status

        DBConnection.execute_update("update incidents set " + status_clause + " where 1 " + id_clause)
        return {"message": "successfully updated"}

