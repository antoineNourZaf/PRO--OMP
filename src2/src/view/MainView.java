package view;

import controller.BibliothequeManager;
import javafx.scene.media.*;
import javafx.scene.effect.*;
import javafx.scene.shape.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import java.lang.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainView extends BorderPane {

   protected final TextFlow affichage;
   protected final TextField entreeUtilisateur;
   protected final Button searchButton;
   protected final ScrollPane scrollPane;
   protected final AnchorPane anchorPane;
   protected final Button playlist;
   protected final Button artiste;
   protected final Button piste;
   protected final Button albumsButton;
   protected final SplitPane splitPane;
   protected final AnchorPane anchorPane0;
   protected final ScrollPane scrollPane0;
   protected final AnchorPane anchorPane1;
   protected final TextArea affichageAmis;
   protected final Button button;
   protected final Button afficheInfos;
   protected final TextArea affichageInfos;
   protected final GridPane gridPane;
   protected final ColumnConstraints columnConstraints;
   protected final ColumnConstraints columnConstraints0;
   protected final RowConstraints rowConstraints;
   protected final RowConstraints rowConstraints0;
   protected final SplitPane splitPane0;
   protected final AnchorPane anchorPane2;
   protected final Button ouvertureFichier;
   protected final AnchorPane anchorPane3;
   protected final TextField playlistOuInsere;
   protected final Button ajouterTitreBouton;
   protected final MenuBar menuBar;
   protected final Menu menu;
   protected final MenuItem menuItem;
   protected final MenuItem menuItem0;
   protected final Menu menu0;
   protected final MenuItem menuItem1;
   protected final Menu menu1;
   protected final MenuItem menuItem2;
   protected final ColorAdjust colorAdjust;
   protected final Label label;
   protected final Pane pane;
   protected final Slider slider;
   protected final Button button0;
   protected final SplitPane splitPane1;
   protected final AnchorPane anchorPane4;
   protected final MediaView mediaView;
   protected final AnchorPane anchorPane5;
   protected final ListView listView;
   protected final BibliothequeManager manager;
   
   public MainView() {

      affichage = new TextFlow();
      entreeUtilisateur = new TextField();
      searchButton = new Button();
      scrollPane = new ScrollPane();
      anchorPane = new AnchorPane();
      playlist = new Button();
      artiste = new Button();
      piste = new Button();
      albumsButton = new Button();
      splitPane = new SplitPane();
      anchorPane0 = new AnchorPane();
      scrollPane0 = new ScrollPane();
      anchorPane1 = new AnchorPane();
      affichageAmis = new TextArea();
      button = new Button();
      afficheInfos = new Button();
      affichageInfos = new TextArea();
      gridPane = new GridPane();
      columnConstraints = new ColumnConstraints();
      columnConstraints0 = new ColumnConstraints();
      rowConstraints = new RowConstraints();
      rowConstraints0 = new RowConstraints();
      splitPane0 = new SplitPane();
      anchorPane2 = new AnchorPane();
      ouvertureFichier = new Button();
      anchorPane3 = new AnchorPane();
      playlistOuInsere = new TextField();
      ajouterTitreBouton = new Button();
      menuBar = new MenuBar();
      menu = new Menu();
      menuItem = new MenuItem();
      menuItem0 = new MenuItem();
      menu0 = new Menu();
      menuItem1 = new MenuItem();
      menu1 = new Menu();
      menuItem2 = new MenuItem();
      colorAdjust = new ColorAdjust();
      label = new Label();
      pane = new Pane();
      slider = new Slider();
      button0 = new Button();
      splitPane1 = new SplitPane();
      anchorPane4 = new AnchorPane();
      mediaView = new MediaView();
      anchorPane5 = new AnchorPane();
      listView = new ListView();
      manager = new BibliothequeManager();
      
      setMaxHeight(USE_PREF_SIZE);
      setMaxWidth(USE_PREF_SIZE);
      setMinHeight(USE_PREF_SIZE);
      setMinWidth(USE_PREF_SIZE);
      setPrefHeight(800.0);
      setPrefWidth(1200.0);

      BorderPane.setAlignment(affichage, javafx.geometry.Pos.CENTER);
      affichage.setPrefHeight(29.0);
      affichage.setPrefWidth(1200.0);

      //entreeUtilisateur.setOnInputMethodTextChanged(this::rechercheDemandee);
      entreeUtilisateur.setPrefHeight(27.0);
      entreeUtilisateur.setPrefWidth(200.0);

      searchButton.setId("searchButton");
      searchButton.setMnemonicParsing(false);
      //searchButton.setOnAction(this::searchButtonClicked);
      searchButton.setPrefHeight(27.0);
      searchButton.setPrefWidth(121.0);
      searchButton.setText("Rechercher");
      setBottom(affichage);

      BorderPane.setAlignment(scrollPane, javafx.geometry.Pos.CENTER);
      scrollPane.setPrefHeight(460.0);
      scrollPane.setPrefWidth(200.0);

      anchorPane.setMinHeight(0.0);
      anchorPane.setMinWidth(0.0);
      anchorPane.setPrefHeight(200.0);
      anchorPane.setPrefWidth(200.0);

      playlist.setId("playlist");
      playlist.setLayoutY(10.0);
      playlist.setMnemonicParsing(false);
      //playlist.setOnAction(this::playlistsButtonClicked);
      playlist.setPrefHeight(50.0);
      playlist.setPrefWidth(199.0);
      playlist.setText("Playlists");

      artiste.setId("artiste");
      artiste.setLayoutY(151.0);
      artiste.setMnemonicParsing(false);
      //artiste.setOnAction(this::artistesButtonClicked);
      artiste.setPrefHeight(49.0);
      artiste.setPrefWidth(199.0);
      artiste.setText("Artistes");

      piste.setId("piste");
      piste.setLayoutX(-1.0);
      piste.setLayoutY(60.0);
      piste.setMnemonicParsing(false);
      //piste.setOnAction(this::pisteButtonClicked);
      piste.setPrefHeight(45.0);
      piste.setPrefWidth(200.0);
      piste.setText("Pistes");

      albumsButton.setLayoutY(105.0);
      albumsButton.setMnemonicParsing(false);
      //albumsButton.setOnAction(this::albumsButtonClicked);
      albumsButton.setPrefHeight(46.0);
      albumsButton.setPrefWidth(199.0);
      albumsButton.setText("Albums");
      scrollPane.setContent(anchorPane);
      setLeft(scrollPane);

      BorderPane.setAlignment(splitPane, javafx.geometry.Pos.CENTER);
      splitPane.setDividerPositions(0.5, 0.5);
      splitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
      splitPane.setPrefHeight(460.0);
      splitPane.setPrefWidth(200.0);

      anchorPane0.setMinHeight(0.0);
      anchorPane0.setMinWidth(0.0);
      anchorPane0.setPrefHeight(217.0);
      anchorPane0.setPrefWidth(150.0);

      scrollPane0.setPrefHeight(200.0);
      scrollPane0.setPrefWidth(200.0);

      anchorPane1.setMinHeight(0.0);
      anchorPane1.setMinWidth(0.0);
      anchorPane1.setPrefHeight(200.0);
      anchorPane1.setPrefWidth(200.0);

      affichageAmis.setLayoutX(-4.0);
      affichageAmis.setPrefHeight(308.0);
      affichageAmis.setPrefWidth(204.0);

      button.setId("contenusAjoutes");
      button.setLayoutX(1.0);
      button.setMnemonicParsing(false);
      //button.setOnAction(this::derniersContenusAjoutes);
      button.setPrefHeight(40.0);
      button.setPrefWidth(189.0);
      button.setText("Derniers medias ajoutes");
      scrollPane0.setContent(anchorPane1);

      afficheInfos.setMnemonicParsing(false);
      //afficheInfos.setOnAction(this::afficherInfos);
      afficheInfos.setText("Informations media");

      affichageInfos.setPrefHeight(209.0);
      affichageInfos.setPrefWidth(184.0);
      setRight(splitPane);

      BorderPane.setAlignment(gridPane, javafx.geometry.Pos.CENTER);
      gridPane.setPrefHeight(124.0);
      gridPane.setPrefWidth(1200.0);

      columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
      columnConstraints.setMinWidth(10.0);
      columnConstraints.setPrefWidth(100.0);

      columnConstraints0.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
      columnConstraints0.setMinWidth(10.0);
      columnConstraints0.setPrefWidth(100.0);

      rowConstraints.setMinHeight(10.0);
      rowConstraints.setPrefHeight(30.0);
      rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

      rowConstraints0.setMinHeight(10.0);
      rowConstraints0.setPrefHeight(30.0);
      rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

      GridPane.setRowIndex(splitPane0, 1);
      splitPane0.setDividerPositions(0.5);
      splitPane0.setOrientation(javafx.geometry.Orientation.VERTICAL);
      splitPane0.setPrefHeight(71.0);
      splitPane0.setPrefWidth(600.0);

      anchorPane2.setMinHeight(0.0);
      anchorPane2.setMinWidth(0.0);
      anchorPane2.setPrefHeight(57.0);
      anchorPane2.setPrefWidth(598.0);

      ouvertureFichier.setMnemonicParsing(false);
      //ouvertureFichier.setOnAction(this::openFileSystem);
      ouvertureFichier.setPrefHeight(27.0);
      ouvertureFichier.setPrefWidth(116.0);
      ouvertureFichier.setText("Ouvrir Fichier");

      anchorPane3.setMinHeight(0.0);
      anchorPane3.setMinWidth(0.0);
      anchorPane3.setPrefHeight(84.0);
      anchorPane3.setPrefWidth(548.0);

      playlistOuInsere.setLayoutX(328.0);
      playlistOuInsere.setLayoutY(-1.0);

      ajouterTitreBouton.setLayoutX(495.0);
      ajouterTitreBouton.setLayoutY(-2.0);
      ajouterTitreBouton.setMnemonicParsing(false);
      //ajouterTitreBouton.setOnAction(this::ajouterTitreAplaylist);
      ajouterTitreBouton.setPrefHeight(33.0);
      ajouterTitreBouton.setPrefWidth(105.0);
      ajouterTitreBouton.setText("Ajouter");

      menuBar.setMinHeight(USE_PREF_SIZE);
      menuBar.setMinWidth(USE_PREF_SIZE);
      menuBar.setPrefHeight(37.0);
      menuBar.setPrefWidth(550.0);

      menu.setMnemonicParsing(false);
      menu.setText("File");

      menuItem.setMnemonicParsing(false);
      menuItem.setText("Close");

      menuItem0.setMnemonicParsing(false);
      menuItem0.setText("Unspecified Action");

      menu0.setMnemonicParsing(false);
      menu0.setText("Edit");

      menuItem1.setMnemonicParsing(false);
      menuItem1.setText("Delete");

      menu1.setMnemonicParsing(false);
      menu1.setText("Help");

      menuItem2.setMnemonicParsing(false);
      menuItem2.setText("About");

      menuBar.setEffect(colorAdjust);

      GridPane.setColumnIndex(label, 1);
      label.setAlignment(javafx.geometry.Pos.CENTER);
      label.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
      label.setPrefHeight(0.0);
      label.setPrefWidth(556.0);
      label.setText("OMP, always the best and play the rest ");
      label.setFont(new Font(24.0));

      GridPane.setColumnIndex(pane, 1);
      GridPane.setRowIndex(pane, 1);
      pane.setPrefHeight(200.0);
      pane.setPrefWidth(200.0);

      slider.setLayoutX(397.0);
      slider.setLayoutY(24.0);

      button0.setLayoutY(19.0);
      button0.setMnemonicParsing(false);
      button0.setText("Button");
      setTop(gridPane);

      BorderPane.setAlignment(splitPane1, javafx.geometry.Pos.CENTER);
      splitPane1.setDividerPositions(0.5);
      splitPane1.setOrientation(javafx.geometry.Orientation.VERTICAL);
      splitPane1.setPrefHeight(200.0);
      splitPane1.setPrefWidth(160.0);

      anchorPane4.setMinHeight(0.0);
      anchorPane4.setMinWidth(0.0);
      anchorPane4.setPrefHeight(100.0);
      anchorPane4.setPrefWidth(160.0);

      mediaView.setFitHeight(200.0);
      mediaView.setFitWidth(200.0);
      mediaView.setLayoutX(237.0);
      mediaView.setLayoutY(60.0);

      anchorPane5.setMinHeight(0.0);
      anchorPane5.setMinWidth(0.0);
      anchorPane5.setPrefHeight(100.0);
      anchorPane5.setPrefWidth(160.0);

      listView.setLayoutX(-1.0);
      listView.setLayoutY(-1.0);
      listView.setPrefHeight(320.0);
      listView.setPrefWidth(801.0);
      setCenter(splitPane1);
      //listView.setItems(value);

      affichage.getChildren().add(entreeUtilisateur);
      affichage.getChildren().add(searchButton);
      anchorPane.getChildren().add(playlist);
      anchorPane.getChildren().add(artiste);
      anchorPane.getChildren().add(piste);
      anchorPane.getChildren().add(albumsButton);
      anchorPane1.getChildren().add(affichageAmis);
      anchorPane1.getChildren().add(button);
      anchorPane0.getChildren().add(scrollPane0);
      splitPane.getItems().add(anchorPane0);
      splitPane.getItems().add(afficheInfos);
      splitPane.getItems().add(affichageInfos);
      gridPane.getColumnConstraints().add(columnConstraints);
      gridPane.getColumnConstraints().add(columnConstraints0);
      gridPane.getRowConstraints().add(rowConstraints);
      gridPane.getRowConstraints().add(rowConstraints0);
      anchorPane2.getChildren().add(ouvertureFichier);
      splitPane0.getItems().add(anchorPane2);
      anchorPane3.getChildren().add(playlistOuInsere);
      anchorPane3.getChildren().add(ajouterTitreBouton);
      splitPane0.getItems().add(anchorPane3);
      gridPane.getChildren().add(splitPane0);
      menu.getItems().add(menuItem);
      menu.getItems().add(menuItem0);
      menuBar.getMenus().add(menu);
      menu0.getItems().add(menuItem1);
      menuBar.getMenus().add(menu0);
      menu1.getItems().add(menuItem2);
      menuBar.getMenus().add(menu1);
      gridPane.getChildren().add(menuBar);
      gridPane.getChildren().add(label);
      pane.getChildren().add(slider);
      pane.getChildren().add(button0);
      gridPane.getChildren().add(pane);
      anchorPane4.getChildren().add(mediaView);
      splitPane1.getItems().add(anchorPane4);
      anchorPane5.getChildren().add(listView);
      splitPane1.getItems().add(anchorPane5);

   }
}
