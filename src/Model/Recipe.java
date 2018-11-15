/**
 * Recipe model
 */

package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Recipe {

  private IntegerProperty idProperty;

  private StringProperty nameProperty;


  public Recipe() {
    this.idProperty = new SimpleIntegerProperty();
    this.nameProperty = new SimpleStringProperty();
  }

  public int getRecId() {
    return idProperty.get();
  }

  public void setRecipeId(int id) {
    this.idProperty.set(id);
  }

  public IntegerProperty getRecipeId() {
    return idProperty;
  }

  public String getRecName() {
    return nameProperty.get();
  }

  public void setRecipeName(String name) {
    this.nameProperty.set(name);
  }

  public StringProperty getRecipeName() {
    return nameProperty;
  }
}
