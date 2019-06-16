from flask import Flask, request
from flask_restful import Resource, Api
from api.GetIncidents import GetIncidents
from api.InsertIncident import InsertIncident
from api.GetItem import GetItem
from api.UpdateStatus import UpdateStatus

app = Flask("Global Incidents")
api = Api(app)

api.add_resource(GetIncidents, '/get-incidents')
api.add_resource(GetItem, '/get-item')
api.add_resource(InsertIncident, '/insert-incidents')
api.add_resource(UpdateStatus, '/update-status')

if __name__ == '__main__':
    app.run(port='5002')
