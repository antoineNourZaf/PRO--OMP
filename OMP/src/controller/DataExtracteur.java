/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Audio;
import java.io.File;
import java.util.Map;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author z-ack & Antoine
 */
public class DataExtracteur {

   private Media media;
   private MediaPlayer mediaPlayer;
   private final static int SECONDES = 60;
   private String path;

   DataExtracteur(String path) {
      this.path = path;
   }

   public void ExtractionMetadata(String path, Audio audio, BibliothequeManager manager) {

      try {
         File filestring = new File(path);
         media = new Media(filestring.toURI().toString());

         mediaPlayer = new MediaPlayer(media);
         
         // En cas d'erreur de l'audio
         mediaPlayer.setOnError(new Runnable() {
            @Override
            public void run() {
               final String errorMessage = media.getError().getMessage();
               System.out.println("MediaPlayer Error: " + errorMessage);
            }
         });
         
         // Il faut que le statut du player soit READY pour obtenir les datas
         mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                  
               for (Map.Entry<String, Object> entry : media.getMetadata().entrySet()) {

                  if (entry.getKey().equals("title")) {
                     audio.setTitre((String)entry.getValue());
                  }
                  if (entry.getKey().equals("artist")) {
                     audio.setArtiste((String)entry.getValue());
                  }
                  if (entry.getKey().equals("album")) {
                     audio.setAlbum((String)entry.getValue());
                  }
                  if (!media.getDuration().isIndefinite() && !media.getDuration().isUnknown()) {

                     double minute = media.getDuration().toMinutes() % 60;
                     double secondes = media.getDuration().toSeconds() % 60;

                     int min = (int)minute;
                     int sec = (int)secondes;

                     audio.setDuree(String.format("%02d:%02d",min, sec));
                  }
                  manager.ajouterMusique(audio);
               }
            }
         });

      } catch (RuntimeException ex) {
         System.out.println("Caught Exception: " + ex.getMessage());
      }

   }
}
