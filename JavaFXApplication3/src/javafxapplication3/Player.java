/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 *
 * @author Antoine
 */
public class Player extends Application {

   @Override
   public void start(Stage stage) {
      // Create and set the Scene.
      Scene scene = new Scene(new Group(), 540, 209);
      stage.setScene(scene);

      // Name and display the Stage.
      stage.setTitle("Hello Media");
      stage.show();

      // Create the media source.
      //String source = getParameters().getRaw().get(0);
      File source = new File("C:/Users/Antoine/Music/duro.mp3");
      Media media = new Media(source.toURI().toString());

      // Create the player and set to play automatically.
      MediaPlayer mediaPlayer = new MediaPlayer(media);
      mediaPlayer.setAutoPlay(true);

      // Create the view and add it to the Scene.
      MediaView mediaView = new MediaView(mediaPlayer);
      ((Group) scene.getRoot()).getChildren().add(mediaView);
   }

}
