package view;

import controller.MediaViewController;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.media.*;
import java.lang.*;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.layout.*;

public class MediaViewBase extends GridPane implements Observer {

   protected final ColumnConstraints columnConstraints;
   protected final RowConstraints rowConstraints;
   protected final RowConstraints rowConstraints0;
   protected final AnchorPane anchorPane;
   protected final MediaView mediaView;
   protected final AnchorPane anchorPane0;
   protected final Slider slider;
   protected final Slider slider0;
   protected final ImageView imageView;
   protected final ImageView imageView0;
   protected final ImageView imageView1;
   protected final ImageView imageView2;
   protected final MediaViewController controller;
   
   protected final Button playButton;
   protected final Button stopButton;
   protected final Button backButton;
   protected final Button nextButton;
   
   public MediaViewBase() {

      controller = new MediaViewController();
      columnConstraints = new ColumnConstraints();
      rowConstraints = new RowConstraints();
      rowConstraints0 = new RowConstraints();
      anchorPane = new AnchorPane();
      mediaView = new MediaView();
      anchorPane0 = new AnchorPane();
      slider = new Slider();
      slider0 = new Slider();
      imageView = new ImageView();
      imageView0 = new ImageView();
      imageView1 = new ImageView();
      imageView2 = new ImageView();
      backButton = new Button();
      playButton = new Button();
      stopButton = new Button();
      nextButton = new Button();
      
      setMaxHeight(USE_PREF_SIZE);
      setMaxWidth(USE_PREF_SIZE);
      setMinHeight(USE_PREF_SIZE);
      setMinWidth(USE_PREF_SIZE);
      setPrefHeight(400.0);
      setPrefWidth(600.0);

      columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
      columnConstraints.setMinWidth(10.0);
      columnConstraints.setPrefWidth(100.0);

      rowConstraints.setMaxHeight(298.0);
      rowConstraints.setMinHeight(10.0);
      rowConstraints.setPrefHeight(298.0);
      rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

      rowConstraints0.setMaxHeight(193.0);
      rowConstraints0.setMinHeight(10.0);
      rowConstraints0.setPrefHeight(102.0);
      rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

      anchorPane.setPrefHeight(297.0);
      anchorPane.setPrefWidth(600.0);

      mediaView.setFitHeight(200.0);
      mediaView.setFitWidth(200.0);
      mediaView.setLayoutX(200.0);
      mediaView.setLayoutY(44.0);

      GridPane.setRowIndex(anchorPane0, 1);
      anchorPane0.setPrefHeight(139.0);
      anchorPane0.setPrefWidth(600.0);

      slider.setLayoutX(14.0);
      slider.setLayoutY(42.0);
      slider.setPrefHeight(14.0);
      slider.setPrefWidth(388.0);

      slider0.setLayoutX(446.0);
      slider0.setLayoutY(42.0);

      imageView.setFitHeight(26.0);
      imageView.setFitWidth(26.0);
      imageView.setLayoutX(44.0);
      imageView.setLayoutY(57.0);
      imageView.setPickOnBounds(true);
      imageView.setPreserveRatio(true);
      imageView.setImage(new Image(getClass().getResource("../model/data/back.png").toExternalForm()));
      backButton.setGraphic(imageView);
      
              
      imageView0.setFitHeight(26.0);
      imageView0.setFitWidth(26.0);
      imageView0.setLayoutX(75.0);
      imageView0.setLayoutY(58.0);
      imageView0.setPickOnBounds(true);
      imageView0.setPreserveRatio(true);
      imageView0.setImage(new Image(getClass().getResource("../model/data/play.png").toExternalForm()));

      imageView1.setFitHeight(26.0);
      imageView1.setFitWidth(26.0);
      imageView1.setLayoutX(107.0);
      imageView1.setLayoutY(58.0);
      imageView1.setPickOnBounds(true);
      imageView1.setPreserveRatio(true);
      imageView1.setImage(new Image(getClass().getResource("../model/data/stop.png").toExternalForm()));

      imageView2.setFitHeight(26.0);
      imageView2.setFitWidth(26.0);
      imageView2.setLayoutX(138.0);
      imageView2.setLayoutY(58.0);
      imageView2.setPickOnBounds(true);
      imageView2.setPreserveRatio(true);
      imageView2.setImage(new Image(getClass().getResource("../model/data/next.png").toExternalForm()));

      getColumnConstraints().add(columnConstraints);
      getRowConstraints().add(rowConstraints);
      getRowConstraints().add(rowConstraints0);
      anchorPane.getChildren().add(mediaView);
      getChildren().add(anchorPane);
      anchorPane0.getChildren().add(slider);
      anchorPane0.getChildren().add(slider0);
      anchorPane0.getChildren().add(imageView);
      anchorPane0.getChildren().add(imageView0);
      anchorPane0.getChildren().add(imageView1);
      anchorPane0.getChildren().add(imageView2);
      getChildren().add(anchorPane0);
      
      controller.addObserver(this);
      
   }

   @Override
   public void update(Observable o, Object arg) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }
   
   
   
}
