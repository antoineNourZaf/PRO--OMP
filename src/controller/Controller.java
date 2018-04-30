package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.FileSystemOpen;
import model.MediaExtracted;
import model.ShowMediaImage;
import model.Recherche;

import java.io.File;
import java.util.ArrayList;

public class Controller{

    public TextFlow affichage;
    public Button searchButton;
    public TextField entreeUtilisateur;
    public TextArea affichagesResultats;
    public Button playlist;
    public Button piste;
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
    public Button albumsButton;
    public Pane lecteurMedia;
    //public ListView listViewContenu;
    public TableView tableView;
    public TableColumn Artiste;
    public TableColumn Genre;
    public TableColumn Format;
    public TableColumn TypeMedia;
    public TableColumn CheminAcces;
    public TableColumn Nom;

    @FXML
    private TextField rechercheLabel;
    public static boolean getLoggedIn = false;

    private Button Playlists;
    private Recherche recherches;

    String rechercheDemandee;


    enum  CATEGORIE {PLAYLISTS,PISTE,ARTISTES,ALBUMS}

    public int nbFichiersAudios;
    private boolean isInPlaylists = false,isInArtistes=false,isInPiste=false,isInAlbums=false;
    //AdvancedMedia media = new AdvancedMedia();

    ArrayList<File> dernierContenuAjoutes= new ArrayList<>();

    public void setColumsOfTableView(){
        Artiste = new TableColumn<MediaExtracted,String>("Artiste");
        Artiste.setPrefWidth(100);
        Artiste.setMinWidth(20);
        Artiste.setCellValueFactory(new PropertyValueFactory<>("artiste")); // parametre artiste de MediaExtracted

        Genre = new TableColumn<MediaExtracted,String>("Genre");
        Genre.setPrefWidth(100);
        Genre.setMinWidth(20);
        Genre.setCellValueFactory(new PropertyValueFactory<>("genre")); // parametre genre de MediaExtracted

        Format = new TableColumn<MediaExtracted,String>("Format");
        Format.setPrefWidth(100);
        Format.setMinWidth(20);
        Format.setCellValueFactory(new PropertyValueFactory<>("format")); // parametre format de MediaExtracted

        TypeMedia = new TableColumn<MediaExtracted,String>("TypeMedia");
        TypeMedia.setPrefWidth(100);
        TypeMedia.setMinWidth(20);
        TypeMedia.setCellValueFactory(new PropertyValueFactory<>("typeMedia")); // parametre typeMedia de MediaExtracted

        Nom = new TableColumn<MediaExtracted,String>("Nom");
        Nom.setPrefWidth(100);
        Nom.setMinWidth(20);
        Nom.setCellValueFactory(new PropertyValueFactory<>("nom")); // parametre nom de MediaExtracted

        CheminAcces = new TableColumn<MediaExtracted,String>("CheminAcces");
        CheminAcces.setPrefWidth(100);
        CheminAcces.setMinWidth(20);
        CheminAcces.setCellValueFactory(new PropertyValueFactory<>("cheminAcces")); // parametre nom de MediaExtracted

        tableView.setItems(getExtractedMedia());
        tableView.getColumns().addAll(Nom,Artiste,Genre,Format,TypeMedia,CheminAcces);
    }

    public ObservableList<MediaExtracted> getExtractedMedia(){
        ObservableList<MediaExtracted> extractedmedias = FXCollections.observableArrayList();
        extractedmedias.add(new MediaExtracted("DKR","Booba","Rap",".mp3","audio","/chemin"));
        extractedmedias.add(new MediaExtracted("QLF","PNL","Rap",".mp3","audio","/chemin2"));
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
            Media media;
            MediaPlayer mediaPlayer;
            File filestring = new File("/Users/walidkoubaa/Documents/The Weeknd - Starboy ft. Daft Punk.mp3");
            media = new Media(filestring.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            new ExtractData().start(new Stage());
            mediaPlayer.play();

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

            String nomFichier = new FileSystemOpen().run();
            //affichageMedias.appendText(nomFichier);

            //listViewContenu = new ListView();
            //listViewContenu.getItems().setAll( new String(nomFichier), new String("Booba"), new String ("Celine Dion"),new String ("Skrillex"));


            //lecteurMedia. = new Stage(new AdvancedMedia());
            //tableView.getColumns().add(1,nomFichier);

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


    public void playlistsButtonClicked(ActionEvent actionEvent) {
        categorieActuelle=CATEGORIE.PLAYLISTS;
        resetOtherButtonClicked(isInPlaylists,isInAlbums,isInPiste,isInArtistes);
    }

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

