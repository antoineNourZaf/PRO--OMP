package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.TextFlow;

import java.sql.Date;
import java.sql.ResultSet;

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
    @FXML
    private TextField rechercheLabel;
    public static boolean getLoggedIn = false;

    private Button Playlists;
    private Recherche recherches;

    String rechercheDemandee;

    public int nbFichiersAudios;
    private boolean isInPlaylists = false,isInArtistes=false,isInPiste=false,isInAlbums=false;

    public void amisAppuyes(ActionEvent actionEvent) {

        try {
            affichageAmis.setText("\n\nPas d'amis ! \n\n");
            affichageAmis.appendText("Prends un Curly !");
        } catch (Exception e ){
            System.out.println("erreur amis appuyes" );
        }

    }


    public void loginClicked(ActionEvent actionEvent) {
        getLoggedIn = true;
    }

    public void ajouterTitreAplaylist(ActionEvent actionEvent) {
        affichagesResultats.setText("Titre ajouté a votre collection !  Souhaitez vous viualiser  ce média ?");
    }

    public void afficherInfos(ActionEvent actionEvent) {

        try {
            for(int i=0; i < nbFichiersAudios;i++){
                affichagesResultats.appendText("Fichier" + i);
            }
            affichageInfos.appendText("");

        } catch (Exception e ){
            System.out.println("erreur afficher infos" );
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

            new FileSystemOpen().start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    enum  CATEGORIE {PLAYLISTS,PISTE,ARTISTES,ALBUMS}

    CATEGORIE categorieActuelle;
    String ouChercher;

    public void searchButtonClicked(ActionEvent actionEvent) {
        affichagesResultats.setText("");
        rechercheDemandee = entreeUtilisateur.getText();
        try {

            ResultSet requestValues;
            switch (categorieActuelle){
                case PLAYLISTS:
                {
                    isInPlaylists = true;
                    requestValues = ProjetOMP.searchInPlaylist(rechercheDemandee, "playlist");
                    break;
                }


                case PISTE:
                {
                    isInPiste = true;
                    requestValues= ProjetOMP.search(rechercheDemandee, "PISTE_VIEW");
                    break;

                }



                case ARTISTES:
                {
                    isInArtistes = true;
                    requestValues= ProjetOMP.searchInArtiste(rechercheDemandee, "artiste");
                    break;
                }



                case ALBUMS: {
                    isInAlbums = true;
                    requestValues= ProjetOMP.searchInAlbums(rechercheDemandee, "ALBUM_VIEW");
                    break;

                }

                default: {
                    isInPiste = true;
                    requestValues= ProjetOMP.search(rechercheDemandee, "PISTE_VIEW");
                    break;
                }
            }

            if (isInPlaylists){
                affichagesResultats.setText("PLAYLISTS\n");

                while (requestValues.next()) {

                    int num = requestValues.getInt("numPlaylist");

                    String nom = requestValues.getString("nomPlaylist");
                    String type = requestValues.getString("typePlaylist");

                    affichagesResultats.appendText(num + " / " + nom + " / " + type +"\n");
                }
            }
            else if (isInPiste){
                affichagesResultats.setText("PISTES\n");

                while (requestValues.next()) {

                    String nom = requestValues.getString("titre");
                    String artiste = requestValues.getString("artiste");

                    String genre = requestValues.getString("genre");
                    Double duree = requestValues.getDouble("duree");

                    affichagesResultats.appendText("titre : " + nom +"  / " + "artiste : " + artiste +  " / "+ "genre : " +
                            genre +" / duree : " +duree  +"\n");
                }
            }
            else if (isInArtistes){
                affichagesResultats.setText("ARTISTES\n");

                while (requestValues.next()) {
                    String nom = requestValues.getString("nom");
                    String genre = requestValues.getString("genre");
                    Date date = requestValues.getDate("dateDebut");
                    String pays = requestValues.getString("pays");
                    affichagesResultats.appendText("nom : " + nom + " / genre : " + genre + " / dateDebut : " + date + " / pays : " + pays + "/ " + "\n");
                }
            }
            else if (isInAlbums){
                affichagesResultats.setText("ALBUMS\n");

                while (requestValues.next()) {
                    String titre = requestValues.getString("titre");
                    String genre = requestValues.getString("artiste");
                    Double duree = requestValues.getDouble("duree");
                    Integer nbPiste = requestValues.getInt("nbPiste");

                    affichagesResultats.appendText("titre : " + titre + "nb Pistes : "+ nbPiste +" / genre " + genre + "dureeTotale :" +duree +"\n");
                }
            }
            requestValues.close();

        } catch (Exception e ){
            System.out.println("erreur recherche" );
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

                // ici on effectue la recherche sql et on affiche les resultat
                affichagesResultats.setText("Nouvlle requete SQL demandé par radamus ... Resultats..." );
                rechercheDemandee = entreeUtilisateur.getText();

            }
        });
        //System.out.println("Controlleur de l'interface : Demande de mise à jour");
    }

    public void rechercheDemandee(InputMethodEvent inputMethodEvent) {
        rechercheDemandee = entreeUtilisateur.getText();

    }
}
