package utils

import org.json.JSONArray
import org.json.JSONObject
import java.sql.*
import java.util.*

object DBConnection {
    lateinit var mConnection: Connection

    init {
        val connectionProps = Properties()
        connectionProps.put("user", "root")
        connectionProps.put("password", "")
        try {
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance()
            mConnection = DriverManager.getConnection(
                "jdbc:" + "mysql" + "://" + "127.0.0.1" + ":" + "3306" + "/" + "global_incidents", connectionProps)
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
        }
    }

    fun executeQuery(aQuery: String): JSONArray {
        val stmt: Statement
        val resultSet: ResultSet

        try {
            stmt = mConnection.createStatement()
            resultSet = stmt.executeQuery(aQuery)
            return convertToJSON(resultSet)
        } catch (ex: SQLException) {
            ex.printStackTrace()
            return JSONArray()
        }
    }

    fun executeUpdate(aQuery: String) {
        val stmt: Statement

        try {
            stmt = mConnection.createStatement()
            stmt.execute(aQuery)
            stmt.close()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
    }

    @Throws(Exception::class)
    fun convertToJSON(resultSet: ResultSet): JSONArray {
        val jsonArray = JSONArray()
        while (resultSet.next()) {
            val totalRows = resultSet.metaData.columnCount
            val obj = JSONObject()
            for (i in 0 until totalRows) {
                obj.put(resultSet.metaData.getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1))
            }

            jsonArray.put(obj)
        }

        resultSet.statement.close()
        return jsonArray
    }
}