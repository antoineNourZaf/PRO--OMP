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
import javafx.scene.layout.VBox;
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
   @FXML
   private VBox vBox;

   public void setMedia(String path) {

      media = new Media(new File(path).toURI().toString());
      mediaPlayer = new MediaPlayer(media);

      mediaView.setMediaPlayer(mediaPlayer);
      mediaView.setPreserveRatio(true);
      
   }

   /**
    * Initializes the controller class.
    *
    * @Override public void start(Stage scene) { try { FXMLLoader loader = new
    * FXMLLoader(); File file = new
    * File("C:/Users/Antoine/Documents/NetBeansProjects/OMP/src/view/mediaPlayerView.fxml");
    * if (file.exists()) { //loader =
    * FXMLLoader.load(getClass().getClassLoader().getResource("view/mediaPlayerView.fxml"));
    * Parent root =
    * FXMLLoader.load(getClass().getClassLoader().getResource("C:/Users/Antoine/Documents/NetBeansProjects/OMP/src/view/mediaPlayerView.fxml"));
    * scene.setTitle("Player"); scene.setScene(new Scene(root,
    * media.getHeight(), media.getWidth())); scene.show(); } else {
    * System.err.println("Fichier non existant"); } mediaPlayer.play();
    *
    * } catch (IOException ex) {
    * Logger.getLogger(MediaPlayerViewController.class.getName()).log(Level.SEVERE,
    * null, ex); }
    *
    * }
    */
   public void initialize() {
      System.out.println("Je passe par la");
      volumeButton.setMax(100);
      volumeButton.setMin(0);
      mediaView = new MediaView();
      mediaView.setFitHeight(400);
      mediaView.setFitWidth(400);
      mediaView.setVisible(true);
   }

   @FXML
   private void moveTime(MouseEvent event) {
      mediaPlayer.setVolume(50);
      volumeButton.setValue(100);
   }

   @FXML
   private void setVolume(MouseEvent event) {
      mediaPlayer.setVolume(event.getSceneY());
      volumeButton.setValue(event.getSceneY());
   }

   @FXML
   private void setVolume(ScrollEvent event) {
      mediaPlayer.setVolume(event.getDeltaY());
      volumeButton.setValue(event.getDeltaY());
   }

   @FXML
   private void playPressed(MouseEvent event) {
      mediaPlayer.play();
      if (playButton.getText().equals("Play")) {
         playButton.setText("Pause");
         mediaPlayer.play();
      } else {
         playButton.setText("Play");
         mediaPlayer.pause();
      }
   }

   @FXML
   private void stopPressed(MouseEvent event) {
      mediaPlayer.stop();
      playButton.setText("Play");
   }

   @FXML
   private void backPressed(MouseEvent event) {
   }

   @FXML
   private void getVolume(MouseEvent event) {
      mediaPlayer.getVolume();
   }
   
   public void affiche(){
      System.out.println("Je ne suis pas nul");
   }

   @FXML
   private void play(MouseEvent event) {
      
   }
}
