package utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.Properties;

public class DBConnection {
  static Connection mInstance = null;

  public static Connection GetInstance() {
    if (DBConnection.mInstance == null) {
      Properties connectionProps = new Properties();
      connectionProps.put("user", "root");
      connectionProps.put("password", "root");
      connectionProps.put("useJDBCCompliantTimezoneShift", "true");
      connectionProps.put("useLegacyDatetimeCode", "false");
      connectionProps.put("serverTimezone", "UTC");

      try {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        DBConnection.mInstance = DriverManager.getConnection("jdbc:" + "mysql" + "://" + "127.0.0.1" + ":" + "3306" + "/" + "global_incidents", connectionProps);
      } catch (Exception ex) {
        // handle any errors
        ex.printStackTrace();
      }
    }

    return mInstance;
  }

  public static JSONArray ExecuteQuery(String aQuery) {
    Statement stmt;
    ResultSet resultSet;

    try {
      stmt = DBConnection.GetInstance().createStatement();
      resultSet = stmt.executeQuery(aQuery);

      return DBConnection.convertToJSON(resultSet);
    } catch (Exception ex) {
      ex.printStackTrace();
      return new JSONArray();
    }
  }

  public static void ExecuteUpdate(String aQuery) {
    Statement stmt;
    try {
      stmt = DBConnection.GetInstance().createStatement();
      stmt.execute(aQuery);

      stmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static JSONArray convertToJSON(ResultSet resultSet) throws Exception {
    JSONArray jsonArray = new JSONArray();
    while (resultSet.next()) {
      int total_rows = resultSet.getMetaData().getColumnCount();
      JSONObject obj = new JSONObject();
      for (int i = 0; i < total_rows; i++) {
        obj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));
      }

      jsonArray.put(obj);
    }

    resultSet.getStatement().close();

    return jsonArray;
  }
}
