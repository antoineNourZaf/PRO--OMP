package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.*;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;

public class Controller {

    public TextFlow affichage;
    public Button searchButton;
    public TextField entreeUtilisateur;
    public TextArea affichagesResultats;
    public TextArea affichageAmis;
    public TextArea affichageInfos;
    public TextField playlistOuInsere;
    public Button afficheInfos;

    public TableView tableView;
    public TableColumn Identifiant;
    public TableColumn Artiste;
    public TableColumn Format;
    public TableColumn TypeMedia;
    public TableColumn Duree;
    public TableColumn CheminAcces;
    public TableColumn Titre;
    public TableColumn Album;
    public TableColumn Annee;

    public MenuBar FileMenu; // barre de menu

    public TreeView PlaylistTree;
    public TreeView PisteTree;
    public TreeView AlbumTree;
    public TreeView ArtisteTree;

    public StackPane StackPane;
    public VBox Vbox;
    public MenuItem ajoutPlaylist;
    public MenuItem ouvertureDeFichier;
    public Menu ajouterMediaAplaylist;



    private BibliothequeManager bibli;
    private int nbOfPlaylists;

    private ArrayList<String> playlistsAjoutees;
    private String rechercheDemandee;

    private ParserXML parser;

    private String cheminFichier;

    /*
    * Fonction permettant d'initialiser tout les composant de l'interface lors du lancement de l'application
    *
    */

    @FXML
    public void initialize() {
        initializeBibli();
        createTreeViews();
        setColumsOfTableView();
    }

    /*
     * Fonction permettant d'initialiser la bibliotheque de l'interface lors du lancement de l'application
    *
    */

    private void initializeBibli() {
        bibli = new BibliothequeManager();
        playlistsAjoutees = new ArrayList<>();
    }
    /*
     * Fonction permettant d'ajouter un media de la bibliotheque a une playlist lors de la selection d'un media de la
     * bibliotheque ainsi que d'une playlist existante dans le menu "Ajouter meid a playlist..."
     *
     * @param actionEvent
     *
     */
    public void ajouterUnMediaAUnePlaylist(ActionEvent actionEvent) {

        Menu menu = (Menu) actionEvent.getSource();
        MediaExtracted extracted = (MediaExtracted) tableView.getSelectionModel().getSelectedItem();

        String cheminTitre = extracted.getCheminAcces();
        TreeItem item = (TreeItem) PlaylistTree.getSelectionModel().getSelectedItem();
        String cheminPlaylist = (String) item.getValue();

        bibli.addToPlaylist(cheminPlaylist, cheminTitre);

    }

