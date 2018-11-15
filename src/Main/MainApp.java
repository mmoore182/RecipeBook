package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/Controller/RecipeView.fxml"));
    Scene scene = new Scene(root);
    scene.getStylesheets().add("/Controller/RecipeStyle.css");
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.setTitle("Recipe Book");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
