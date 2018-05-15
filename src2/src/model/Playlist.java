package model;

import java.util.ArrayList;

public class Playlist {
   
   private ArrayList<Integer> idMedias;
   private String nomPlaylist;
   
   public Playlist(String nom) {
      idMedias    = new ArrayList<>();
      nomPlaylist = nom;
   }
   
   public Playlist() {
      idMedias    = new ArrayList<>();
      nomPlaylist = "Sans titre";
   }
   
   public void changerNom(String nom) {
      nomPlaylist = nom;
   }
   
   public String getNom() {
      return nomPlaylist;
   }
   
   
   
   public ArrayList<Integer> getContenu() {
      return idMedias;
   }
}
