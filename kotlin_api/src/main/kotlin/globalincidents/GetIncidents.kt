package globalincidents

import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.*

class GetIncidents(name: String) {

    fun GetResults(): String {
        val connectionProps = Properties()
        connectionProps.put("user", "root")
        connectionProps.put("password", "root")
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance()
            val conn = DriverManager.getConnection(
                "jdbc:" + "mysql" + "://" +
                        "127.0.0.1" +
                        ":" + "3306" + "/" +
                        "",
                connectionProps)

            var stmt: Statement? = null
            var resultset: ResultSet? = null

            try {
                stmt = conn!!.createStatement()
                resultset = stmt!!.executeQuery("SHOW DATABASES;")

                if (stmt.execute("SHOW DATABASES;")) {
                    resultset = stmt.resultSet
                }

                while (resultset!!.next()) {
                    println(resultset.getString("Database"))
                }
            } catch (ex: SQLException) {
                // handle any errors
                ex.printStackTrace()
            }

        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
        }


        return "Test"
    }
}