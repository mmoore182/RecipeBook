/**
 * Ingredient data access object
 */

package Model;

import static Model.RecipeDAO.removeSpaces;

import Util.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class IngredientDAO {

  /**
   * Inserts ingredient & ingredient amount into ingredient table using SQL INSERT.
   */
  public static void insertIngredient(String recipeName, String ingredient, String amount)
      throws SQLException, ClassNotFoundException {
    String sql =
        "insert into " + removeSpaces(recipeName) + "(ingredients, amount) values('" + ingredient
            + "', '" + amount + "')";
    try {
      DBUtil.dbExecuteQuery(sql);
    } catch (SQLException e) {
      System.out.println("Error adding ingredient");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Removes ingredient & ingredient amount from ingredient table using SQL DELETE & SQL WHERE.
   */
  public static void deleteIngredient(String tableName, String ingredient)
      throws SQLException, ClassNotFoundException {
    String sql = "delete from " + tableName + " where ingredients = '" + ingredient + "' ";
    try {
      DBUtil.dbExecuteQuery(sql);
    } catch (SQLException e) {
      System.out.println("Error deleting ingredient");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Removes ingredient table when recipe is deleted using SQL DROP.
   */
  public static void deleteIngredientTable(String tableName)
      throws SQLException, ClassNotFoundException {
    String sql = "drop table if exists " + tableName;
    try {
      DBUtil.dbExecuteQuery(sql);
    } catch (SQLException e) {
      System.out.print("Exception removing ingredient table");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Returns ingredients from ingredient table from user selection using SQL SELECT.
   */
  public static ObservableList<Ingredient> getIngredients(String tableName)
      throws SQLException, ClassNotFoundException {
    String sql = "select * from " + removeSpaces(tableName);
    try {
      ResultSet resSet = DBUtil.dbExecute(sql);
      ObservableList<Ingredient> ingredList = getIngredientObjects(resSet);
      return ingredList;
    } catch (SQLException e) {
      System.out.println("Error getting ingredients from table");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Returns list of ingredient objects.
   */
  private static ObservableList<Ingredient> getIngredientObjects(ResultSet resSet)
      throws ClassNotFoundException, SQLException {
    try {
      ObservableList<Ingredient> ingredList = FXCollections.observableArrayList();
      while (resSet.next()) {
        Ingredient ing = new Ingredient();
        ing.setIngredientProperty(resSet.getString("ingredients"));
        ing.setAmountProperty(resSet.getString("amount"));
        ingredList.add(ing);
      }
      return ingredList;
    } catch (SQLException e) {
      System.out.println("Error while fetching data from ingredient DB" + e);
      e.printStackTrace();
      throw e;
    }
  }

}
