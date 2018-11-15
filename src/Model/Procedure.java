/**
 * Procedure model
 */

package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Procedure {

  private IntegerProperty stepProperty;
  private StringProperty procedureProperty;

  public Procedure() {
    this.stepProperty = new SimpleIntegerProperty();
    this.procedureProperty = new SimpleStringProperty();
  }

  public int getStep() {
    return stepProperty.get();
  }

  public IntegerProperty getStepProperty() {
    return stepProperty;
  }

  public void setStepProperty(int stepProperty) {
    this.stepProperty.set(stepProperty);
  }

  public String getProcedure() {
    return procedureProperty.get();
  }

  public StringProperty getProcedureProperty() {
    return procedureProperty;
  }

  public void setProcedureProperty(String procedureProperty) {
    this.procedureProperty.set(procedureProperty);
  }
}
