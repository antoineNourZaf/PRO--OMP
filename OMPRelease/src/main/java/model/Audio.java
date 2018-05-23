package model;

import controller.BibliothequeManager;

public class Audio {

   private String cheminAcces;
   private String titre;
   private String artiste;
   private String album;
   private String duree;
   private String format;
   private String annee;
   
   public Audio(String path) {
      cheminAcces = path;
      titre = "Sans titre";
      artiste = "artiste inconnu";
      album   = "";
      duree = "";
      annee = "";
      format = "";
   }
   
  
  public String getPath() {
     return cheminAcces;
  }

   public String getAlbum() {

      return album;
   }

   public String getAnnee() {
      return annee;
   }

   public String getDuree() {
      return duree;
   }

   public String getArtiste() {

      return artiste;
   }
   
   public String getFormat() {
      return format;
   }
   
   public void setCheminAccès(String path) {
      cheminAcces = path;
   }

   public void setDuree(String duree) {
      this.duree = duree;
   }

   public void setArtiste(String artiste) {
      this.artiste = artiste;
   }

   public void setAlbum(String album) {
      this.album = album;
   }
   
   public void setAnnee(String annee) {
      this.annee = annee;
   }
   public void setTitre(String titre) {
      this.titre = titre;
   }
   
   public String getTitre() {
      return titre;
   }
   public void setFormat() {
      
      if (cheminAcces.contains("mp3"))
         format = "mp3";
      else if (cheminAcces.contains("aiff"))
         format = "aiff";
      else if (cheminAcces.contains("wav"))
         format = "wav";
      else if (cheminAcces.contains("aac"))
         format = "aac";
      else
         format = "format non trouvé";
   }
   
   @Override
   public String toString() {
      return  "titre   :" + titre + "\n"
              + "artiste :" + artiste + "\n"
              + "album   :" + album + "\n"
              + "annee   :" + annee + "\n"
              + "duree   :" + duree + "\n"
              + "chemin  :" + cheminAcces + "\n"
              + "Format  :" + format + "\n";
   }
   
   
}
