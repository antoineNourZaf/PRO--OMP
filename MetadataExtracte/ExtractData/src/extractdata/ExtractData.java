/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractdata;
import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.collections.MapChangeListener;
import javafx.collections.MapChangeListener.Change;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 *
 * @author zacharie
 */
public class ExtractData extends Application {
  private Media media;
  private MediaPlayer mediaPlayer;
  
  private Label artist;
  private Label album;
  private Label title;
  private Label year;
  private ImageView albumCover;
  
  private String Artiste;
  private String Album;
  private String Titre;
  private String Annee;
  

  public static void main(String[] args) {
    launch(args);
  }
  
   void afficheMetadata(){
       System.out.println(Artiste);
        System.out.println(Album);
        System.out.println(Titre);
        System.out.println(Annee);
   }

  @Override
  public void start(Stage primaryStage) {
    createControls();
    createMedia();
    
    final Scene scene = new Scene(createGridPane(), 800, 400);
  //  final URL stylesheet = getClass().getResource("media.css");
  //  scene.getStylesheets().add(stylesheet.toString());

    primaryStage.setScene(scene);
    primaryStage.setTitle("Audio Player 2");
    primaryStage.show();
  }

  private GridPane createGridPane() {
    final GridPane gp = new GridPane();
    gp.setPadding(new Insets(10));
    gp.setHgap(20);
   // gp.add(albumCover, 0, 0, 1, GridPane.REMAINING);
    gp.add(title , 1, 0);
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
        
      File filestring = new File("C:\\Users\\z-ack\\Documents\\musique\\La Fouine - Ma meilleure ft. Zaho.mp3");
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

    } catch (RuntimeException re) {
      System.out.println("Caught Exception: " + re.getMessage());
    }
  }

  private void handleMetadata(String key, Object value) {
    if (key.equals("album")) {
      album.setText( "album: " + value.toString());
      Album = value.toString();
    } else if (key.equals("artist")) {
      artist.setText("artist:   " + value.toString());
       Artiste = value.toString();
    } if (key.equals("title")) {
      title.setText("title:   " + value.toString());
      Titre = value.toString();
    } if (key.equals("year")) {
      year.setText(value.toString());
      Annee = value.toString();
    } if (key.equals("image")) {
      albumCover.setImage((Image)value);
    }
  }
}
