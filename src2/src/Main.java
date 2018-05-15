
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.MainView;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
/*
        Parent root = FXMLLoader.load(getClass().getResource("samplecheck.fxml"));;
        primaryStage.setTitle(" Connexion");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
*/
        //if (Controller.getLoggedIn){
        Parent root = FXMLLoader.load(getClass().getResource("view/sample.fxml"));
        //Parent root = new MainView();
        primaryStage.setTitle("OMP - Outstanding Media Player");
        primaryStage.setScene(new Scene(root,1024,768));
        primaryStage.show();
        //}


        //FileSystemOpen.lauchSystemOpen();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
