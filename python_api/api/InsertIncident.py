from api.BaseApiCall import BaseApiCall
from flask import request
from utils.DBConnection import DBConnection
from utils.ApiUtils import ApiUtils


class InsertIncident(BaseApiCall):
    PARAMS = {"title": 10, "description": None, "lat": None, "lng": None, "priority": None}

    title = None
    description = None
    lat = None
    lng = None
    priority = None

    def get_parameters(self):
        self.title = ApiUtils.get_param(request.args.get("title"), self.PARAMS["title"])
        self.description = ApiUtils.get_param(request.args.get("description"), self.PARAMS["description"])
        self.lat = ApiUtils.get_param(request.args.get("lat"), self.PARAMS["lat"])
        self.lng = ApiUtils.get_param(request.args.get("lng"), self.PARAMS["lng"])
        self.priority = ApiUtils.get_param(request.args.get("priority"), self.PARAMS["priority"])

    def get_results(self):
        if self.title is None or self.description is None or self.lat is None or self.lng is None or self.priority is None:
            return {"message": "Some of the mandatory parameters are missing. Please consult our API documentation"}

        DBConnection.execute_update('insert ignore into incidents (title, description, lat, lng, priority) '
                                    + 'values '
                                    + '("' + self.title + '", "' + self.description + '", ' + str(self.lat) + ', '
                                    + str(self.lng) + ', ' + str(self.priority) + ')')
        return {"message": "successfully inserted"}

