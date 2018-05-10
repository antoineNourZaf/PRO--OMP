package metadonnees;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;



/* Exemple de lecture de tous les fichiers audio dans un répertoire donné. */

public class AudioPlaylist extends Application {
     
  final Label currentlyPlaying = new Label();
  final Label duration = new Label();
  final Label titre = new Label();

  final ProgressBar progress = new ProgressBar();
  
  private ChangeListener<Duration> progressChangeListener;

  public static void main(String[] args) throws Exception { launch(args); }

  public void start(final Stage stage) throws Exception {
  
    stage.setTitle("Simple Audio Player");

    final StackPane layout = new StackPane();

    
// détermine le répertoire source de la playlist (soit le premier paramètre du programme, soit un
    final List<String> myParams = getParameters().getRaw();
    File myDirectory;
    if(myParams.size() > 0){
       myDirectory =  new File(myParams.get(0));
    }else{
        myDirectory = new File("C:\\Users\\z-ack\\Documents\\musique");
    }
    
    if (!myDirectory.exists() && myDirectory.isDirectory()) {
      System.out.println("Cannot find audio source directory: " + myDirectory);
    }

 // crée des lecteurs multimédias.
    final List<MediaPlayer> playersList = new ArrayList<MediaPlayer>();
    for (String file : myDirectory.list(new FilenameFilter() {
      @Override public boolean accept(File dir, String audioName) {
        return audioName.endsWith(".mp3");
      }
    }))
        playersList.add(createPlayer("file:///" + (myDirectory + "\\" + file).replace("\\", "/").replaceAll(" ", "%20")));
    
    if (playersList.isEmpty()) {
      System.out.println("No audio found in " + myDirectory);
      return;
    }    
    
// crée une vue pour montrer les mediaplayers.
    final MediaView mediaView = new MediaView(playersList.get(0));
    final Button next = new Button("Next");
    final Button play = new Button("Pause");
    
    

 
// joue chaque fichier audio à son tour.
    for (int i = 0; i < playersList.size(); i++) {
      final MediaPlayer player     = playersList.get(i);
      final MediaPlayer nextPlayer = playersList.get((i + 1) % playersList.size());
      player.setOnEndOfMedia(new Runnable() {
        @Override public void run() {
          player.currentTimeProperty().removeListener(progressChangeListener);
          mediaView.setMediaPlayer(nextPlayer);
          nextPlayer.play();
        }
      });
    }
     
  
// joue chaque fichier audio à son tour.
    next.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent actionEvent) {
        final MediaPlayer curPlayer = mediaView.getMediaPlayer();
        MediaPlayer nextPlayer = playersList.get((playersList.indexOf(curPlayer) + 1) % playersList.size());
        mediaView.setMediaPlayer(nextPlayer);
        curPlayer.currentTimeProperty().removeListener(progressChangeListener);
        curPlayer.stop();
        nextPlayer.play();
      }
    });

    // allow the user to play or pause a track.
    play.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent actionEvent) {
        if ("Pause".equals(play.getText())) {
          mediaView.getMediaPlayer().pause();
          play.setText("Play");
        } else {
          mediaView.getMediaPlayer().play();
          play.setText("Pause");
        }
      }
    });

  
// affiche le nom de la piste en cours de lecture.
    mediaView.mediaPlayerProperty().addListener(new ChangeListener<MediaPlayer>() {
      @Override public void changed(ObservableValue<? extends MediaPlayer> observableValue, MediaPlayer oldPlayer, MediaPlayer newPlayer) {
        setCurrentlyPlaying(newPlayer);
      }
    });
    
   
// commence à jouer la première piste.
    mediaView.setMediaPlayer(playersList.get(0));
    mediaView.getMediaPlayer().play();
    setCurrentlyPlaying(mediaView.getMediaPlayer());

   
// bouton invisible utilisé comme un modèle pour obtenir la taille préférée réelle du bouton Pause.
    Button backgroundButton = new Button("Pause");
    
    backgroundButton.setVisible(false);
    play.prefHeightProperty().bind(backgroundButton.heightProperty());
    play.prefWidthProperty().bind(backgroundButton.widthProperty());
    
    // la scène .
    layout.setStyle("-fx-background-color: cornsilk; -fx-font-size: 20; -fx-padding: 20; -fx-alignment: center;");
    layout.getChildren().addAll(backgroundButton,
      VBoxBuilder.create().spacing(10).children(currentlyPlaying,HBoxBuilder.create().spacing(10).alignment(Pos.CENTER).children(next, play, progress, mediaView).build()
      ).build()
    );
    progress.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(progress, Priority.ALWAYS);
    Scene scene = new Scene(layout, 600, 120);
    stage.setScene(scene);
    stage.show();
  }
  
  /** @return un MediaPlayer pour la source donnée qui rapportera les erreurs rencontrées */
  private MediaPlayer createPlayer(String aMediaSrc) {
    final MediaPlayer player = new MediaPlayer(new Media(aMediaSrc));
    player.setOnError(new Runnable() {
      @Override public void run() {
        System.out.println("Media error occurred: " + player.getError());
      }
    });
    return player;
  }
  
/** définit l'étiquette en cours de lecture sur l'étiquette du nouveau lecteur multimédia et met à jour le moniteur de progression. */
  private void setCurrentlyPlaying(final MediaPlayer newPlayer) {
    progress.setProgress(0);
    progressChangeListener = new ChangeListener<Duration>() {
      @Override public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
        progress.setProgress(1.0 * newPlayer.getCurrentTime().toMillis() / newPlayer.getTotalDuration().toMillis());
      }
    };
    newPlayer.currentTimeProperty().addListener(progressChangeListener);
 
  }
}