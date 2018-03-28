package xmlgenerator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import javax.xml.transform.OutputKeys;
import org.w3c.dom.Node;

public class Bibliotheque {

   private DocumentBuilderFactory dbFactory;
   private DocumentBuilder dBuilder;
   private File bibliotheque;

   public Bibliotheque() {

      bibliotheque = new File("." + File.separator + "bibliotheque.xml");

      try {

         dbFactory = DocumentBuilderFactory.newInstance();
         dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.newDocument();

         // Element bibliothèque
         Element root = doc.createElement("Bibliotheque");
         doc.appendChild(root);

         // Element playlists --> biblio
         Element playlists = doc.createElement("Playlists");
         root.appendChild(playlists);

         // Element playlist --> playlists 
         Element playlist = doc.createElement("Playlist");
         playlists.appendChild(playlist);

         // Element Audios --> biblio
         Element audios = doc.createElement("Audios");
         root.appendChild(audios);

         // Element vidéos --> biblio
         Element videos = doc.createElement("Videos");
         root.appendChild(videos);

         // write the content into xml file
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
         DOMSource source = new DOMSource(doc);
         StreamResult result = new StreamResult(bibliotheque);
         transformer.transform(source, result);

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**
    * Cette fonction ajoute au fichier xml de la bibliotheque le chemin d'accès
    * a la musique que l'on veut ajouter.
    * 
    * @param file Le fichier a ajouter à la bibliotheque 
    */
   public void ajouterMusique(File file) {

      String path = file.getAbsolutePath();
      String name = file.getName();
      
      try {
         
         dbFactory = DocumentBuilderFactory.newInstance();
         dBuilder = dbFactory.newDocumentBuilder();

         Document doc = dBuilder.parse(bibliotheque);

         //Recherche du noeud audio
         Node audios = doc.getElementsByTagName("Audios").item(0);
         
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
