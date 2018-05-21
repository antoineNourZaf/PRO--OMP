package model;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileSystemOpen{

    private File fichier;
    public File getFileSelected(){
        return fichier;
    }

    /*public void openFileSystem()  throws Exception{
        //Desktop d = null;
        //File file = new File("/Users/Downloads"/*System.getenv("USERS"*/ /*+ System.getProperty("user.name") + "/Downloads"*/ ;

        /*if( Desktop.isDesktopSupported()) {
           d = Desktop.getDesktop();
        }
        try {
            d.open(file);

        } catch (IOException e){
            System.out.println(e.getMessage());
        }*/




    public String run() throws Exception{


        //la fenêtre de dialogue présentera au départ le contenu du répertoire dont le chemin est indiqué en argument.
        // On peut aussi utiliser le constructeur sans paramètre qui conduirait à avoir une fenêtre de dialogue
        // présentant à l'ouverture le contenu du répertoire de connexion.
        try{
            FileChooser fenetreDialogue = new FileChooser();
            fenetreDialogue.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mp3", "*.mp3"));
            fenetreDialogue.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mp4", "*.mp4"));
            fenetreDialogue.setInitialDirectory( new File("/Users/" + System.getProperty("user.name") + "/Downloads"));
            fichier = fenetreDialogue.showOpenDialog(new Stage());
            System.out.println(fichier.getPath());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // recupere le chemin du fichier
        return fichier.getPath();
        //if(fenetreDialogue.getSelectedExtensionFilter() == isMp3) ;
        //FXMLLoader
            /*File fichier;
            if (fenetreDialogue.showOpenDialog(null)==
                    JFileChooser.APPROVE_OPTION) {
                fichier = fenetreDialogue.getSelectedFile();
                System.out.println(fichier.getPath());
                sortie = new PrintWriter
                        (new FileWriter(fichier.getPath(), true));
                sortie.println(fichier.getPath());
                sortie.close();
            }
        }catch (IOException e){}

            }*/
    }
}

