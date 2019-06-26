from api.BaseApiCall import BaseApiCall
from flask import request
from utils.DBConnectionSingleton import DBConnectionSingleton
from utils.ApiUtils import ApiUtils
from utils.Constants import Constants


class InsertIncident(BaseApiCall):
    title = None
    description = None
    lat = None
    lng = None
    priority = None

    def get_parameters(self):
        self.title = ApiUtils.get_param(request.args, Constants.API_TITLE)
        self.description = ApiUtils.get_param(request.args, Constants.API_DESCRIPTION)
        self.lat = ApiUtils.get_param(request.args, Constants.API_LAT)
        self.lng = ApiUtils.get_param(request.args, Constants.API_LNG)
        self.priority = ApiUtils.get_param(request.args, Constants.API_PRIORITY)

    def run(self):
        if self.title is None or self.description is None or self.lat is None or self.lng is None or self.priority is None:
            return {"message": "Some of the mandatory parameters are missing. Please consult our API documentation"}

        DBConnectionSingleton.execute_update('insert ignore into incidents (title, description, lat, lng, priority) '
                                    + 'values '
                                    + '("' + self.title + '", "' + self.description + '", ' + str(self.lat) + ', '
                                    + str(self.lng) + ', ' + str(self.priority) + ')')
        return {"message": "successfully inserted"}

