package Model;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class MediaPlayerModel {
   
   private int volume;
   private boolean play;
   private Duration duree;
   private MediaPlayer media;
   
   public MediaPlayerModel(MediaPlayer mp) {
      volume = 50;
      play = false;
      media = mp;
      duree = media.getStopTime();
      
   }
   public int getVolume() {
      return volume;
   }
   
   public void setVolume(int volume) {
      this.volume = volume;
   }
   
   
}
