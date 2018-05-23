package controller;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import model.Audio;

import model.Video;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BibliothequeManager /*extends Observable*/ {

   private DocumentBuilderFactory dbFactory;
   private DocumentBuilder dBuilder;
   private Document doc;
   private int givenId;

   private final File FICHIER_XML;

   /**
    * Inscrit les modifications que l'on a effectué sur le document, dans le
    * fichier XML
    */
   private void docToXML() {

      try {
         // Ecrit le contenu dans le fichier xml
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer;

         transformer = transformerFactory.newTransformer();
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");

         doc.normalize();
         DOMSource source = new DOMSource(doc);
         StreamResult result = new StreamResult(FICHIER_XML);
         transformer.transform(source, result);
      } catch (IllegalArgumentException | TransformerException ex) {
         Logger.getLogger(BibliothequeManager.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   /**
    * Cree le bibliotheque manager qui va gérer la bibliotheque XML
    */
   public BibliothequeManager() {

      FICHIER_XML = new File("/media/bibliotheque.xml");
      
      try {

         dbFactory = DocumentBuilderFactory.newInstance();
         dBuilder = dbFactory.newDocumentBuilder();
         doc = dBuilder.newDocument();

         if (!FICHIER_XML.exists()) {
            File directory = new File("/media");
            directory.mkdir();

            // Element bibliothèque
            Element root = doc.createElement("Bibliotheque");
            doc.appendChild(root);

            // Element Audios --> biblio
            Element audios = doc.createElement("Audios");
            audios.setNodeValue("Audios");
            root.appendChild(audios);

            // Element vidéos --> biblio
            Element videos = doc.createElement("Videos");
            root.appendChild(videos);

            // Element playlists --> biblio
            Element playlists = doc.createElement("Playlists");
            root.appendChild(playlists);

            // Ecrit le contenu dans le fichier xml
            docToXML();
         }
      } catch (ParserConfigurationException | DOMException ex) {
         Logger.getLogger(BibliothequeManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      givenId = getLastId();

   }

   /**
    * Creation du fichier contenant la bibliotheque. Il s'agit d'un fichier XML
    * qui sera sauvegarder et dont nous lirons le contenu. Il n'est crée qu'une
    * seule fois dès le premier lancement du lecteur
    *
    * @throws TransformerConfigurationException
    * @throws TransformerException
    */
   private void createBibliotheque() throws TransformerConfigurationException, TransformerException {

   }

   /**
    * Cette fonction ajoute au fichier xml de la bibliotheque les details de la
    * musique que l'on veut.
    *
    * @param chanson Le fichier a ajouter à la bibliotheque
    */
   public void ajouterMusique(Audio chanson) {

      try {

         doc = dBuilder.parse(FICHIER_XML.getAbsolutePath());
         doc.normalize();
         NodeList audiosList = doc.getElementsByTagName("Audios");
         Element audio = doc.createElement("Audio");

         Node audios = audiosList.item(0);

         // Si le noeud "audios" a des elements "audio" , on verifie que la 
         // chanson existe déja dans le fichier xml, auquel cas on la supprime 
         if (audios.hasChildNodes()) {

            // On recupere les chemins
            NodeList cheminList = doc.getElementsByTagName("Chemin");

            // On regarde si un des audios possède le chemin qu'on insere
            for (int i = 0; i < cheminList.getLength(); ++i) {

               Node chemin = cheminList.item(i);

               // Si oui, on supprime le noeud <Audio> qui contient deja le chemin
               // car il est en double
               if (chemin.getTextContent().equals(chanson.getPath())) {
                  Node audioNode = chemin.getParentNode();
                  Node audiosNode = audioNode.getParentNode();

                  audiosNode.removeChild(audioNode);
                  //--givenId;
               }
            }
         }
         audios.normalize();
         audios.appendChild(audio);
         audio.setAttribute("id", String.valueOf(++givenId));

         Element cheminAcces = doc.createElement("Chemin");
         cheminAcces.setTextContent(chanson.getPath().replace("\0", ""));

         Element artiste = doc.createElement("Artiste");
         artiste.setTextContent(chanson.getArtiste().replace("\0", ""));
         Element titre = doc.createElement("Titre");
         titre.setTextContent(chanson.getTitre().replace("\0", ""));

         Element album = doc.createElement("Album");
         album.setTextContent(chanson.getAlbum().replace("\0", ""));

         Element duree = doc.createElement("Duree");
         duree.setTextContent(chanson.getDuree().replace("\0", ""));

         Element format = doc.createElement("Format");
         format.setTextContent(chanson.getFormat().replace("\0", ""));

         audio.appendChild(cheminAcces);
         audio.appendChild(titre);
         audio.appendChild(artiste);
         audio.appendChild(duree);
         audio.appendChild(album);
         audio.appendChild(format);

      } catch (IOException | DOMException | SAXException ex) {
         Logger.getLogger(BibliothequeManager.class
                 .getName()).log(Level.SEVERE, null, ex);

      }

      doc.normalizeDocument();
      docToXML();
      /*setChanged();
      notifyObservers(Controller.class.getSimpleName());*/
   }

   /**
    * Cette fonction ajoute au fichier xml de la bibliotheque les details de la
    * musique que l'on veut.
    *
    * @param chanson Le fichier a ajouter à la bibliotheque
    */
   public void ajouterVideo(Video clip) {

      try {

         doc = dBuilder.parse(FICHIER_XML.getAbsolutePath());
         doc.normalize();
         NodeList videosList = doc.getElementsByTagName("Videos");
         Element video = doc.createElement("Video");

         Node videos = videosList.item(0);

         // Si le noeud "audios" a des elements "audio" , on verifie que la 
         // chanson existe déja dans le fichier xml, auquel cas on la supprime 
         if (videos.hasChildNodes()) {

            // On recupere les chemins
            NodeList cheminList = doc.getElementsByTagName("Chemin");

            // On regarde si un des audios possède le chemin qu'on insere
            for (int i = 0; i < cheminList.getLength(); ++i) {

               Node chemin = cheminList.item(i);

               // Si oui, on supprime le noeud <Video> qui contient deja le chemin
               // car il est en double
               if (chemin.getTextContent().equals(clip.getPath())) {
                  Node videoNode = chemin.getParentNode();
                  Node videosNode = videoNode.getParentNode();

                  videosNode.removeChild(videoNode);
                  //--givenId;
               }
            }
         }
         videos.normalize();
         videos.appendChild(video);
         video.setAttribute("id", String.valueOf(++givenId));

         Element cheminAcces = doc.createElement("Chemin");
         cheminAcces.setTextContent(clip.getPath().replace("\0", ""));

         Element titre = doc.createElement("Titre");
         titre.setTextContent(clip.getTitre().replace("\0", ""));

         Element duree = doc.createElement("Duree");
         duree.setTextContent(clip.getDuree().replace("\0", ""));

         Element format = doc.createElement("Format");
         format.setTextContent(clip.getFormat().replace("\0", ""));

         video.appendChild(cheminAcces);
         video.appendChild(titre);
         video.appendChild(duree);
         video.appendChild(format);

         System.out.println("passe dans ajoutVideo");

      } catch (IOException | DOMException | SAXException ex) {
         Logger.getLogger(BibliothequeManager.class
                 .getName()).log(Level.SEVERE, null, ex);

      }

      doc.normalizeDocument();
      docToXML();
      /*setChanged();
      notifyObservers(Controller.class.getSimpleName());*/
   }

   /**
    * Retourne le dernier id donnée a un media pour ne pas en écraser un autre.
    *
    * @return le dernier id donnée pour un média ajouté
    */
   public int getLastId() {

      int lastId = -1;
      if (FICHIER_XML.exists()) {
         try {
            doc = dBuilder.parse(FICHIER_XML.getAbsolutePath());

            NodeList list = doc.getElementsByTagName("Audio");
            int length = list.getLength();

            if (length == 0) {
               return 0;
            }

            for (int i = 0; i < length; ++i) {

               Node audio = list.item(i);

               if (lastId < Integer.parseInt(audio.getAttributes().item(0).getTextContent())) {
                  lastId = Integer.parseInt(audio.getAttributes().item(0).getTextContent());

               }
            }
         } catch (IOException | NumberFormatException | SAXException ex) {
            Logger.getLogger(BibliothequeManager.class
                    .getName()).log(Level.SEVERE, null, ex);
         }
      }

      return lastId;
   }

   /**
    * Ajouter une playlist au fichier xml
    *
    * @param name
    */
   public void creerPlaylist(String name) {

      try {
         // Parser le xml
         doc = dBuilder.parse(FICHIER_XML.getAbsolutePath());

         // Creation de l'element playlist
         Element playlist = doc.createElement("Playlist");

         // Si une playlist du meme nom a déja été crée, on ne la crée pas
         NodeList titrePlaylist = doc.getElementsByTagName("TitrePlaylist");

         if (titrePlaylist.getLength() > 0) {

            for (int i = 0; i < titrePlaylist.getLength(); ++i) {
               Node titre = titrePlaylist.item(i);
               if (titre.getTextContent().equals(name)) {
                  System.err.println("Une playlist avec ce nom existe deja");
                  // Afficher message erreur indiquant qu une playlist possedant
                  // ce nom existe deja
                  return;
               }
            }
         }

         // Insertion du titre
         Element titreP = doc.createElement("TitrePlaylist");
         titreP.setTextContent(name);

         // Insertion content 
         Element content = doc.createElement("Content");

         // Rattacher <Titre> et <Content> à <Playlist> 
         playlist.appendChild(titreP);
         playlist.appendChild(content);

         // Recuperation de l'element <Playlists>
         NodeList playlistList = doc.getElementsByTagName("Playlists");
         Node playlists = playlistList.item(0);

         // Rattacher <Playlist> à <Playlists>
         playlists.appendChild(playlist);

      } catch (IOException | SAXException e) {
         Logger.getLogger(BibliothequeManager.class
                 .getName()).log(Level.SEVERE, null, e);
      }

      // Inserer le document dans le xml
      docToXML();
   }

   /**
    * Pour ajouter un media a la playlist
    *
    * @param titrePlaylist La playlist a laquelle on rajoute le media
    * @param path Le chemin du media que l'on ajoute
    */
   public void addToPlaylist(String titrePlaylist, String path) {

      try {
         // Parser le xml
         doc = dBuilder.parse(FICHIER_XML.getAbsolutePath());
         doc.normalize();
         Element mediaId = doc.createElement("MediaID");
         // Recuperation de l'element <Playlists>
         NodeList audiosList = doc.getElementsByTagName("Playlists");
         Node audio = audiosList.item(0);

         // Retrouver la playlist ou on veut ajouter la chanson
         NodeList titrePList = doc.getElementsByTagName("TitrePlaylist");
         int nbPlaylist = titrePList.getLength();

         // On parcourt tout les tags <TitrePLaylist>
         for (int i = 0; i < nbPlaylist; ++i) {

            // On trouve le nom de la playlist
            if (titrePList.item(i).getTextContent().equals(titrePlaylist)) {

               Node titreP = titrePList.item(i);
               // On recupere le parent <Playlist>
               Node playlist = titreP.getParentNode();

               NodeList contents = playlist.getChildNodes();
               // On choisit la balise <Content> a laquelle on ajoute le noeud
               // fils <MediaID>
               for (int k = 0; k < contents.getLength(); ++k) {
                  if (contents.item(k).getNodeName().equals("Content")) {
                     Node content = contents.item(k);

                     mediaId.setTextContent(getId(path));
                     playlist.appendChild(content);
                     content.appendChild(mediaId);

                     doc.normalize();
                     docToXML();
                  }
               }

            }
         }

      } catch (IOException | SAXException e) {

      }
   }

   /**
    * Retourne l'id d'un fichier choisi
    *
    * @param path Le chemin du fichier dont il faut retourner l'id
    * @return l'id du fichier
    */
   public String getId(String path) {
      String id = "";
      try {

         // Parser le xml
         doc = dBuilder.parse(FICHIER_XML.getAbsolutePath());
         doc.normalize();

         // Retrouver la chanson dont a le chemin
         NodeList cheminList = doc.getElementsByTagName("Chemin");
         int nbElements = cheminList.getLength();

         // Pour chaque chemin obtenu
         for (int i = 0; i < nbElements; ++i) {

            Node chemin = cheminList.item(i);
            // Si les deux chemins sont identiques
            if (chemin.getTextContent().equals(path)) {

               // On recupere le noeud parent donc <Audio>
               Node audio = chemin.getParentNode();

               // Puis on recupere les attributs (ici id), et son contenu.
               return audio.getAttributes().item(0).getTextContent();

            }
         }

      } catch (SAXException | IOException e) {
         Logger.getLogger(BibliothequeManager.class
                 .getName()).log(Level.SEVERE, null, e);
      }
      // Si on arrive ici, il y a un soucis
      return id;
   }

   /**
    * Pour supprimer un media de la bibliotheque
    *
    * @param path Chemin du fichier a supprimer de la bibliotheque
    */
   public void deleteMedia(String path) {

      try {
         doc = dBuilder.parse(FICHIER_XML.getAbsolutePath());

         // On prends tout les chemins
         NodeList listeChemin = doc.getElementsByTagName("Chemin");

         // On trouve celui que l'on veut supprimer
         int length = listeChemin.getLength();

         for (int i = 0; i < length; ++i) {

            Node chemin = listeChemin.item(i);

            // Suppression du media (Valable pour Audio et Video)
            if (chemin.getTextContent().equals(path)) {
               Node audio = chemin.getParentNode();
               Node audios = audio.getParentNode();
               audios.removeChild(audio);
            }
         }
         docToXML();

      } catch (IOException | SAXException e) {
         // Fenetre pop-Up
      }
   }

   public void deletePlaylist(String nomPlaylist) {

      try {
         doc = dBuilder.parse(FICHIER_XML.getAbsolutePath());

         // On prends tout les titres de playlists
         NodeList listeTitreP = doc.getElementsByTagName("TitreP");

         // On cherche celle à effacer
         int length = listeTitreP.getLength();

         for (int i = 0; i < length; ++i) {

            Node titre = listeTitreP.item(i);
            // Une fois qu on a trouve la playlist avec le titre recherche on l'
            // efface
            if (titre.getTextContent().equals(nomPlaylist)) {

               Node playlist = titre.getParentNode();
               Node playlists = playlist.getParentNode();
               playlists.removeChild(playlist);
            }
         }
         docToXML();
      } catch (IOException | SAXException e) {
         // Fenetre pop-up
      }
   }

   public String getPath(String id) {

      String path = "";

      try {
         doc = dBuilder.parse(FICHIER_XML.getAbsolutePath());

         // Recupere tout les elements audios
         NodeList audioList = doc.getElementsByTagName("Audio");
         int length = audioList.getLength();

         // Pour chaque Audio, on regarde son id
         for (int i = 0; i < length; ++i) {

            Node audio = audioList.item(i);
            Node idAudio = audio.getAttributes().item(0);

            // Si l'audio a l'id recherché, on recupere son chemin d'acces
            if (idAudio.getTextContent().equals(id)) {
               Node chemin = audio.getFirstChild();
               return chemin.getTextContent();
            }
         }
      } catch (Exception e) {
         //Fenetre pop-up
      }

      return path;
   }

   public String suivant(String musiqueActuelle) {

      String suivant = "";

      NodeList chemins = doc.getElementsByTagName("Chemin");

      // Parcours de la liste
      for (int i = 0; i < chemins.getLength(); ++i) {
         Node chemin = chemins.item(i);

         // si on trouve la musique actuelle, on retourne la musique suivante
         if (chemin.getTextContent().equals(musiqueActuelle)) {
            suivant = chemins.item((i + 1) % chemins.getLength()).getTextContent();
         }
      }
      return suivant;
   }

   public String precedant(String musiqueActuelle) {

      String precedant = "";

      NodeList chemins = doc.getElementsByTagName("Chemin");

      // Parcours de la liste
      for (int i = 0; i < chemins.getLength(); ++i) {

         Node chemin = chemins.item(i);

         // si on trouve la musique actuelle, on retourne la musique suivante
         if (chemin.getTextContent().equals(musiqueActuelle)) {

            if (i == 0) {
               i = chemins.getLength();
            }

            precedant = chemins.item((i - 1)).getTextContent();
         }
      }
      return precedant;
   }
}
