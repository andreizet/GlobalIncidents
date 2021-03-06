from flask import Flask, request, g, make_response
from flask_cors import CORS
from flask_restful import Resource, Api
from api.GetIncidents import GetIncidents
from api.InsertIncident import InsertIncident
from api.GetItem import GetItem
from api.UpdateStatus import UpdateStatus
from api.GetGroupedDescriptions import GetGroupedDescriptions
from api.GetGroupedCountries import GetGroupedCountries
from pyinstrument import Profiler
from utils.ConfigurationLoader import ConfigurationLoader

# Load configuration
ConfigurationLoader.load()

app = Flask("Global Incidents")
CORS(app)
api = Api(app)

api.add_resource(GetIncidents, '/get-incidents')
api.add_resource(GetItem, '/get-item')
api.add_resource(InsertIncident, '/insert-incident')
api.add_resource(UpdateStatus, '/update-status')
api.add_resource(GetGroupedDescriptions, '/grouped-descriptions')
api.add_resource(GetGroupedCountries, '/grouped-countries')

@app.before_request
def before_request():
    if "profile" in request.args:
        g.profiler = Profiler()
        g.profiler.start()


@app.after_request
def after_request(response):
    if not hasattr(g, "profiler"):
        return response
    g.profiler.stop()
    output_html = g.profiler.output_html()
    return make_response(output_html)


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5002)
