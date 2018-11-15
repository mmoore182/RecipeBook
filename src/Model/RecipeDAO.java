/**
 * Recipe data access object
 */

package Model;

import Util.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RecipeDAO {

  /**
   * Inserts recipe into recipetbl from user input in text field. Create corresponding ingredient
   * table & procedure table.
   */
  public static void insertRecipe(String recipeName) throws SQLException, ClassNotFoundException {
    String sql = "insert into recipetbl(recipeName) values('" + recipeName + "')";
    try {
      DBUtil.dbExecuteQuery(sql);
      createIngredientTable(recipeName);
      createProcedureTable(recipeName);
    } catch (SQLException e) {
      System.out.println("Exception inserting recipe data");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Creates ingredient table based on recipe name.
   */
  public static void createIngredientTable(String recipeName)
      throws SQLException, ClassNotFoundException {
    System.out.println("Creating table: " + removeSpaces(recipeName));
    String sqlTbl = "create table " + removeSpaces(recipeName)
        + " (ingredients varchar(100), amount varchar(20))";
    try {
      DBUtil.dbExecuteQuery(sqlTbl);
    } catch (SQLException e) {
      System.out.println("Exception creating new table");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Create procedure table based on recipe name.
   */
  public static void createProcedureTable(String recipeName)
      throws SQLException, ClassNotFoundException {
    String sqlTbl =
        "create table " + removeSpaces(recipeName) + "proc (step int,proc varchar(255))";
    System.out.println(sqlTbl);
    try {
      DBUtil.dbExecuteQuery(sqlTbl);
    } catch (SQLException e) {
      System.out.println("Exception creating new procedure table");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Updates recipe name using recipe ID and using SQL UPDATE & WHERE.
   */
  public static void updateRecipe(int id, String recipeName)
      throws SQLException, ClassNotFoundException {
    String sql = "update recipetbl set recipeName = '" + recipeName + "' where ID = '" + id + "' ";
    try {
      DBUtil.dbExecuteQuery(sql);
    } catch (SQLException e) {
      System.out.println("Error while updating");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Removes recipe from recipetbl using selected recipe ID.
   */
  public static void deleteRecipe(int id) throws SQLException, ClassNotFoundException {
    String sql = "delete from recipetbl where ID = '" + id + "' ";
    try {
      DBUtil.dbExecuteQuery(sql);
    } catch (SQLException e) {
      System.out.println("Error while deleting entry");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Returns list of all recipes for populating table.
   */
  public static ObservableList<Recipe> getAllRecipes() throws SQLException, ClassNotFoundException {
    String sql = "select * from recipetbl";
    try {
      ResultSet resSet = DBUtil.dbExecute(sql);
      ObservableList<Recipe> recList = getRecipeObjects(resSet);
      return recList;
    } catch (SQLException e) {
      System.out.println("Error retrieving all recipes" + e);
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Returns list of recipe objects.
   */
  private static ObservableList<Recipe> getRecipeObjects(ResultSet resSet)
      throws ClassNotFoundException, SQLException {
    try {
      ObservableList<Recipe> recList = FXCollections.observableArrayList();
      while (resSet.next()) {
        Recipe rec = new Recipe();
        rec.setRecipeId(resSet.getInt("ID"));
        rec.setRecipeName(resSet.getString("recipename"));
        recList.add(rec);
      }
      return recList;
    } catch (SQLException e) {
      System.out.println("Error while fetching data from DB" + e);
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Utility for removing spaces from recipe names.
   */
  public static String removeSpaces(String s) {
    String result = "";
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) != ' ') {
        result += s.charAt(i);
      }
    }
    return result;
  }
}


