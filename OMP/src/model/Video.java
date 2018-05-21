package model;

public class Video extends Media {
   
   private String duree;
   private String format;
   
   public Video(String path) {
      super(path);
      format = "mp4";
      duree = "Unknown";
   }
}
