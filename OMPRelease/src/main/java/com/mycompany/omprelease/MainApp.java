package com.mycompany.omprelease;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

   @Override
   public void start(Stage primaryStage) throws Exception {
      
      Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
      primaryStage.setTitle("OMP - Outstanding Media Player");
      primaryStage.setScene(new Scene(root, 1200, 700));
      primaryStage.show();

   }

   public static void main(String[] args) {
      launch(args);
   }
}
