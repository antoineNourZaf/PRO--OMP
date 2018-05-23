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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import org.w3c.dom.Element;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;

public class Controller /*implements Observer*/ {

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
   public TableColumn Genre;
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

   MenuItem choix3;
   MenuItem choix2;
   MenuItem choix1;

   ContextMenu contextMenu;
   AnchorPane anchorPane;
   @FXML
   private TextField rechercheLabel;
   public static boolean getLoggedIn = false;

   private Button Playlists;
   private Recherche recherches;

   private BibliothequeManager bibli;
   private int nbOfPlaylists;

   private ArrayList<String> playlistsAjoutees;

   @FXML
   public void initialize() {
      initializeBibli();
      createTreeViews();
      setColumsOfTableView();
      //initializePopUp();
   }

   String rechercheDemandee;

   ParserXML parser;

   private String cheminFichier;

   private void initializeBibli() {
      bibli = new BibliothequeManager();
      playlistsAjoutees = new ArrayList<>();
   }

   private void initializePopUp() {

      Stage dialog = new Stage();
      dialog.initModality(Modality.APPLICATION_MODAL);
      VBox dialogVbox = new VBox(20);
      dialogVbox.getChildren().add(new Text("This is a Dialog"));
      Scene dialogScene = new Scene(dialogVbox, 300, 200);
      dialog.setScene(dialogScene);
      dialog.show();

   }

   public void ajouterUnMediaAUnePlaylist(ActionEvent actionEvent) {

      Menu menu = (Menu) actionEvent.getSource();
      MediaExtracted extracted = (MediaExtracted) tableView.getSelectionModel().getSelectedItem();

      String cheminTitre = extracted.getCheminAcces();
      TreeItem item = (TreeItem) PlaylistTree.getSelectionModel().getSelectedItem();
      String cheminPlaylist = (String) item.getValue();

      bibli.addToPlaylist(cheminPlaylist, cheminTitre);
      /*for(MenuItem item : menu.getItems()){
            //if(item.getText().equalsIgnoreCase("playlist1")){

            // On peut seulement obtenir les metadatas des fichiers mp3
            if (cheminFichier.contains("mp3")) {
                md.ExtractionMetadata(cheminFichier,audio, bibli);
            }
            else {
                audio.setTitre(cheminFichier);
                bibli.ajouterMusique(audio);
            }
            updateTableView();


        }catch (Exception e) {
            e.printStackTrace();
        }*/
      System.out.println("fin Ajout playlist");
   }
// lire la playlist lors d'un double click

   public void playlistSelectedToRead(MouseEvent mouseEvent) {
      if (mouseEvent.getClickCount() == 2) {
         MenuItem playlistSelectionnee = (MenuItem) PlaylistTree.getSelectionModel().getSelectedItem();
         String nomplaylistSelectionnee = playlistSelectionnee.getText(); //nom de la playlist selectionnee
         System.out.println("Double click sur " + nomplaylistSelectionnee);

         ArrayList<String> identifiantsDesPlayistALire = new ArrayList<>(); // recuperer les ids des audio de la playlist selectionnee
         //lancer le lecteur de playlist de Zack
      }
   }

   public void ajouterFichierDepuisMenu(ActionEvent actionEvent) {
      openFileSystem(actionEvent);

   }

   /*@Override
    public void update(Observable o, Object arg) {
        if(arg.equals(BibliothequeManager.class.getSimpleName())){
            synchronized (this) {
                parser = new ParserXML();
            }
        }
    }*/
   enum CATEGORIE {
      PLAYLISTS, PISTE, ARTISTES, ALBUMS
   }

   public int nbFichiersAudios;
   private boolean isInPlaylists = false, isInArtistes = false, isInPiste = false, isInAlbums = false;
   //AdvancedMedia media = new AdvancedMedia();

   ArrayList<String> dernierContenuJoues = new ArrayList<>();

