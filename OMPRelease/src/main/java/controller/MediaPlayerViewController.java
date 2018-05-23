/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import static java.lang.Math.floor;
import static java.lang.String.format;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Antoine
 */
public class MediaPlayerViewController {

   private Media media;
   private MediaPlayer mediaPlayer;
   private double mRate;
   private boolean fullscreen;
   private Stage stage;
   private Duration duration;
   private BibliothequeManager bibli;
   private String path;

   @FXML
   private MediaView mediaView;
   @FXML
   private Slider volumeButton;
   @FXML
   private Button playButton;
   @FXML
   private Button stopButton;
   @FXML
   private Group groupScene;
   @FXML
   private Button fsButton;
   @FXML
   private ToolBar sliderToolbar;
   @FXML
   private ToolBar toolBarButton;
   @FXML
   private Slider sliderTime;
   @FXML
   private Label timeLabel;
   @FXML
   private Button slowButton;
   @FXML
   private Button fastButton;
   @FXML
   private GridPane gridPane;
   @FXML
   private ImageView imageView;

   public void setMedia(String path) {

      this.path = path;
      media = new Media(new File(path).toURI().toString());
      mediaPlayer = new MediaPlayer(media);

      // Creer la vue à partir du media player si c'est une video.
      if (media.getSource().contains("mp4") || media.getSource().contains("flv")) {

         mediaView.setMediaPlayer(mediaPlayer);

         // On regle la resolution du lecteur afin qu'il s'adapte à celle de la video
         DoubleProperty mvw = mediaView.fitWidthProperty();
         DoubleProperty mvh = mediaView.fitHeightProperty();
         mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
         mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
         mediaView.setViewport(new Rectangle2D(0, 0, mediaView.getFitWidth(), mediaView.getFitHeight()));
         mediaView.setPreserveRatio(true);
         mediaView.setVisible(true);
         imageView.setVisible(false);
         fsButton.setVisible(true);
      } // Sinon on affiche une image
      else {

         imageView.setVisible(true);
         mediaView.setVisible(false);
         // Comme on affiche une image, pas besoin de fullScreen
         fsButton.setVisible(false);
      }

      volumeButton.setValue(mediaPlayer.getVolume() * 100);

      sliderTime.valueProperty().addListener(new InvalidationListener() {
         public void invalidated(Observable ov) {
            if (sliderTime.isValueChanging()) {
               // 
               if (duration != null) {
                  mediaPlayer.seek(duration.multiply(sliderTime.getValue() / 100));
               }
               updateValues();

            }
         }
      });

      // On règle les ecouteurs pour regler le slider du temps
      mediaPlayer.currentTimeProperty().addListener(new ChangeListener() {
         @Override
         public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            updateValues();
         }
      });

      mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
         @Override
         public void invalidated(Observable observable) {
            updateValues(); //To change body of generated methods, choose Tools | Templates.
         }
      });

      mediaPlayer.setOnReady(new Runnable() {
         @Override
         public void run() {
            duration = mediaPlayer.getMedia().getDuration();
            updateValues();
         }
      });

      // On obtient le debit du media
      mRate = mediaPlayer.getRate();
      playPressed(null);
   }

   public void setBibliotheque(BibliothequeManager bibliotheque) {
      bibli = bibliotheque;
   }

   // Initializes the controller class.
   public void initialize() {

      fullscreen = false;

   }

   public MediaView getMediaView() {
      return mediaView;
   }

   @FXML
   private void moveTime(MouseEvent event) {
      updateValues();
   }

   @FXML
   private void setVolume(MouseEvent event) {
      volumeButton.valueProperty().addListener(new InvalidationListener() {
         @Override
         public void invalidated(Observable obs) {
            mediaPlayer.setVolume(volumeButton.getValue() / 100);
         }

      });
   }

   @FXML
   private void setVolume(ScrollEvent event) {
      volumeButton.valueProperty().addListener(new InvalidationListener() {
         @Override
         public void invalidated(Observable obs) {
            mediaPlayer.setVolume(volumeButton.getValue() / 100);
         }

      });
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
      mediaPlayer.stop();
      playButton.setText("Play");
      setMedia(bibli.precedant(path));
      playButton.setText("Pause");
      mediaPlayer.play();

   }

   private void getVolume(MouseEvent event) {
      mediaPlayer.getVolume();
   }

   @FXML
   private void fullScreenClicked(MouseEvent event) {

      fullscreen = !fullscreen;
      stage = (Stage) mediaView.getScene().getWindow();
      
      stage.setAlwaysOnTop(fullscreen);
      stage.setFullScreen(fullscreen);
      
      //sliderToolbar.setVisible(false);
      //toolBarButton.setVisible(false);
   }

   @FXML
   private void onClickMediaView(MouseEvent event) {
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
   private void mouseOnVolume(MouseEvent event) {
      
   }

   protected void updateValues() {
      if (timeLabel != null && sliderTime != null && duration != null) {
         Platform.runLater(new Runnable() {
            public void run() {
               Duration currentTime = mediaPlayer.getCurrentTime();
               timeLabel.setText(formatTime(currentTime, duration));
               sliderTime.setDisable(duration.isUnknown());
               if (!sliderTime.isDisabled() && duration.greaterThan(Duration.ZERO) && !sliderTime.isValueChanging()) {
                  sliderTime.setValue(currentTime.divide(duration).toMillis() * 100.0);
               }

            }
         });
      }
   }

   private static String formatTime(Duration elapsed, Duration duration) {
      int intElapsed = (int) floor(elapsed.toSeconds());
      int elapsedHours = intElapsed / (60 * 60);
      if (elapsedHours > 0) {
         intElapsed -= elapsedHours * 60 * 60;
      }
      int elapsedMinutes = intElapsed / 60;
      int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
              - elapsedMinutes * 60;

      if (duration.greaterThan(Duration.ZERO)) {
         int intDuration = (int) floor(duration.toSeconds());
         int durationHours = intDuration / (60 * 60);
         if (durationHours > 0) {
            intDuration -= durationHours * 60 * 60;
         }
         int durationMinutes = intDuration / 60;
         int durationSeconds = intDuration - durationHours * 60 * 60
                 - durationMinutes * 60;
         if (durationHours > 0) {
            return format("%d:%02d:%02d/%d:%02d:%02d",
                    elapsedHours, elapsedMinutes, elapsedSeconds,
                    durationHours, durationMinutes, durationSeconds);
         } else {
            return format("%02d:%02d/%02d:%02d",
                    elapsedMinutes, elapsedSeconds, durationMinutes,
                    durationSeconds);
         }
      } else {
         if (elapsedHours > 0) {
            return format("%d:%02d:%02d", elapsedHours,
                    elapsedMinutes, elapsedSeconds);
         } else {
            return format("%02d:%02d", elapsedMinutes,
                    elapsedSeconds);
         }
      }
   }

   @FXML
   private void ralentirVideo(MouseEvent event) {

      if (mRate >= 1.) {
         mRate = 0.75;
         slowButton.setText("x 0.5");
         mediaPlayer.setRate(mRate);
      } else if (mRate <= 0.75 && mRate > 0.5) {
         mRate = 0.5;
         slowButton.setText("x 0.25");
         mediaPlayer.setRate(mRate);
      } else if (mRate <= 0.5 && mRate > 0.25) {
         slowButton.setText("x 1");
         mRate = 0.25;
         mediaPlayer.setRate(mRate);
      } else if (mRate <= 0.25) {
         mRate = 1.;
         slowButton.setText("Slow");
         mediaPlayer.setRate(mRate);
      }
   }

   @FXML
   private void accelerVideo(MouseEvent event) {
      if (mRate <= 1.) {
         mRate = 1.5;
         fastButton.setText("x 2");
         mediaPlayer.setRate(mRate);
      } else if (mRate < 2. && mRate >= 1.5) {
         mRate = 2.;
         fastButton.setText("x 4");
         mediaPlayer.setRate(mRate);
      } else if (mRate < 4. && mRate >= 2.) {
         fastButton.setText("x 8");
         mRate = 4.;
         mediaPlayer.setRate(mRate);
      } else if (mRate < 8. && mRate >= 4.) {
         mRate = 8.;
         fastButton.setText("x 1");
         mediaPlayer.setRate(mRate);
      } else if (mRate == 8.) {
         mRate = 1.;
         fastButton.setText("Fast");
         mediaPlayer.setRate(mRate);
      }
   }

   @FXML
   private void onExitFullScreen(KeyEvent event) {
      
   }

   @FXML
   private void exitFullScreen(KeyEvent event) {
      
   }

   @FXML
   private void nextPressed(MouseEvent event) {

      mediaPlayer.stop();
      playButton.setText("Play");
      setMedia(bibli.suivant(path));
      playButton.setText("Pause");
      mediaPlayer.play();
   }
}
