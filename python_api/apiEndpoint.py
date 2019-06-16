from flask import Flask, request
from flask_restful import Resource, Api
from api.GetIncidents import GetIncidents
from api.InsertIncident import InsertIncident

app = Flask("Global Incidents")
api = Api(app)

api.add_resource(GetIncidents, '/get-incidents')
api.add_resource(InsertIncident, '/insert-incidents')

if __name__ == '__main__':
    app.run(port='5002')