    /*
   * Fonction permettant de lire une playlist lors d'un double click
   *
   *@param mouseEvent
   */
    public void playlistSelectedToRead(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            MenuItem playlistSelectionnee = (MenuItem) PlaylistTree.getSelectionModel().getSelectedItem();
            String nomplaylistSelectionnee = playlistSelectionnee.getText(); //nom de la playlist selectionnee
            System.out.println("Double click sur " + nomplaylistSelectionnee);

            ArrayList<String> identifiantsDesPlayistALire = new ArrayList<>(); // recuperer les ids des audio de la playlist selectionnee
            //TODO -> lancer le lecteur de playlist
            //TODO -> a implementer pour la version 2.0 du programme OMP
        }
    }

    /*
   * Fonction permettant d'ajouter un fichier a la bibliotheque depuis le le menu
   * Cette fonction fait appelle a la classe FileSystemOpen pour ouvrir un nouvelle fenetre de selection de fichier
   *@param mouseEvent
   */
    public void ajouterFichierDepuisMenu(ActionEvent actionEvent) {
        try {

         boolean fichierVideo;
         // on recupere le chemin du fichier
         cheminFichier = new FileSystemOpen().run();

         // On recupere le titre du media
         String titre = "";
         if (cheminFichier.contains("\\")) {
            titre = cheminFichier.substring(cheminFichier.lastIndexOf("\\")+1);
         } else {
            titre = cheminFichier.substring(cheminFichier.lastIndexOf("/")+1);
         }

         // On regarde si c est une video ou un audio
         fichierVideo = cheminFichier.contains("mp4") || cheminFichier.contains("flv");

         if (fichierVideo) {
            System.out.println("Fichier video ajouté");
            Video video = new Video(cheminFichier);
            video.setTitre(titre);
            Media media = new Media(new File(cheminFichier).toURI().toString());
            Thread.sleep(200);
            
            // il est possible que la durée ne soit pas encore disponible au moment
            // ou ces instructions sont exécutés
            if (!media.getDuration().isIndefinite() && !media.getDuration().isUnknown()) {

               double minute = media.getDuration().toMinutes() % 60;
               double secondes = media.getDuration().toSeconds() % 60;

               int min = (int) minute;
               int sec = (int) secondes;

               video.setDuree(String.format("%02d:%02d", min, sec));
            }
            bibli.ajouterVideo(video);
            Thread.sleep(400);
         } 
         else {

            DataExtracteur md = new DataExtracteur(cheminFichier);
            Audio audio = new Audio(cheminFichier);

            audio.setFormat();

            // On peut seulement obtenir les metadatas des fichiers mp3
            if (cheminFichier.contains("mp3")) {
               md.ExtractionMetadata(cheminFichier, audio, bibli);
            } else {

               audio.setTitre(titre);
               audio.setFormat();
               bibli.ajouterMusique(audio);

            }
         }
         Thread.sleep(400);
         updateTableView();

      } catch (Exception e) {
         e.printStackTrace();
      }
    }


    public int nbFichiersAudios;

    ArrayList<String> dernierContenuJoues = new ArrayList<>();

    /*
   * Fonction qui initialise les differentes colonne de la bibliotheque, avec pour chaque colonne un des champs
   * extrait des metadonnes de chaque fichier qu'on ajoute a la bibliotheque
   *
   *
   */
    public void setColumsOfTableView() {
        parser = new ParserXML();

        Identifiant = new TableColumn<MediaExtracted, String>("Id");
        Identifiant.setPrefWidth(100);
        Identifiant.setMinWidth(20);
        Identifiant.setCellValueFactory(new PropertyValueFactory<>("id")); // parametre artiste de MediaExtracted

        Titre = new TableColumn<MediaExtracted, String>("Titre");
        Titre.setPrefWidth(100);
        Titre.setMinWidth(20);
        Titre.setCellValueFactory(new PropertyValueFactory<>("titre")); // parametre nom de MediaExtracted

        Format = new TableColumn<MediaExtracted, String>("Format");
        Format.setPrefWidth(100);
        Format.setMinWidth(20);
        Format.setCellValueFactory(new PropertyValueFactory<>("format")); // parametre format de MediaExtracted

        Artiste = new TableColumn<MediaExtracted, String>("Artiste");
        Artiste.setPrefWidth(100);
        Artiste.setMinWidth(20);
        Artiste.setCellValueFactory(new PropertyValueFactory<>("artiste")); // parametre artiste de MediaExtracted

        Duree = new TableColumn<MediaExtracted, String>("Duree");
        Duree.setPrefWidth(100);
        Duree.setMinWidth(20);
        Duree.setCellValueFactory(new PropertyValueFactory<>("duree")); // parametre nom de MediaExtracted

        Album = new TableColumn<MediaExtracted, String>("Album");
        Album.setPrefWidth(100);
        Album.setMinWidth(20);
        Album.setCellValueFactory(new PropertyValueFactory<>("album")); // parametre nom de MediaExtracted

        Annee = new TableColumn<MediaExtracted, String>("Annee");
        Annee.setPrefWidth(100);
        Annee.setMinWidth(20);
        Annee.setCellValueFactory(new PropertyValueFactory<>("annee")); // parametre nom de MediaExtracted

        TypeMedia = new TableColumn<MediaExtracted, String>("TypeMedia");
        TypeMedia.setPrefWidth(100);
        TypeMedia.setMinWidth(20);
        TypeMedia.setCellValueFactory(new PropertyValueFactory<>("typeMedia")); // parametre typeMedia de MediaExtracted

        CheminAcces = new TableColumn<MediaExtracted, String>("CheminAcces");
        CheminAcces.setPrefWidth(100);
        CheminAcces.setMinWidth(20);
        CheminAcces.setCellValueFactory(new PropertyValueFactory<>("cheminAcces")); // parametre nom de MediaExtracted

        tableView.setItems(getExtractedMedia());
        tableView.getColumns().addAll(Identifiant, Titre, Format, Artiste, Duree, Album, Annee, TypeMedia, CheminAcces);
    }

    /*
       * Fonction mettant a jour l'affichage de la bibliotheque
       * Cette fonction est appelle pour permettre la synchronisation entre l'affichage d'un nouveau media ajoute
       * et son ajout effectif dans la bibliotheque (bibliotheque.xml)
       *
       *
       */
    public void updateTableView() {

        try {
            parser = new ParserXML();
            Thread.sleep(500); // force un temps d'attente pour permettre la synchronisation
            tableView.setItems(getExtractedMedia());
        } catch (InterruptedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*
   * Fonction permettant de recuperer toute les metadonnees extraites du fichier et de les afficher sur l'interface
    * en ajoutant a chaque colonne la metadonnee correspondante
   *
   */
    public ObservableList<MediaExtracted> getExtractedMedia() {
        ObservableList<MediaExtracted> extractedmedias = FXCollections.observableArrayList();
        ArrayList<Element> audio = parser.getAudiosDonnees();
        ArrayList<Element> video = parser.getVideosDonnees();
        System.out.println(video.size());
        if (audio == null) {
            return extractedmedias;
        } else {

            // on itere sur tout les audios contenus dans la bibliotheque pour les ajouter
            for (int i = 0; i < audio.size(); i++) {
                Element identifiant = (Element) audio.get(i);
                String id = identifiant.getAttribute("id");
                Element titre = (Element) audio.get(i).getElementsByTagName("Titre").item(0);
                Element format = (Element) audio.get(i).getElementsByTagName("Format").item(0);
                Element artiste = (Element) audio.get(i).getElementsByTagName("Artiste").item(0);
                Element duree = (Element) audio.get(i).getElementsByTagName("Duree").item(0);
                Element album = (Element) audio.get(i).getElementsByTagName("Album").item(0);
                //Element annee = (Element) audio.get(i).getElementsByTagName("Annee").item(0);
                String typeMedia;
                Element cheminAcces = (Element) audio.get(i).getElementsByTagName("Chemin").item(0);

                // si c'est un format de type audio affiche le type audio
                if (format.getTextContent().equals("mp3") || format.getTextContent().equals("aac")) {
                    typeMedia = "audio";

                } // si c'est un format de type video affiche le type video
                else if (format.getTextContent().equals("mp4") || format.getTextContent().equals("flv")) {
                    typeMedia = "video";

                } //sinon affiche que le type n'est pas reconnu
                else {
                    typeMedia = "unknown media";

                }

                MediaExtracted media = new MediaExtracted(
                        id, titre.getTextContent(), format.getTextContent(), artiste.getTextContent(),
                        duree.getTextContent(), album.getTextContent(), "", "image vide", typeMedia,
                        cheminAcces.getTextContent());
                extractedmedias.add(media);
            }

            // on itere sur toute les videos contenus dans la bibliotheque pour les ajouter
            for (int i = 0; i < video.size(); ++ i) {
                Element identifiant = (Element) video.get(i);
                String id = identifiant.getAttribute("id");
                Element titre = (Element) video.get(i).getElementsByTagName("Titre").item(0);
                Element format = (Element) video.get(i).getElementsByTagName("Format").item(0);
                Element duree = (Element) video.get(i).getElementsByTagName("Duree").item(0);
                String typeMedia = "video";
                Element cheminAcces = (Element) video.get(i).getElementsByTagName("Chemin").item(0);

                MediaExtracted media = new MediaExtracted(
                        id, titre.getTextContent(), format.getTextContent(), "",
                        duree.getTextContent(),"",  "", "", "video",
                        cheminAcces.getTextContent());
                extractedmedias.add(media);
            }
        }

        return extractedmedias;
    }

    /*
     * Fonction permettant de recuperer toute les metadonnees extraites du fichier et de les afficher sur l'interface
     * en ajoutant a chaque colonne la metadonnee correspondante
     *
    */
    public void tableViewCliqued(MouseEvent click) {

        MediaExtracted extracted = (MediaExtracted) tableView.getSelectionModel().getSelectedItem();

        // cas ou on double click sur un media de la bibliotheque
        if (click.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != tableView.getColumns().get(0)) {

            try {
                //ajoute le media a la liste des dernier medias joues
                dernierContenuJoues.add(extracted.getTitre());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mediaPlayerView.fxml"));
                Stage stage = new Stage();
                Parent root = (Parent) loader.load();

                // lance le lecteur a partir du chemin d'acces du media selectionne
                MediaPlayerViewController controller = loader.getController();
                controller.setMedia(extracted.getCheminAcces());
                controller.setBibliotheque(bibli);
                loader.setController(controller);

                Scene scene = new Scene(root);

                stage.setScene(scene);

                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /*
        * Fonction d'afficher les dernier contenu lu par l'utilisateur, ainsi celui ci peut retrouver
        * plus facilement les derniers médias qu'il a joué
        *
       */
    public void derniersContenusAjoutes(ActionEvent actionEvent) {

        try {
            if (dernierContenuJoues.isEmpty()) {
                affichageAmis.setText("\n\nPas de contenu ! \n\n");
                affichageAmis.appendText("Vous pouvez ajouter un media \n a votre librairie avec \"Ouvrir un fichier\"  !");
            } else {
                affichageAmis.setText("\n");
                for (String f : dernierContenuJoues) {
                    affichageAmis.appendText(f + "\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur Affichage contenu");
        }

    }

    /*
        * Fonction qui va permmetre de creer des playlist dans la bibliotheque de l'utilisateur et sur l'affichage
        * de l'interface graphique
        *
        * @param actionEvent
        *
       */
    public void ajouterPlaylist(ActionEvent actionEvent) {
        //ajout de nouvelle playlist a la treeView

        //MediaExtracted extracted = (MediaExtracted) tableView.getSelectionModel().getSelectedItem();
        //PlaylistTree.getTreeItem(1).getChildren().add(extracted);
        TreeItem<String> newPlaylist = new TreeItem<String>("playlist" + (++nbOfPlaylists));
        PlaylistTree.getRoot().getChildren().add(newPlaylist);
        playlistsAjoutees.add(newPlaylist.getValue());
        ajouterMediaAplaylist.getItems().add(new MenuItem(newPlaylist.getValue()));

        bibli.creerPlaylist(newPlaylist.getValue());

    }


    /*
   * Fonction permettant de gerer la recherche des metadonnes correspondante dans la bibliotheque en fonction
   * de l'entree utilisateur
   *
   * @param actionEvent
   */
    public void searchButtonClicked(ActionEvent actionEvent) {
        affichagesResultats.setText("");
        rechercheDemandee = entreeUtilisateur.getText();

        // TODO -> permettre la recherche des metadonnes correspondante dans la bibliotheque en
        // TODO -> fonction de l'entree utilisateur
        // TODO -> a implementer pour la version 2.0 du programme OMP

    }
    /*
       * Fonction qui va creer la TreeView pour afficher les playlists dans le panneau a gauche de l'interface
       *
       */
    public void createTreeViews() {

        Vbox = new VBox();

        // Creation d'un TreeView representant les playlists
        //Playlist
        TreeItem<String> rootItemP = new TreeItem<String>("Playlists");
        rootItemP.setExpanded(true);

        PlaylistTree = new TreeView<String>(rootItemP);
        PlaylistTree.setEditable(true);

        // Ci dessous on offre la possibilite pour une future mise a jour du programme de permettre la creation
        // de TreeView representant les artistes et les albums
        // TODO -> a implementer pour la version 2.0 du programme OMP
        // TODO -> ceci est une fonctionnalite optionnelle a ajouter dans le futur

        //Artistes
      /*TreeItem<String> rootItemArt = new TreeItem<String> ("Artistes");
        rootItemArt.setExpanded(true);
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<String> ("artiste " + i);
            rootItemArt.getChildren().add(item);
        }
        ArtisteTree = new TreeView<String> (rootItemArt);


        //Albums
        TreeItem<String> rootItemAlb = new TreeItem<String> ("Albums");
        rootItemAlb.setExpanded(true);
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<String> ("albums " + i);
            rootItemAlb.getChildren().add(item);
        }

        AlbumTree = new TreeView<String> (rootItemAlb);

       */
        Vbox.getChildren().addAll(PlaylistTree);

        StackPane.getChildren().add(Vbox);

    }

    /*
      * Fonction permettant de detecter la recherche des metadonnes par l'utilsateur et executer
      *
      * @param actionEvent
      */
    public void rechercheDemandee(InputMethodEvent inputMethodEvent) {
        rechercheDemandee = entreeUtilisateur.getText();
        // TODO -> a implementer pour la version 2.0 du programme OMP
        // TODO -> ceci est une fonctionnalite optionnelle a ajouter dans le futur

    }

}
