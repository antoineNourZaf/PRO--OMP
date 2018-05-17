/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Antoine
 */
public class MediaPlayerViewController {

   private Media media;
   private MediaPlayer mediaPlayer;
   @FXML
   private MediaView mediaView;
   @FXML
   private Slider elapseTimeButton;
   @FXML
   private Slider volumeButton;
   @FXML
   private Button playButton;
   @FXML
   private Button stopButton;

   
   public void getMedia(String path) {
      
         media = new Media(new File(path).toURI().toString());
      mediaPlayer = new MediaPlayer(media);
      mediaView = new MediaView(mediaPlayer);

   }

   /**
    * Initializes the controller class.
    
   @Override
   public void start(Stage scene) {
      try {
         FXMLLoader loader = new FXMLLoader();
         File file = new File("C:/Users/Antoine/Documents/NetBeansProjects/OMP/src/view/mediaPlayerView.fxml");
         if (file.exists()) {
            //loader = FXMLLoader.load(getClass().getClassLoader().getResource("view/mediaPlayerView.fxml"));
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("C:/Users/Antoine/Documents/NetBeansProjects/OMP/src/view/mediaPlayerView.fxml"));
            scene.setTitle("Player");
            scene.setScene(new Scene(root, media.getHeight(), media.getWidth()));
            scene.show();
         } else {
            System.err.println("Fichier non existant");
         }
         mediaPlayer.play();

      } catch (IOException ex) {
         Logger.getLogger(MediaPlayerViewController.class.getName()).log(Level.SEVERE, null, ex);
      }

   }
   */
   @FXML
   public void initialize() {
      System.out.println("Je passe par la");
   }
   
   
   @FXML
   private void moveTime(MouseEvent event) {
   }

   @FXML
   private void setVolume(MouseEvent event) {
   }

   @FXML
   private void setVolume(ScrollEvent event) {
   }

   @FXML
   private void playPressed(MouseEvent event) {
   }

   @FXML
   private void stopPressed(MouseEvent event) {
   }

}
