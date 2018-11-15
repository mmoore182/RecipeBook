package Controller;

import Model.Ingredient;
import Model.IngredientDAO;
import Model.Procedure;
import Model.ProcedureDAO;
import Model.Recipe;
import Model.RecipeDAO;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RecipeController {

  /**
   * Textfields for getting user input.
   */
  @FXML
  private TextField txtIngredient;
  @FXML
  private TextField txtIngredientAmt;
  @FXML
  private TextField txtRecipe;
  @FXML
  private TextField txtRecipeID;
  @FXML
  private TextArea txtProcedure;
  @FXML
  private TextField txtStep;

  /**
   * Recipe table + columns for table.
   */
  @FXML
  private TableColumn<Recipe, Integer> idColumn;
  @FXML
  private TableColumn<Recipe, String> recipeColumn;
  @FXML
  private TableView recipeTable;

  /**
   * Ingredient Table + columns for table.
   */
  @FXML
  private TableColumn<Ingredient, String> ingredientColumn;
  @FXML
  private TableColumn<Ingredient, String> amountColumn;
  @FXML
  private TableView ingredientTable;

  /**
   * Procedure Table + columns for table.
   */
  @FXML
  private TableColumn<Procedure, String> procedureColumn;
  @FXML
  private TableColumn<Procedure, Integer> procedureStepColumn;
  @FXML
  private TableView procedureTable;

  /**
   * Inserts recipe into recipetable using input from txtRecipe textfield. Repopulates recipetable
   * after recipe insertion
   */
  @FXML
  private void insertRecipe(ActionEvent event) throws ClassNotFoundException, SQLException {
    try {
      RecipeDAO.insertRecipe(txtRecipe.getText());
      ObservableList<Recipe> recList = RecipeDAO.getAllRecipes();
      populateRecTable(recList);
    } catch (SQLException e) {
      System.out.println("Error inserting recipe" + e);
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Adds functionality for recipetable to be double clicked. Populates ingredient table & procedure
   * table using double-clicked recipe.
   */
  public void setClickableTables(TableView table) {
    table.setRowFactory(tv -> {
      TableRow<Recipe> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && (!row.isEmpty())) {
          Recipe rowData = row.getItem();
          String selection = rowData.getRecName();
          System.out.println("Double click on: " + selection.replaceAll(" ", ""));
          ObservableList<Ingredient> ingredList = null;
          ObservableList<Procedure> procList = null;
          try {
            ingredList = IngredientDAO.getIngredients(rowData.getRecName().replaceAll(" ", ""));
            procList = ProcedureDAO.getProcedures(rowData.getRecName().replaceAll(" ", ""));
          } catch (SQLException e) {
            e.printStackTrace();
          } catch (ClassNotFoundException e) {
            e.printStackTrace();
          }
          populateIngredTable(ingredList);
          populateProcTable(procList);
        }
      });
      return row;
    });
  }

  /**
   * Returns selected recipe name from recipetable.
   */
  public String onSelection() {
    Recipe recipe = (Recipe) recipeTable.getSelectionModel().getSelectedItem();
    String recName = recipe.getRecName();
    return recName;
  }

  /**
   * Returns selected ingredient name from ingredient table.
   */
  public String onIngredientSelection() {
    Ingredient ingredient = (Ingredient) ingredientTable.getSelectionModel().getSelectedItem();
    String ing = ingredient.getIngredient();
    return ing;
  }

  /**
   * Returns selected procedure from procedure table.
   */
  public String onProcedureSelection() {
    Procedure procedure = (Procedure) procedureTable.getSelectionModel().getSelectedItem();
    String proc = procedure.getProcedure();
    return proc;
  }

  /**
   * Returns selected recipeID from recipetable.
   */
  private int onSelectionId() {
    Recipe recipe = (Recipe) recipeTable.getSelectionModel().getSelectedItem();
    int recId = recipe.getRecId();
    return recId;
  }

  /**
   * Updates recipe name using the inserted recipe ID from user.
   */
  @FXML
  private void updateRecipe(ActionEvent event) throws ClassNotFoundException, SQLException {
    try {
      RecipeDAO.updateRecipe(Integer.parseInt(txtRecipeID.getText()), txtRecipe.getText());
      ObservableList<Recipe> recList = RecipeDAO.getAllRecipes();
      ;
      populateRecTable(recList);
    } catch (SQLException e) {
      System.out.println("Error while updating recipe");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Removes recipe based on user selection from table. Removes corresponding ingredient & procedure
   * tables. Repopulates recipe table.
   */
  @FXML
  private void deleteRecipe(ActionEvent event) throws ClassNotFoundException, SQLException {
    try {
      //RecipeDAO.deleteRecipe(Integer.parseInt(txtRecipeID.getText()));
      RecipeDAO.deleteRecipe(onSelectionId());
      IngredientDAO.deleteIngredientTable(onSelection());
      ProcedureDAO.deleteProcedureTable(onSelection());
      ObservableList<Recipe> recList = RecipeDAO.getAllRecipes();
      populateRecTable(recList);
    } catch (SQLException e) {
      System.out.println("Error while deleting recipe");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Removes selected ingredient from ingredient table based on user selection. Repopulates table
   * after deletion.
   */
  @FXML
  private void deleteIngredient(ActionEvent event) throws ClassNotFoundException, SQLException {
    try {
      IngredientDAO.deleteIngredient(onSelection().replaceAll(" ", ""), onIngredientSelection());
      ObservableList<Ingredient> ingredList = IngredientDAO
          .getIngredients(onSelection().replaceAll(" ", ""));
      populateIngredTable(ingredList);
    } catch (SQLException e) {
      System.out.println("Error deleting ingredient from table");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Removes selected procedure from procedure table based on user selection. Repopulates table
   * after deletion
   */
  @FXML
  private void deleteProcedure(ActionEvent event) throws ClassNotFoundException, SQLException {
    try {
      ProcedureDAO.deleteProcedure(onSelection().replaceAll(" ", ""), onProcedureSelection());
      ObservableList<Procedure> procList = ProcedureDAO
          .getProcedures(onSelection().replaceAll(" ", ""));
      populateProcTable(procList);
    } catch (SQLException e) {
      System.out.println("Error deleting ingredient from table");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Inserts ingredient and ingredient amount into ingredient table based on user input. Repopulates
   * ingredient table after insertion.
   */
  @FXML
  private void insertIngredient(ActionEvent event) throws ClassNotFoundException, SQLException {
    try {
      Recipe recipe = (Recipe) recipeTable.getSelectionModel().getSelectedItem();
      String recName = recipe.getRecName();
      IngredientDAO.insertIngredient(recName, txtIngredient.getText(), txtIngredientAmt.getText());
      ObservableList<Ingredient> ingredList = IngredientDAO.getIngredients(recName);
      System.out.println("Populate table: " + recName);
      populateIngredTable(ingredList);
    } catch (SQLException e) {
      System.out.println("Error while inserting ingredient");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Inserts procedure and step number into procedure table. Repopulates & Sorts procedure table
   * after insertion.
   */
  @FXML
  private void insertProcedure(ActionEvent event) throws ClassNotFoundException, SQLException {
    try {
      Recipe recipe = (Recipe) recipeTable.getSelectionModel().getSelectedItem();
      String recName = recipe.getRecName();
      ProcedureDAO
          .insertProcedure(recName, Integer.parseInt(txtStep.getText()), txtProcedure.getText());
      ObservableList<Procedure> procList = ProcedureDAO.getProcedures(recName);
      populateProcTable(procList);
    } catch (SQLException e) {
      System.out.print("Error inserting procedure into table");
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Initializes all table columns. Populates recipe table on startup.
   */
  @FXML
  private void initialize() throws Exception {
    idColumn.setCellValueFactory(cellData -> cellData.getValue().getRecipeId().asObject());
    recipeColumn.setCellValueFactory(cellData -> cellData.getValue().getRecipeName());
    ingredientColumn.setCellValueFactory(cellData -> cellData.getValue().getIngredientProperty());
    amountColumn.setCellValueFactory(cellData -> cellData.getValue().getAmountProperty());
    procedureColumn.setCellValueFactory(cellData -> cellData.getValue().getProcedureProperty());
    procedureStepColumn
        .setCellValueFactory(cellData -> cellData.getValue().getStepProperty().asObject());
    ObservableList<Recipe> recList = RecipeDAO.getAllRecipes();
    populateRecTable(recList);
    setClickableTables(recipeTable);
  }

  /**
   * Populates recipe table.
   */
  private void populateRecTable(ObservableList<Recipe> recTable) {
    recipeTable.setItems(recTable);
  }

  /**
   * Populates ingredient table.
   */
  private void populateIngredTable(ObservableList<Ingredient> ingredTable) {
    ingredientTable.setItems(ingredTable);
  }

  /**
   * Populates procedure table.
   */
  private void populateProcTable(ObservableList<Procedure> procTable) {
    System.out.println(procTable);
    procedureTable.setItems(procTable);
  }

}
