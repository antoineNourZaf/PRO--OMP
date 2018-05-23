/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author zacharie
 */
public class AudioPlayer extends Application {

   private Media media;
   private MediaPlayer mediaPlayer;
   private MediaView mediaView;
   private Label artist;
   private Label album;
   private Label title;
   private Label year;
   private ImageView albumCover;

   private String Artiste;
   private String Album;
   private String Titre;
   private String Annee;

   private ProgressBar progress = new ProgressBar();
   private ChangeListener<Duration> progressChangeListener;

   private String mediaPath;

   void afficheMetadata() {
      System.out.println(Artiste);
      System.out.println(Album);
      System.out.println(Titre);
      System.out.println(Annee);
   }

   @Override
   public void start(Stage stage) {
      //createControls();
      //createMedia();

      //  final URL stylesheet = getClass().getResource("media.css");
      //  scene.getStylesheets().add(stylesheet.toString());
      final StackPane layout = new StackPane();
      stage.setTitle("OMP Audio Player");

// crÈe une vue pour montrer les mediaplayers.
      final Button next = new Button("Next");
      final Button play = new Button("Pause");

      File file = new File(mediaPath);

      media = new Media(file.toURI().toString());
      mediaPlayer = new MediaPlayer(media);
      mediaView = new MediaView(mediaPlayer);

      Button backgroundButton = new Button("Pause");
      backgroundButton.setVisible(false);
      play.prefHeightProperty().bind(backgroundButton.heightProperty());
      play.prefWidthProperty().bind(backgroundButton.widthProperty());

      // allow the user to play or pause a track.
      play.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent actionEvent) {
            if ("Pause".equals(play.getText())) {
               mediaView.getMediaPlayer().pause();
               play.setText("Play");
            } else {
               mediaView.getMediaPlayer().setAutoPlay(true);
               mediaView.getMediaPlayer().play();

               play.setText("Pause");
            }
         }
      });

      // affiche le nom de la piste en cours de lecture.
      mediaView.mediaPlayerProperty().addListener(new ChangeListener<MediaPlayer>() {
         @Override
         public void changed(ObservableValue<? extends MediaPlayer> observableValue, MediaPlayer oldPlayer, MediaPlayer newPlayer) {
            setCurrentlyPlaying(newPlayer);
         }
      });

      // la scene
      layout.setStyle("-fx-background-color: cornsilk; -fx-font-size: 20; -fx-padding: 20; -fx-alignment: center;");
      layout.getChildren().addAll(backgroundButton,
              VBoxBuilder.create().spacing(10).children(HBoxBuilder.create().spacing(10).alignment(Pos.CENTER).children(next, play, progress, mediaView).build()
              ).build()
      );

      progress.setMaxWidth(Double.MAX_VALUE);
      HBox.setHgrow(progress, Priority.ALWAYS);

      Scene scene = new Scene(layout, 600, 120);
      stage.setScene(scene);
      stage.show();

   }

   private void setCurrentlyPlaying(final MediaPlayer player) {
      progress.setProgress(0);
      progressChangeListener = new ChangeListener<Duration>() {
         @Override
         public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
            progress.setProgress(1.0 * player.getCurrentTime().toMillis() / player.getTotalDuration().toMillis());
         }
      };
      player.currentTimeProperty().addListener(progressChangeListener);
   }

   private GridPane createGridPane() {
      final GridPane gp = new GridPane();
      gp.setPadding(new Insets(10));
      gp.setHgap(20);
      // gp.add(albumCover, 0, 0, 1, GridPane.REMAINING);
      gp.add(title, 1, 0);
      gp.add(artist, 1, 1);
      gp.add(album, 1, 2);
      gp.add(year, 1, 3);

      final ColumnConstraints c0 = new ColumnConstraints();
      final ColumnConstraints c1 = new ColumnConstraints();
      c1.setHgrow(Priority.ALWAYS);
      gp.getColumnConstraints().addAll(c0, c1);

      final RowConstraints r0 = new RowConstraints();
      r0.setValignment(VPos.TOP);
      gp.getRowConstraints().addAll(r0, r0, r0, r0);

      return gp;
   }

   private void createControls() {
      artist = new Label();
      artist.setId("artist");
      album = new Label();
      album.setId("album");
      title = new Label();
      title.setId("title");
      year = new Label();
      year.setId("year");

      final Reflection reflection = new Reflection();
      reflection.setFraction(0.2);

      /* final URL url = getClass().getResource("C:\\Users\\z-ack\\Pictures\\Camera Roll\\la-fouine-la-fouine-laouni.png");
  final Image image = new Image(url.toString());
    
  albumCover = new ImageView(image);
   albumCover.setFitWidth(240);
   albumCover.setPreserveRatio(true);
  albumCover.setSmooth(true);
    albumCover.setEffect(reflection);*/
   }

   private void createMedia() {
      try {

         File filestring = new File("/Users/walidkoubaa/Documents/The Weeknd - Starboy ft. Daft Punk.mp3");
         media = new Media(filestring.toURI().toString());
         media.getMetadata().addListener(new MapChangeListener<String, Object>() {
            @Override
            public void onChanged(Change<? extends String, ? extends Object> ch) {
               if (ch.wasAdded()) {
                  handleMetadata(ch.getKey(), ch.getValueAdded());
               }
            }
         });

         mediaPlayer = new MediaPlayer(media);
         mediaPlayer.setOnError(new Runnable() {
            @Override
            public void run() {
               final String errorMessage = media.getError().getMessage();
               System.out.println("MediaPlayer Error: " + errorMessage);
            }
         });

         mediaPlayer.play();
         mediaPlayer.setVolume(2.0);
         mediaPlayer.setAutoPlay(true);

      } catch (RuntimeException re) {
         System.out.println("Caught Exception: " + re.getMessage());
      }
   }

   private void handleMetadata(String key, Object value) {
      if (key.equals("album")) {
         album.setText("album: " + value.toString());
         Album = value.toString();
      } else if (key.equals("artist")) {
         artist.setText("artist:   " + value.toString());
         Artiste = value.toString();
      }
      if (key.equals("title")) {
         title.setText("title:   " + value.toString());
         Titre = value.toString();
      }
      if (key.equals("year")) {
         year.setText(value.toString());
         Annee = value.toString();
      }
      if (key.equals("image")) {
         albumCover.setImage((Image) value);
      }
   }

   public void setMediaPath(String mediaPath) {
      this.mediaPath = mediaPath;
   }
}
