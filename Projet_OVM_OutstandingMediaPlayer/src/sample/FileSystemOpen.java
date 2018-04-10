package sample;

import javax.swing.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileSystemOpen extends Thread {


    public void openFileSystem()  throws Exception{
        //Desktop d = null;
        //File file = new File("/Users/Downloads"/*System.getenv("USERS"*/ /*+ System.getProperty("user.name") + "/Downloads"*/ );

        /*if( Desktop.isDesktopSupported()) {
           d = Desktop.getDesktop();
        }
        try {
            d.open(file);

        } catch (IOException e){
            System.out.println(e.getMessage());
        }*/

        //la fenêtre de dialogue présentera au départ le contenu du répertoire dont le chemin est indiqué en argument.
        // On peut aussi utiliser le constructeur sans paramètre qui conduirait à avoir une fenêtre de dialogue
        // présentant à l'ouverture le contenu du répertoire de connexion.

            }


    @Override
    public void run(){
        try{
            JFileChooser fenetreDialogue = new JFileChooser(new File("/Users/Downloads"));
            PrintWriter sortie;
            File fichier;
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

            }
}

