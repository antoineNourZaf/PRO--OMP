package parserxml;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParserXML {
    public static void main(final String[] args) {
        /*
         * Etape 1 : récupération d'une instance de la classe "DocumentBuilderFactory"
         */
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            	
        try {
            /*
             * Etape 2 : création d'un parseur
             */
            final DocumentBuilder builder = factory.newDocumentBuilder();
			
	    /*
	     * Etape 3 : création d'un Document
	     */
	    final Document document= builder.parse(new File("test.xml"));
			
	    //Affichage du prologue
	    System.out.println("*************PROLOGUE************");
	    System.out.println("version : " + document.getXmlVersion());
	    System.out.println("encodage : " + document.getXmlEncoding());		
            System.out.println("standalone : " + document.getXmlStandalone());
					
	    /*
	     * Etape 4 : récupération de l'Element racine
	     */
	    final Element racine = document.getDocumentElement();
		
	    //Affichage de l'élément racine
	    System.out.println("\n*************RACINE************");
	    System.out.println(racine.getNodeName());
		
	    /*
	     * Etape 5 : récupération des noeuds
	     */
	    final NodeList racineNoeuds = racine.getChildNodes();
	    final int nbRacineNoeuds = racineNoeuds.getLength();
            System.out.println(racineNoeuds.getLength());
          
			
	    for (int i = 0; i<nbRacineNoeuds; i++) {
                
                //permet de selectionner uniquement les noeuds du fichiers xml
	        if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    
                    
                  // on affiche les differents noeuds  
                    System.out.println(racineNoeuds.item(i).getNodeName());
                    
                    //on recupere chaque noeud (videos, audios ou playlists)
	            final Element principalNode = (Element) racineNoeuds.item(i);
     
                   			
		    /*
		     *  récupération des numéros de videos
		     */
		    final NodeList videos = principalNode.getElementsByTagName("video");
		    final int nbvideosElements = videos.getLength();
					
		    for(int j = 0; j<nbvideosElements; j++) {
		        final Element video = (Element) videos.item(j);
		    
                        //Affichage de la video
                        System.out.println(video.getAttribute("type") + " : " + video.getTextContent());
		    }
                    
                    
                    /*
		     *  récupération  des audios
		     */
                    final NodeList audios = principalNode.getElementsByTagName("audio");
		    final int nbaudiosElements = audios.getLength();
					
		    for(int j = 0; j<nbaudiosElements; j++) {
		        final Element audio = (Element) audios.item(j);
		    
                        //Affichage de l'audio
                        System.out.println(audio.getAttribute("type") + " : " + audio.getTextContent());
		    }
                    
                    /*
		     *  récupération des playlists
		     */
                    
                    final NodeList playlists = principalNode.getElementsByTagName("playlist");
		    final int nbplaylistsElements = playlists.getLength();
                    
                    
                    for(int j = 0; j<nbplaylistsElements; j++) {
		        final Element playlist = (Element) playlists.item(j);
                        final Element name = (Element) playlist.getElementsByTagName("name").item(0);
                        System.out.println( name.getTextContent());
                        
                        
                        final NodeList contents = playlist.getElementsByTagName("song");
		        final int nbcontentsElements = contents.getLength();
                        
		        for(int k = 0; k<nbcontentsElements; k++) {
		        final Element song = (Element) contents.item(k);		    
                        //Affichage du song
                        System.out.println( song.getTextContent());
		    }

		    }
                    
                
	        }				
	    }			
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
       		
    }
}
