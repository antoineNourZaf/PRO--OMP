package model;

public class Media {
  
   private String titre;
   private String chemin;
   
   public Media() {
      
      titre  = "sans titre";
      chemin = "";
   }
   
   public Media(String path) {
      titre  = "Sans titre";
      chemin = path;
   }
   
   public Media(String titre,String path) {
      
      this.titre = titre;
      chemin     = path;
   }
   
   @Override 
   public String toString() {
      return "titre :" + titre + "\n";
   }
   
   
   public final String getTitre() {
      return titre;
   }
   
   public final String getChemin() {
      return chemin;
   }
   
   public void setTitre(String titre) {
      this.titre = titre;
   }
   
   public void setChemin(String path) {
      chemin = path;
   }
}
