/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import controller.MediaPlayerViewController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Antoine
 */
public class FXMLDocumentController implements Initializable {
   
   @FXML
   private Label label;
   
   @FXML
   private void handleButtonAction(ActionEvent event) {
      
      try {
         
         
         String path = "C:/Users/Antoine/Music/video.mp4";
         FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
         Stage stage = new Stage();
         Parent root = (Parent) loader.load();
         Scene scene = new Scene(root);
         
         MediaPlayerViewController controller = loader.getController();
         controller.setMedia(path);
         loader.setController(controller);
         stage.setScene(scene);
         
         stage.show();
      } catch (IOException ex) {
         Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      
      
      
   }
   
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      // TODO
   }   
   
}
