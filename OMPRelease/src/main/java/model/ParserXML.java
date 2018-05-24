package model;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParserXML {

   private NodeList audios;
   private NodeList videos;
   private NodeList playlists;
   private ArrayList<Element> audiosDonnees;
   private ArrayList<Element> videosDonnees;
   private ArrayList<Element> playlistsDonnees;
   private ArrayList<Element> tabAudios = new ArrayList<>();
   private ArrayList<Element> tabVideos = new ArrayList<>();
   
   public ParserXML() {
      /*
         * récupération d'une instance de la classe "DocumentBuilderFactory"
       */
      final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

      try {
         /*
             *  création d'un parseur
          */
         final DocumentBuilder builder = factory.newDocumentBuilder();

         /*
	     *  création d'un Document
          */
         final Document document = builder.parse(new File("bibliotheque/bibliotheque.xml"));

         /*
	     *  récupération de l'Element racine
          */
         final Element racine = document.getDocumentElement();

         /*
	     *  récupération des noeuds
          */
         final NodeList racineNoeuds = racine.getChildNodes();
         final int nbRacineNoeuds = racineNoeuds.getLength();

         for (int i = 0; i < nbRacineNoeuds; i++) {

            //permet de selectionner uniquement les noeuds du fichiers xml
            if (racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {

               //on recupere chaque noeud (videos, audios ou playlists)
               final Element principalNode = (Element) racineNoeuds.item(i);

               // récupération des numéros de videos
               videos = principalNode.getElementsByTagName("Video");
               videosDonnees = mesVideos(videos);

               // récupération  des audios
               audios = principalNode.getElementsByTagName("Audio");

               audiosDonnees = mesAudios(audios);

               //récupération des playlists
               playlists = principalNode.getElementsByTagName("Playlist");

               playlistsDonnees = mesPlaylist(playlists);

            }
         }
      } catch (final Exception e) {
         e.printStackTrace();
      }

   }

   public ArrayList<Element> mesPlaylist(NodeList nodeplaylist) {

      final int nbplaylistsElements = nodeplaylist.getLength();

      for (int j = 0; j < nbplaylistsElements; j++) {
         final Element playlist = (Element) nodeplaylist.item(j);
         final Element name = (Element) playlist.getElementsByTagName("Name").item(0);
         //System.out.println(name.getTextContent());

         final NodeList contents = playlist.getElementsByTagName("MediaID");
         final int nbcontentsElements = contents.getLength();

         for (int k = 0; k < nbcontentsElements; k++) {
            final Element song = (Element) contents.item(k);
            //ajouter a playlist
            //Affichage du song
            //System.out.println(song.getTextContent());
         }

      }
      return new ArrayList<Element>();
   }

   public ArrayList<Element> mesVideos(NodeList nodevideos) {

      final int nbvideosElements = videos.getLength();
     
      for (int j = 0; j < nbvideosElements; j++) {

         tabVideos.add((Element) nodevideos.item(j));

         System.out.println("\n========================================");
         final Element audio = (Element) nodevideos.item(j);
         //System.out.println("identifiant : " + audio.getAttribute("id"));

         final Element titre = (Element) audio.getElementsByTagName("Titre").item(0);
         //System.out.println("titre : " + titre.getTextContent());

         final Element format = (Element) audio.getElementsByTagName("Format").item(0);
         //System.out.println("format : " + format.getTextContent());


         final Element duree = (Element) audio.getElementsByTagName("Duree").item(0);
         //System.out.println("duree : " + duree.getTextContent());

      }

      return tabVideos;
   }

   public ArrayList<Element> mesAudios(NodeList nodeaudios) {

      final int nbaudiosElements = nodeaudios.getLength();

      for (int j = 0; j < nbaudiosElements; j++) {

         tabAudios.add((Element) nodeaudios.item(j));

         System.out.println("\n========================================");
         final Element audio = (Element) nodeaudios.item(j);
         //System.out.println("identifiant : " + audio.getAttribute("id"));

         final Element titre = (Element) audio.getElementsByTagName("Titre").item(0);
         //System.out.println("titre : " + titre.getTextContent());

         final Element format = (Element) audio.getElementsByTagName("Format").item(0);
         //System.out.println("format : " + format.getTextContent());

         final Element artiste = (Element) audio.getElementsByTagName("Artiste").item(0);
         //System.out.println("artiste : " + artiste.getTextContent());

         final Element duree = (Element) audio.getElementsByTagName("Duree").item(0);
         //System.out.println("duree : " + duree.getTextContent());

         final Element album = (Element) audio.getElementsByTagName("Album").item(0);
         //System.out.println("album : " + album.getTextContent());

         //final Element annee = (Element) audio.getElementsByTagName("Annee").item(0);
         //System.out.println("annee : " + annee.getTextContent());
         //final Element image = (Element) audio.getElementsByTagName("image").item(0);
         //System.out.println("image : " + image.getTextContent());
      }

      return tabAudios;

   }

   public ArrayList<Element> getAudiosDonnees() {
      //System.out.println(audiosDonnees.get(0).toString());
      return audiosDonnees;
   }

   public ArrayList<Element> getVideosDonnees() {
      return videosDonnees;
   }

   public ArrayList<Element> getPlaylistsDonnees() {
      return playlistsDonnees;
   }
}