   public void setColumsOfTableView() {
      parser = new ParserXML();
      //createTreeViews();

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

   // update tableview on case where
   public void updateTableView() {

      try {
         parser = new ParserXML();
         Thread.sleep(500);
         tableView.setItems(getExtractedMedia());
      } catch (InterruptedException ex) {
         Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
      }

   }

   public ObservableList<MediaExtracted> getExtractedMedia() {
      ObservableList<MediaExtracted> extractedmedias = FXCollections.observableArrayList();
      ArrayList<Element> audio = parser.getAudiosDonnees();
      ArrayList<Element> video = parser.getVideosDonnees();
      System.out.println(video.size());
      if (audio == null) {
         return extractedmedias;
      } else {
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

            System.out.println(id + titre.getTextContent() + format.getTextContent() + artiste.getTextContent()
                    + duree.getTextContent() + album.getTextContent() + "" + "image vide" + typeMedia
                    + cheminAcces.getTextContent());
            MediaExtracted media = new MediaExtracted(
                    id, titre.getTextContent(), format.getTextContent(), artiste.getTextContent(),
                    duree.getTextContent(), album.getTextContent(), "", "image vide", typeMedia,
                    cheminAcces.getTextContent());
            extractedmedias.add(media);
         }
         
         for (int i = 0; i < video.size(); ++ i) {
            Element identifiant = (Element) video.get(i);
            String id = identifiant.getAttribute("id");
            Element titre = (Element) video.get(i).getElementsByTagName("Titre").item(0);
            Element format = (Element) video.get(i).getElementsByTagName("Format").item(0);
            Element duree = (Element) video.get(i).getElementsByTagName("Duree").item(0);
            String typeMedia = "video";
            Element cheminAcces = (Element) video.get(i).getElementsByTagName("Chemin").item(0);
            
            System.out.println(id + titre.getTextContent() + format.getTextContent()
                    + duree.getTextContent()  + "" + "image vide" + typeMedia
                    + cheminAcces.getTextContent());
            
            MediaExtracted media = new MediaExtracted(
                    id, titre.getTextContent(), format.getTextContent(), "",
                    duree.getTextContent(),"",  "", "", "video",
                    cheminAcces.getTextContent());
            extractedmedias.add(media);
         }
      }

      return extractedmedias;
   }

