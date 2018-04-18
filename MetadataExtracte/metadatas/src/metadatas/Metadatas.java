/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metadatas;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 *
 * @author z-ack
 */
public class Metadatas extends Application {

    private String Artiste;
    private String Album;
    private String Titre;
    private String Annee;
    private String Format;
    private String Duration;
    private Media media;
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) {

        try {

            File filestring = new File("C:\\Users\\z-ack\\Documents\\musique\\Michael Jackson;The Cleveland Orchestra - Will You Be There.mp3");
            media = new Media(filestring.toURI().toString());

            ArrayList<String> metadata = new ArrayList<>();
            
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
                    int duree = (int) media.getDuration().toSeconds();
                    int seconde = duree % 60;
                    int minutes = (duree / 60) % 60;
                    Duration = "duree: " + minutes + ":" + seconde;

                    for (Map.Entry<String, Object> entry : media.getMetadata().entrySet()) {
                        if(entry.getKey().toString() == "title" || entry.getKey().toString() == "artist" ||
                                entry.getKey().toString() == "album" ||  entry.getKey().toString() == "genre" 
                                      || entry.getKey().toString() == "image" || entry.getKey().toString() == "year"){
                            
                             metadata.add(entry.getKey() + ": " + entry.getValue());   
                        } 
                    }
                    metadata.add(Duration);
                    metadata.add("format: mp3");
                    
                    for(String s: metadata){
                        System.out.println(s);
                    }
                    mediaPlayer.play();
                }
            });

        } catch (RuntimeException re) {
            System.out.println("Caught Exception: " + re.getMessage());
        }

    }


}
