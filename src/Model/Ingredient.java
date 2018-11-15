/**
 * Ingredient model
 */

package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ingredient {

  private StringProperty ingredientProperty;
  private StringProperty amountProperty;

  public Ingredient() {
    this.ingredientProperty = new SimpleStringProperty();
    this.amountProperty = new SimpleStringProperty();
  }

  public String getIngredient() {
    return ingredientProperty.get();
  }

  public StringProperty getIngredientProperty() {
    return ingredientProperty;
  }

  public void setIngredientProperty(String ingredientProperty) {
    this.ingredientProperty.set(ingredientProperty);
  }

  public String getAmount() {
    return amountProperty.get();
  }

  public StringProperty getAmountProperty() {
    return amountProperty;
  }

  public void setAmountProperty(String amountProperty) {
    this.amountProperty.set(amountProperty);
  }
}
