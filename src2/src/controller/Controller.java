package controller;

import view.AudioPlayer;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.FileSystemOpen;
import model.MediaExtracted;
import model.ShowMediaImage;
import model.Recherche;
import controller.ParserXML;
import org.w3c.dom.Element;
import model.Audio;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Controller {

    public TextFlow affichage;
    public Button searchButton;
    public TextField entreeUtilisateur;
    public TextArea affichagesResultats;
    public TextArea affichageAmis;
    public TextArea affichageInfos;
    public TextField utilisateur;
    public TextField mdp;
    public Button Login;
    public Label insertionTitre;
    public Label playlistOuInserer;
    public Button ajouterTitreBouton;
    public TextField insertionTitr;
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

    @FXML
    private TextField rechercheLabel;
    public static boolean getLoggedIn = false;

    private Button Playlists;
    private Recherche recherches;

    String rechercheDemandee;

    ParserXML parser;

    private String cheminFichier;


    /*public void initController(){
        createTreeViews();
        setColumsOfTableView();
    }*/

    enum  CATEGORIE {PLAYLISTS,PISTE,ARTISTES,ALBUMS}

    public int nbFichiersAudios;
    private boolean isInPlaylists = false,isInArtistes=false,isInPiste=false,isInAlbums=false;
    //AdvancedMedia media = new AdvancedMedia();

    ArrayList<File> dernierContenuAjoutes= new ArrayList<>();

    public void setColumsOfTableView(){
        parser = new ParserXML();
        createTreeViews();

        Identifiant = new TableColumn<MediaExtracted,String>("Id");
        Identifiant.setPrefWidth(100);
        Identifiant.setMinWidth(20);
        Identifiant.setCellValueFactory(new PropertyValueFactory<>("id")); // parametre artiste de MediaExtracted


        Titre = new TableColumn<MediaExtracted,String>("Titre");
        Titre.setPrefWidth(100);
        Titre.setMinWidth(20);
        Titre.setCellValueFactory(new PropertyValueFactory<>("Titre")); // parametre nom de MediaExtracted


        Format = new TableColumn<MediaExtracted,String>("Format");
        Format.setPrefWidth(100);
        Format.setMinWidth(20);
        Format.setCellValueFactory(new PropertyValueFactory<>("Format")); // parametre format de MediaExtracted


        Artiste = new TableColumn<MediaExtracted,String>("Artiste");
        Artiste.setPrefWidth(100);
        Artiste.setMinWidth(20);
        Artiste.setCellValueFactory(new PropertyValueFactory<>("Artiste")); // parametre artiste de MediaExtracted


        Duree = new TableColumn<MediaExtracted,String>("Duree");
        Duree.setPrefWidth(100);
        Duree.setMinWidth(20);
        Duree.setCellValueFactory(new PropertyValueFactory<>("Duree")); // parametre nom de MediaExtracted


        Album = new TableColumn<MediaExtracted,String>("Album");
        Album.setPrefWidth(100);
        Album.setMinWidth(20);
        Album.setCellValueFactory(new PropertyValueFactory<>("Album")); // parametre nom de MediaExtracted


        Annee = new TableColumn<MediaExtracted,String>("Annee");
        Annee.setPrefWidth(100);
        Annee.setMinWidth(20);
        Annee.setCellValueFactory(new PropertyValueFactory<>("Annee")); // parametre nom de MediaExtracted



        TypeMedia = new TableColumn<MediaExtracted,String>("TypeMedia");
        TypeMedia.setPrefWidth(100);
        TypeMedia.setMinWidth(20);
        TypeMedia.setCellValueFactory(new PropertyValueFactory<>("TypeMedia")); // parametre typeMedia de MediaExtracted



        CheminAcces = new TableColumn<MediaExtracted,String>("CheminAcces");
        CheminAcces.setPrefWidth(100);
        CheminAcces.setMinWidth(20);
        CheminAcces.setCellValueFactory(new PropertyValueFactory<>("cheminAcces")); // parametre nom de MediaExtracted

        tableView.setItems(getExtractedMedia());
        tableView.getColumns().addAll(Identifiant,Titre,Format,Artiste,Duree,Album,Annee,TypeMedia,CheminAcces);
    }


    public ObservableList<MediaExtracted> getExtractedMedia(){
        ObservableList<MediaExtracted> extractedmedias = FXCollections.observableArrayList();
        ArrayList<Element> audio = parser.getAudiosDonnees();

        for(int i=0; i< audio.size();i++) {
            Element identifiant = (Element) audio.get(i);
            String id = identifiant.getAttribute("id");
            Element titre = (Element) audio.get(i).getElementsByTagName("Titre").item(0);
            Element format = (Element) audio.get(i).getElementsByTagName("Format").item(0);
            Element artiste = (Element) audio.get(i).getElementsByTagName("Artiste").item(0);
            Element duree = (Element) audio.get(i).getElementsByTagName("Duree").item(0);
            Element album = (Element) audio.get(i).getElementsByTagName("Album").item(0);
            Element annee = (Element) audio.get(i).getElementsByTagName("Annee").item(0);
            String typeMedia;
            //Element cheminAcces = (Element) audio.get(1).getElementsByTagName("Chemin").item(0);


            // si c'est un format de type audio affiche le type audio
            if(format.getTextContent().equals("mp3") || format.getTextContent().equals("aac")){
                typeMedia="audio";

            }
            // si c'est un format de type video affiche le type video
            else if (format.getTextContent().equals("mp4") || format.getTextContent().equals("wav")){
                typeMedia= "video";

            }
            //sinon affiche que le type n'est pas reconnu
            else {
                typeMedia ="unknown media";


            }

            System.out.println(typeMedia);
           MediaExtracted media = new MediaExtracted(
                    id, titre.getTextContent(),format.getTextContent(), artiste.getTextContent(),
                    duree.getTextContent(), album.getTextContent(),annee.getTextContent(),"image vide",typeMedia,
                   cheminFichier);
            extractedmedias.add(media);
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

        if (click.getClickCount() == 2) {
            MediaExtracted extracted = (MediaExtracted) tableView.getSelectionModel().getSelectedItem();

            System.out.println("lire media !");
            //Media media;
            //MediaPlayer mediaPlayer;
            //File filestring = new File(extracted.getCheminAcces());
            //media = new Media(filestring.toURI().toString());
            //mediaPlayer = new MediaPlayer(media);

            AudioPlayer audioPlayer = new AudioPlayer();
            audioPlayer.setMediaPath(extracted.getCheminAcces());
            audioPlayer.start(new Stage());

        }

        if(click.getButton()== MouseButton.SECONDARY){


            // cree un menu une fois qu'on clique avec le clique droit
            ContextMenu menu = new ContextMenu();
            MenuItem choix1 = new MenuItem("Lire");
            MenuItem choix2 = new MenuItem("Supprimer");
            MenuItem choix3 = new MenuItem("Supprimer");

            menu.getItems().addAll(choix1,choix2,choix3);

            final Rectangle rectangle = new Rectangle(70, 70, Color.TAN);

            menu.show(rectangle, click.getScreenX(), click.getScreenY());

        }

    }




    public void derniersContenusAjoutes(ActionEvent actionEvent) {

        try {
            if (dernierContenuAjoutes.isEmpty()) {
                affichageAmis.setText("\n\nPas de contenu ! \n\n");
                affichageAmis.appendText("Vous pouvez ajouter un media \n a votre librairie avec \"Ouvrir un fichier\"  !");
            }
            else {
                for (File f:dernierContenuAjoutes) {
                    affichageAmis.appendText(f.getName() + "\n");
                }
            }
        } catch (Exception e ){
            System.out.println("Erreur Affichage contenu" );
        }

    }


    public void ajouterTitreAplaylist(ActionEvent actionEvent) {
        affichagesResultats.setText("Titre ajouté a votre collection !  Souhaitez vous viualiser  ce média ?");
    }

    public void afficherInfos(ActionEvent actionEvent) {

        try {
            // si fichier selectionne affiche son image
            affichageInfos.setText("Illustration du mp3");
            //affiche image du fichier en lecture en dessous d
            ShowMediaImage image = new ShowMediaImage("path image");
            //sinon affiche image de base du programme (png inexistant)
        } catch (Exception e ){
            System.out.println("erreur affichage infos" );
        }

    }

    public void pisteButtonClicked(ActionEvent actionEvent) {
        categorieActuelle=CATEGORIE.PISTE;
        resetOtherButtonClicked(isInPiste,isInPlaylists,isInAlbums,isInArtistes);
    }

    public void albumsButtonClicked(ActionEvent actionEvent) {

        categorieActuelle=CATEGORIE.ALBUMS;
        resetOtherButtonClicked(isInAlbums,isInPiste,isInPlaylists,isInArtistes);
    }

    public void openFileSystem(ActionEvent actionEvent) {
        try{

            BibliothequeManager bManager = new BibliothequeManager();
         bManager.createBibliotheque();
         
         
         
         String nomFichier = new FileSystemOpen().run();
         
         DataExtracteur md = new DataExtracteur(nomFichier);
         Audio audio = new Audio(nomFichier);
         audio.setFormat();
         
         // On peut seulement obtenir les metadatas des fichiers mp3
         if (nomFichier.contains("mp3")) {
            md.ExtractionMetadata(nomFichier,audio, bManager);
         }
         else {
            audio.setTitre(nomFichier);
            bManager.ajouterMusique(audio);
         }
         
            setColumsOfTableView();


        }catch (Exception e) {
            e.printStackTrace();
        }
    }




    CATEGORIE categorieActuelle;
    String ouChercher;

    public void searchButtonClicked(ActionEvent actionEvent) {
        affichagesResultats.setText("");
        rechercheDemandee = entreeUtilisateur.getText();

        try{

            //new AdvancedMedia().start(lecteurMedia);
            //lecteurMedia = FXMLLoader.setDefaultClassLoader("lecteur.fxml");
            //lecteurMedia = new Pane();
            /*Stage stage = new Stage();
            media.start(stage);
            stage.show();*/
        }catch (Exception e){

        }

    }

    public void createTreeViews(){

        Vbox = new VBox();

        //TreeItem<String> root = new TreeItem<String> ("ROOT");


        //Playlist
        TreeItem<String> rootItemP = new TreeItem<String> ("Playlists");
        rootItemP.setExpanded(true);
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<String> ("playlist n°" + i);
            rootItemP.getChildren().add(item);
        }

        PlaylistTree = new TreeView<String> (rootItemP);

        //Artistes
        TreeItem<String> rootItemArt = new TreeItem<String> ("Artistes");
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


        Vbox.getChildren().addAll(PlaylistTree,ArtisteTree,AlbumTree);

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
        categorieActuelle=CATEGORIE.ARTISTES;
        resetOtherButtonClicked(isInArtistes,isInAlbums,isInPiste,isInPlaylists);
    }

    public void resetOtherButtonClicked(boolean toRestore,boolean toCancel1,boolean toCancel2,boolean toCancel3){
        toRestore=true;
        toCancel1=toCancel2=toCancel3=false;

    }

    public void update() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public void rechercheDemandee(InputMethodEvent inputMethodEvent) {
        rechercheDemandee = entreeUtilisateur.getText();

    }



}