   /*public String updateListView(){
        String pathToOpen;
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    @SuppressWarnings("rawtypes")
                    MediaExtracted extracted = (MediaExtracted) tableView.getSelectionModel().getSelectedCells().get(0);

                    System.out.println("lire media !");

                }
            }
        });
        return "";
    }*/
   public void tableViewCliqued(MouseEvent click) {
      MediaExtracted extracted = (MediaExtracted) tableView.getSelectionModel().getSelectedItem();
      if (click.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != tableView.getColumns().get(0)) {

         //Media media;
         //MediaPlayer mediaPlayer;
         //File filestring = new File(extracted.getCheminAcces());
         //media = new Media(filestring.toURI().toString());
         //mediaPlayer = new MediaPlayer(media);
         try {
            dernierContenuJoues.add(extracted.getTitre());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/mediaPlayerView.fxml"));
            Stage stage = new Stage();
            Parent root = (Parent) loader.load();

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

      if (click.getButton() == (MouseButton.SECONDARY)) {

         System.out.println("Menu !");

         initializePopUp();

         // cree un menu une fois qu'on clique avec le clique droit
         //ContextMenu menu = new ContextMenu();
         /*grid.setVgap(4);
            grid.setHgap(10);
            grid.setPadding(new Insets(5, 5, 5, 5));
            grid.add(choix1,0,1);*/
         // menuPopUp.show(null, (int) click.getScreenX(), (int) click.getScreenY());
         // contextMenu.show(anchorPane,null, click.getScreenX(), click.getScreenY());
      }

   }

   public void shwoPopUpError(String errorMessage) {

      //POP up media non reconnu
      JOptionPane.showMessageDialog(null,
              errorMessage, "Error !", JOptionPane.ERROR_MESSAGE);
   }

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

   public void afficherInfos(ActionEvent actionEvent) {

      try {
         // si fichier selectionne affiche son image
         affichageInfos.setText("Illustration du mp3");

         //affiche image du fichier en lecture en dessous
         ShowMediaImage image = new ShowMediaImage("");
         //sinon affiche image de base du programme (png inexistant)
      } catch (Exception e) {
         System.out.println("erreur affichage infos");
      }

   }

   public void openFileSystem(ActionEvent actionEvent) {

      try {

         boolean fichierVideo;
         // on recupere le chemin du fichier
         cheminFichier = new FileSystemOpen().run();

         // On regarde si c est une video ou un audio
         fichierVideo = cheminFichier.contains("mp4") || cheminFichier.contains("flv");

         if (fichierVideo) {
            System.out.println("Fichier video ajouté");
            Video video = new Video(cheminFichier);
            Media media = new Media(new File(cheminFichier).toURI().toString());
            Thread.sleep(200);
            if (!media.getDuration().isIndefinite() && !media.getDuration().isUnknown()) {

               double minute = media.getDuration().toMinutes() % 60;
               double secondes = media.getDuration().toSeconds() % 60;

               int min = (int) minute;
               int sec = (int) secondes;

               video.setDuree(String.format("%02d:%02d", min, sec));
            }
            bibli.ajouterVideo(video);
            Thread.sleep(250);
         } else {

            DataExtracteur md = new DataExtracteur(cheminFichier);
            Audio audio = new Audio(cheminFichier);

            audio.setFormat();

            // On peut seulement obtenir les metadatas des fichiers mp3
            if (cheminFichier.contains("mp3")) {
               md.ExtractionMetadata(cheminFichier, audio, bibli);
            } else {
               audio.setTitre(cheminFichier);
               bibli.ajouterMusique(audio);

            }
         }
         updateTableView();

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   CATEGORIE categorieActuelle;
   String ouChercher;

   public void searchButtonClicked(ActionEvent actionEvent) {
      affichagesResultats.setText("");
      rechercheDemandee = entreeUtilisateur.getText();

      try {

         //new AdvancedMedia().start(lecteurMedia);
         //lecteurMedia = FXMLLoader.setDefaultClassLoader("lecteur.fxml");
         //lecteurMedia = new Pane();
         /*Stage stage = new Stage();
            media.start(stage);
            stage.show();*/
      } catch (Exception e) {

      }

   }

   public void createTreeViews() {

      Vbox = new VBox();

      //TreeItem<String> root = new TreeItem<String> ("ROOT");
      //Playlist
      TreeItem<String> rootItemP = new TreeItem<String>("Playlists");
      rootItemP.setExpanded(true);

      PlaylistTree = new TreeView<String>(rootItemP);
      PlaylistTree.setEditable(true);
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

   public void playlistsButtonClicked(ActionEvent actionEvent) {
      //categorieActuelle=CATEGORIE.PLAYLISTS;
      //resetOtherButtonClicked(isInPlaylists,isInAlbums,isInPiste,isInArtistes);

      //expandTreeView(PlaylistTree);
      //PlaylistTree.get (1);
   }

   /* private void expandTreeView(TreeView view){
        for(TreeItem item : view) {
            if (item != null && !item.isLeaf()) {
                item.setExpanded(true);
                for (TreeItem<?> child : item.getChildren()) {
                    expandTreeView(child);
                }
            }
        }
    }*/
   public void artistesButtonClicked(ActionEvent actionEvent) {
      categorieActuelle = CATEGORIE.ARTISTES;
      resetOtherButtonClicked(isInArtistes, isInAlbums, isInPiste, isInPlaylists);
   }

   public void resetOtherButtonClicked(boolean toRestore, boolean toCancel1, boolean toCancel2, boolean toCancel3) {
      toRestore = true;
      toCancel1 = toCancel2 = toCancel3 = false;

   }

   public void rechercheDemandee(InputMethodEvent inputMethodEvent) {
      rechercheDemandee = entreeUtilisateur.getText();

   }

}
