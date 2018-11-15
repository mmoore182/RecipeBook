/**
 * Procedure data access object
 */

package Model;

import static Model.RecipeDAO.removeSpaces;

import Util.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProcedureDAO {

  /**
   * Inserts procedure into table using SQL INSERT.
   */
  public static void insertProcedure(String recipeName, Integer step, String procedure)
      throws SQLException, ClassNotFoundException {
    String sql =
        "insert into " + removeSpaces(recipeName) + "proc(step, proc) values('" + step
            + "', '" + procedure + "')";
    try {
      DBUtil.dbExecuteQuery(sql);
    } catch (SQLException e) {
      System.out.println("Error adding procedure");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Removes procedure from table using SQL DELETE & SQL WHERE.
   */
  public static void deleteProcedure(String tableName, String procedure)
      throws SQLException, ClassNotFoundException {
    String sql = "delete from " + tableName + "proc where proc = '" + procedure + "' ";
    try {
      DBUtil.dbExecuteQuery(sql);
    } catch (SQLException e) {
      System.out.println("Error deleting procedure");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Removes procedure table when recipe is deleted using SQL DROP.
   */
  public static void deleteProcedureTable(String tableName)
      throws SQLException, ClassNotFoundException {
    String sql = "drop table if exists " + tableName + "proc";
    try {
      DBUtil.dbExecuteQuery(sql);
    } catch (SQLException e) {
      System.out.print("Exception removing procedure table");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Returns ordered procedure list for given recipe.
   */
  public static ObservableList<Procedure> getProcedures(String tableName)
      throws SQLException, ClassNotFoundException {
    String sql = "select * from " + removeSpaces(tableName) + "proc ORDER BY step ASC";
    try {
      ResultSet resSet = DBUtil.dbExecute(sql);
      ObservableList<Procedure> procList = getProcedureObjects(resSet);
      return procList;
    } catch (SQLException e) {
      System.out.println("Error getting procedures from table");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Returns procedure table as list of objects.
   */
  private static ObservableList<Procedure> getProcedureObjects(ResultSet resSet)
      throws ClassNotFoundException, SQLException {
    try {
      ObservableList<Procedure> procList = FXCollections.observableArrayList();
      while (resSet.next()) {
        Procedure proc = new Procedure();
        proc.setProcedureProperty(resSet.getString("proc"));
        proc.setStepProperty(resSet.getInt("step"));
        procList.add(proc);
      }
      return procList;
    } catch (SQLException e) {
      System.out.println("Error while fetching data from procedure DB" + e);
      e.printStackTrace();
      throw e;
    }
  }
}
