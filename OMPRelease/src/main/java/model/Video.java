package model;

public class Video {
   
   private String titre;
   private String duree;
   private String format;
   private String path;
   
   public Video(String path) {
      titre = "Sans Titre";
      
      if (path.contains("mp4"))
         format = "mp4";
      else if (path.contains("flv"))
         format = "flv";
      else
         format = "format inconnu";
      
      duree = "Unknown";
      this.path = path;
   }
   
   public String getPath() {
      return path;
   }
   
   public String getTitre() {
      return titre;
   }
   
   public void setTitre(String titre){
      this.titre = titre;
   }
   
   public void setDuree(String duree){
      this.duree = duree;
   }
   
   public String getFormat() {
      return format;
   }
   
   public String getDuree() {
      return duree;
   }
   
}
