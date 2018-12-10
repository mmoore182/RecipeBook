package Util;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

  private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  /** JDBC DRIVER. **/
  private static Connection connection = null;
  // SQL DB URL Should not be changed.
  private static final String DB_URL =
      "jdbc:mysql://localhost:3306/recipedb?autoReconnect=true&"
          + "useSSL=false&createDatabaseIfNotExist=true";
  private static final String USER = "root";       /** SQL USERNAME. **/
  private static final String PASS = "password";   /** SQL PASSWORD. **/

  /**
   * Opens DB connection using JDBC driver.
   */
  public static void dbConnect() throws SQLException, ClassNotFoundException {
    try {
      Class.forName(JDBC_DRIVER);
    } catch (ClassNotFoundException e) {
      System.out.println("Could not connect to driver");
      e.printStackTrace();
      throw e;
    }
    System.out.println("JDBC Driver connected");
    try {
      connection = DriverManager.getConnection(DB_URL, USER, PASS);
    } catch (SQLException e) {
      System.out.println("Could not connect to DB. Connection failed" + e);
      throw e;
    }
  }

  /**
   * Closes the connection to DB.
   */
  public static void dbDisconnect() throws SQLException {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
      }
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * SQL Query for insert, delete, update, etc.
   */
  public static void dbExecuteQuery(String sqlStmt) throws SQLException, ClassNotFoundException {
    Statement stmt = null;
    try {
      dbConnect();
      if (connection != null) {
        stmt = connection.createStatement();
        stmt.executeUpdate(sqlStmt);
      } else {
        System.out.println("Connection is null");
      }
    } catch (SQLException e) {
      System.out.println("Problem executing dbExecuteQuery" + e);
      throw e;
    } finally {
      if (stmt != null) {
        stmt.close();
      }
      dbDisconnect();
    }
  }

  /**
   * SQL query for retrieving data from db (select).
   */
  public static ResultSet dbExecute(String sqlQuery) throws SQLException, ClassNotFoundException {
    Statement stmt = null;
    ResultSet rs = null;
    CachedRowSetImpl crs = null;

    try {
      dbConnect();
      stmt = connection.createStatement();
      rs = stmt.executeQuery(sqlQuery);
      crs = new CachedRowSetImpl();
      crs.populate(rs);
    } catch (SQLException e) {
      System.out.println("Error in dbExecute Operation" + e);
      throw e;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (stmt != null) {
        stmt.close();
      }
      dbDisconnect();
    }
    return crs;
  }
}
