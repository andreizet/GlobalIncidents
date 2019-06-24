import mysql.connector


class DBConnection2:
    connection = None
    
    def __init__(self):
        if self.connection is None or self.connection.is_connected() is False:
            self.connection = mysql.connector.connect(user='root', password='root', host='localhost',
                                                      database='global_incidents')

    def execute_query(self, a_query):
        cursor = self.connection.cursor(dictionary=True)
        cursor.execute(a_query)
        results = cursor.fetchall()
        cursor.close()
        return results

    def execute_update(self, a_query):
        cursor = self.connection.cursor()
        cursor.execute(a_query)
        self.connection.commit()
        row_count = cursor.rowcount
        cursor.close()
        return row_count