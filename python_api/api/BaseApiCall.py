from flask_restful import Resource
import json


class BaseApiCall(Resource):
    def get(self):
        self.get_parameters()
        result = self.get_results()
        return result

    def get_parameters(self):
        pass

    def get_results(self):
        pass