from flask import Flask, request
from flask_restful import Resource, Api
from api.GetIncidents import GetIncidents
from api.InsertIncident import InsertIncident
from api.GetItem import GetItem
from api.UpdateStatus import UpdateStatus
from flask_cors import CORS

app = Flask("Global Incidents")
CORS(app)
api = Api(app)

api.add_resource(GetIncidents, '/get-incidents')
api.add_resource(GetItem, '/get-item')
api.add_resource(InsertIncident, '/insert-incident')
api.add_resource(UpdateStatus, '/update-status')

if __name__ == '__main__':
    app.run(port='5002')
