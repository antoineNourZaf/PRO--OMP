/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractdata;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
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
  private Label format;
  private ImageView albumCover;
  private Label duration;
  private Label genre;
  
  private String Artiste;
  private String Album;
  private String Titre;
  private String Annee;
  private String Format;
  private String Duration;
  

  public static void main(String[] args) {
    launch(args);
  }
  
   void afficheMetadata(){
       System.out.println(Artiste);
        System.out.println(Album);
        System.out.println(Titre);
        System.out.println(Annee);
        System.out.println(Duration);
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
    
    String ERREUR = "donnee pas disponible";
    
    if(Annee == null){
        year.setText("Annee :   " + ERREUR );
    }
    if(Album == null){
        album.setText("Album :   " + ERREUR);
    }
    
   // gp.add(albumCover, 0, 0, 1, GridPane.REMAINING);
    gp.add(title , 1, 0);
    gp.add(artist, 1, 1);
    gp.add(album,1, 2);
    gp.add(year,1, 3);
    gp.add(format,1, 4);
    gp.add(duration,1,5);
    gp.add(genre,1,6);
    

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
    album = new Label();
    title = new Label();
    year = new Label();
    format = new Label();
    duration = new Label();
    genre = new  Label();
 
    
    final Reflection reflection = new Reflection();
    reflection.setFraction(0.2);
  }

  private void createMedia() {
    try {
        
      File filestring = new File("C:\\Users\\z-ack\\Documents\\musique\\Michael Jackson;The Cleveland Orchestra - Will You Be There.mp3");
      media = new Media(filestring.toURI().toString());
      
      ArrayList<String>  metadata = new ArrayList<>();
   
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
      
         mediaPlayer.setOnReady(new Runnable() {

        @Override
        public void run() {
               int seconde = (int) media.getDuration().toSeconds();
               int nbresecond = seconde%60;
               int nbreminut = (seconde/60)%60; 
               Duration = "Duration: "+ nbreminut + ":" + nbresecond;
               System.out.println(Duration);  
               duration.setText(Duration);

            // display media's metadata
            for (Map.Entry<String, Object> entry : media.getMetadata().entrySet()){
                System.out.println(entry.getKey() + ": " + entry.getValue());
                metadata.add(entry.getKey() + ": " + entry.getValue());
            }
            
            mediaPlayer.play();
        }
    });
      
    } catch (RuntimeException re) {
      System.out.println("Caught Exception: " + re.getMessage());
    }
  }

  private void handleMetadata(String key, Object value) {
      
    format.setText("Format:  "+ "mp3");
    if (key.equals("album")) {
      album.setText( "Album : " + value.toString());
      Album = value.toString();
    }
    if (key.equals("artist")) {
      artist.setText("Artist  :   " + value.toString());
       Artiste = value.toString();
    }
    if (key.equals("title")) {
      title.setText("Title    :   " + value.toString());
      Titre = value.toString();
    } 
    if (key.equals("year")) {
      year.setText("Annee :   " +value.toString());
      Annee = value.toString();
    } 
    if (key.equals("image")) {
      albumCover.setImage((Image)value);
    }
    if(key.equals("genre")){
        genre.setText("Genre :   " + value.toString());
        
    }
    
  }
}

