from flask_restful import Resource
from datetime import datetime
import time

class BaseApiCall(Resource):
    def get(self):
        start_time = time.time_ns() // 1000000
        self.get_parameters()
        result = self.get_results()
        stop_time = time.time_ns() // 1000000

        print("Time: " + str(stop_time-start_time))

        return result

    def get_parameters(self):
        pass

    def get_results(self):
        pass
