import mysql.connector
from utils.ConfigurationLoader import ConfigurationLoader

connection = None


class DBConnection:

    def __init__(self):
        pass

    @staticmethod
    def get_instance():
        global connection

        if connection is None or connection.is_connected() is False:
            connection = mysql.connector.connect(user=ConfigurationLoader.db_user,
                                                 password=ConfigurationLoader.db_password,
                                                 host=ConfigurationLoader.db_host,
                                                 database=ConfigurationLoader.db_name)
            return connection
        else:
            return connection

    @staticmethod
    def execute_query(a_query):
        cursor = DBConnection.get_instance().cursor(dictionary=True)
        cursor.execute(a_query)
        results = cursor.fetchall()
        cursor.close()
        return results

    @staticmethod
    def execute_update(a_query):
        cursor = DBConnection.get_instance().cursor()
        cursor.execute(a_query)
        DBConnection.get_instance().commit()
        row_count = cursor.rowcount
        cursor.close()
        return row_count
