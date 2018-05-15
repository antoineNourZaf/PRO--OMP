package model;

import controller.Controller;

public class Recherche implements Runnable {

    private Controller interfaceController;

    String requeteSQL;

    public Recherche (Controller ic) {
        interfaceController = ic;
    }

    @Override
    public void run() {

    }

    public void searchInDatabase(){

        interfaceController.update();

    }


    public void afficherPlaylists() {

        interfaceController.update();
    }

    public void afficheResultat(){

    }
}
