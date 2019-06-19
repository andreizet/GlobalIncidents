import mysql.connector

connection = None


class DBConnection:

    def __init__(self):
        pass

    @staticmethod
    def get_instance():
        global connection

        if connection is None:
            connection = mysql.connector.connect(user='root', password='',
                                                 host='localhost',
                                                 database='global_incidents')
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
