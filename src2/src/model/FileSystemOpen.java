package model;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileSystemOpen {

   private File fichier;

   public File getFileSelected() {
      return fichier;
   }

   public String run() throws Exception {

      //la fenêtre de dialogue présentera au départ le contenu du répertoire dont le chemin est indiqué en argument.
      // On peut aussi utiliser le constructeur sans paramètre qui conduirait à avoir une fenêtre de dialogue
      // présentant à l'ouverture le contenu du répertoire de connexion.
      try {

         FileChooser fenetreDialogue = new FileChooser();

         // Choix des extensions des fichiers
         fenetreDialogue.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mp3", "*.mp3"));
         fenetreDialogue.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("aac", "*.aac"));
         fenetreDialogue.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("aiff", "*.aiff"));
         fenetreDialogue.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("wav", "*.wav"));

         fenetreDialogue.setInitialDirectory(new File("/Users/" + System.getProperty("user.name") + "/Downloads"));

         fichier = fenetreDialogue.showOpenDialog(new Stage());
         System.out.println(fichier.getPath());
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }

      return fichier.getPath();
   }
}
