 /**
  * DataExtracteur.java
  * 
  * Cette classe sert à obtenir les metadatas d'un fichier audio.
  * Malheureuseument JavaFX ne permet d'obtenir que les metadonnes pour un 
  * fichier au format mp3. Ainsi les autres formats ne sont pas pris en charge
  * et on ne peut avoir leur description
  */
package controller;

import model.Audio;
import java.io.File;
import java.util.Map;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class DataExtracteur {

   private Media media;
   private MediaPlayer mediaPlayer;
   private final static int SECONDES = 60;
   private String path;

   DataExtracteur(String path) {
      this.path = path;
   }

   public void ExtractionMetadata(String path, final Audio audio, final BibliothequeManager manager) {

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
                     
                     double minute = media.getDuration().toMinutes() % SECONDES;
                     double secondes = media.getDuration().toSeconds() % SECONDES;
                     
                     int min = (int)minute;
                     int sec = (int)secondes;
                     
                     audio.setDuree(String.format("%02d:%02d",min, sec));
                  }
                  // A chaque nouvelle information recueilli, on ajoute le fichier
                  // a la bibliotheque. Cela provoque des ajouts repetitifs, mais
                  // c'est le seul moyen trouver pour l'instant pour gérer la synchronisation
                  // entre l'ajout a la bibliotheque et le recueil des metadonnes
                  manager.ajouterMusique(audio);
               }
            }
         });

      } catch (RuntimeException ex) {
         System.out.println("Caught Exception: " + ex.getMessage());
      }

   }
}
