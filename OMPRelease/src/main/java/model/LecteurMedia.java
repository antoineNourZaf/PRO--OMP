
package model;

import controller.MediaPlayerViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Antoine
 */
public class LecteurMedia extends Application {
   
   @Override
   public void start(Stage secondaryStage) throws Exception {
      
      Parent root = FXMLLoader.load(getClass().getResource("view/mediaPlayerView.fxml"));
      secondaryStage.setTitle("OMP");
      secondaryStage.setScene(new Scene(root, 400, 400));
      secondaryStage.show();
      
      

   }
   
}
