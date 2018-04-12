/*
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package advancedmedia;

import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.SliderBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * An advanced media player with controls for play/pause, seek, and volume. 
 *
 * @see javafx.scene.media.MediaPlayer
 * @see javafx.scene.media.Media
 *
 *@autheur: Doriane Kaffo
 */
public class AdvancedMedia extends Application {

    private MediaPlayer mediaPlayer;
    private Media pick;
    //largeur et hauteur minimale de notre lecteur
    private static final int LARGEUR= 480;    
    private static final int HAUTEUR= 280;
    
    //Elements des menus
    private MenuBar menuBar = new MenuBar();
    private MenuItem exitItem = new MenuItem("Quitter");
    private MenuItem openItem = new MenuItem("Ouvrir"); 
    private Menu fileMenu = new Menu("Fichier"); 
    private MenuItem aboutItem = new MenuItem("À propos..."); 
    private Menu helpMenu = new Menu("Aide"); 

    private void init(final Stage primaryStage) {
        //Group root = new Group();
        //primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Mon Lecteur Multimedia");
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(LARGEUR);
        primaryStage.setMinHeight(HAUTEUR);
        
        //initialisons les menus
        fileMenu.getItems().setAll(openItem, new SeparatorMenuItem(), exitItem);
        helpMenu.getItems().setAll(aboutItem);
        menuBar.getMenus().setAll(fileMenu, helpMenu);
        
        //on definit les images des sous menus
        Image OpenImage = new Image(AdvancedMedia.class.getResourceAsStream("open.png"));
        ImageView imageViewOpen = new ImageView(OpenImage);
        openItem.setGraphic(imageViewOpen);
         Image QuitImage = new Image(AdvancedMedia.class.getResourceAsStream("quit.png"));
        ImageView imageViewQuit = new ImageView(QuitImage);
        exitItem.setGraphic(imageViewQuit);
        Image InfoImage = new Image(AdvancedMedia.class.getResourceAsStream("info.png"));
        ImageView imageViewInfo = new ImageView(InfoImage);
        aboutItem.setGraphic(imageViewInfo);
            
        //on definit l'action a effectuer quand on clique sur le sous menu Ouvrir
            openItem.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
        // On définit les extensions que l'on accepte
        FileFilter multimediaFilter = new FileNameExtensionFilter("Audio & Videos Files", "3gp", "flv", "mp3", "wav", "mp4");
        //on cree l'objet qui va parcourir les fichiers de la machine     
        JFileChooser j= new JFileChooser();
        //on definit le filtre
        j.setFileFilter(multimediaFilter);
        j.showOpenDialog(null);
        //on recupere l'objet java File asscocie au fichier selectionne
        File file = j.getSelectedFile();
        pick = new Media(file.toURI().toString());

        mediaPlayer = new MediaPlayer(pick);

        mediaPlayer.setAutoPlay(true);
        //mediaControl contient la zone de la video et des boutons et infos sur la lecture de la video ou audio
         MediaControl mediaControl = new MediaControl(mediaPlayer);
        //mediaControl.setMinSize(480,280);
        mediaControl.setPrefSize(LARGEUR,HAUTEUR);
        //mediaControl.setMaxSize(480,280);
        //on met la barre de menu dans notre mediaControl
        mediaControl.setTop(menuBar);
        primaryStage.setScene(new Scene(mediaControl));

                }
            });
            
            
      //on definit l'action a effectuer quand on clique sur le sous menu Quitter
      
      exitItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
      
      
       //on definit l'action a effectuer quand on clique sur le sous menu A propos
      
      aboutItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
            JOptionPane jop = new JOptionPane();
		String mot="Mon Lecteur Multimedia\n" +
			   "Free Licence\n"+"Version 1.0";
		jop.showMessageDialog(null,mot, "Information", JOptionPane.INFORMATION_MESSAGE);
	}            
        });

        MediaControl mediaControl = new MediaControl(null);
        //mediaControl.setMinSize(480,280);
        mediaControl.setPrefSize(LARGEUR,HAUTEUR);
        //mediaControl.setMaxSize(480,280);
        mediaControl.setTop(menuBar);

        primaryStage.setScene(new Scene(mediaControl));
        

        //root.getChildren().add(mediaControl);*/
    }

    public void play() {
        Status status = mediaPlayer.getStatus();
        if (status == Status.UNKNOWN
            || status == Status.HALTED)
        {
            //System.out.println("Player is in a bad or unknown state, can't play.");
            return;
        }
        
        if (status == Status.PAUSED
         || status == Status.STOPPED
         || status == Status.READY)
        {
            mediaPlayer.play();
        }
    }

    @Override public void stop() {
        mediaPlayer.stop();
    }

    //mediaControl est le panneau principal de notre lecteur: la zone de vue de la video plus les boutons de lecture de la video 
    public class MediaControl extends BorderPane {
        private MediaPlayer mp;
        private MediaView mediaView;
        private final boolean repeat = false;
        private boolean stopRequested = false;
        private boolean atEndOfMedia = false;
        private Duration duration;
        private Slider timeSlider;
        private Label playTime;
        private Slider volumeSlider;
        private HBox mediaBar;
        private final Image PlayButtonImage = new Image(AdvancedMedia.class.getResourceAsStream("playbutton.png"));
        private final Image PauseButtonImage = new Image(AdvancedMedia.class.getResourceAsStream("pausebutton.png"));
        ImageView imageViewPlay = new ImageView(PlayButtonImage);
        ImageView imageViewPause = new ImageView(PauseButtonImage);
        private Pane mvPane;
        private Stage newStage;
        private boolean fullScreen = false;
       
     
        
        public MediaControl(final MediaPlayer mp) {
            this.mp=mp;
            setStyle("-fx-background-color: #bfc2c7;"); // TODO: Use css file
            mediaView = new MediaView(mp);
            mvPane = new Pane();
            mvPane.getChildren().add(mediaView);
            mvPane.setStyle("-fx-background-color: black;"); // TODO: Use css file
            setCenter(mvPane);
            mediaBar = new HBox(5.0);
            mediaBar.setPadding(new Insets(5, 10, 5, 10));
            mediaBar.setAlignment(Pos.CENTER_LEFT);
            BorderPane.setAlignment(mediaBar, Pos.CENTER);
            
            
            final Button playButton  = ButtonBuilder.create()
                    .minWidth(Control.USE_PREF_SIZE)
                    .build();
            
            playButton.setGraphic(imageViewPlay);
            if(mp!=null){
            playButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    updateValues();
                    Status status = mp.getStatus();
                    if (status == Status.UNKNOWN
                        || status == Status.HALTED)
                    {
                        // don't do anything in these states
                        return;
                    }

                    if (status == Status.PAUSED
                        || status == Status.READY
                        || status == Status.STOPPED)
                    {
                        // rewind the movie if we're sitting at the end
                        if (atEndOfMedia) {
                            mp.seek(mp.getStartTime());
                            atEndOfMedia = false;
                            playButton.setGraphic(imageViewPlay);
                            //playButton.setText(">");
                            updateValues();
                        }
                        mp.play();
                        playButton.setGraphic(imageViewPause);
                        //playButton.setText("||");
                    }
                    else {
                        mp.pause();
                    }
                }
            });
            mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    updateValues();
                }
            });
            mp.setOnPlaying(new Runnable() {
                public void run() {
                    
                    if (stopRequested) {
                        mp.pause();
                        stopRequested = false;
                    } else {
                        playButton.setGraphic(imageViewPause);
                        //playButton.setText("||");
                    }
                }
            });
            mp.setOnPaused(new Runnable() {
                public void run() {
                    
                    playButton.setGraphic(imageViewPlay);
                    //playButton.setText("||");
                }
            });
            mp.setOnReady(new Runnable() {
                public void run() {
                    duration = mp.getMedia().getDuration();
                    updateValues();
                }
            });
            
            mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
            mp.setOnEndOfMedia(new Runnable() {
                public void run() {
                    if (!repeat) {
                        playButton.setGraphic(imageViewPlay);
                        //playButton.setText(">");
                        stopRequested = true;
                        atEndOfMedia = true;
                    }
                }
            });
            }else {}
            mediaBar.getChildren().add(playButton);
            
            // Time label
            Label timeLabel = new Label("Time");
            timeLabel.setMinWidth(Control.USE_PREF_SIZE);
            mediaBar.getChildren().add(timeLabel);
            
            
            // Time slider
            timeSlider = SliderBuilder.create()
                    .minWidth(30)
                    .maxWidth(Double.MAX_VALUE)
                    .build();
            HBox.setHgrow(timeSlider, Priority.ALWAYS);
            timeSlider.valueProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    if (timeSlider.isValueChanging()) {
                        // multiply duration by percentage calculated by slider position
                        if(duration!=null) {
                            mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
                        }
                        updateValues();

                    }
                }
            });
            mediaBar.getChildren().add(timeSlider);
            
            // Play label
            playTime = LabelBuilder.create()
                    //.prefWidth(130)
                    .minWidth(Control.USE_PREF_SIZE)
                    .build();
            
            mediaBar.getChildren().add(playTime);
            
            
            //Fullscreen button
            
            Button buttonFullScreen = ButtonBuilder.create()
                    .text("Full Screen")
                    .minWidth(Control.USE_PREF_SIZE)
                    .build();
            
            buttonFullScreen.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (!fullScreen){
                    newStage = new Stage();
                    newStage.fullScreenProperty().addListener(new ChangeListener<Boolean>() {
                        @Override public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                            onFullScreen(); 
                        }
                    });
                    final BorderPane borderPane = new BorderPane(){
                        
                    };
                   
                    setCenter(null);
                    setBottom(null);
                    borderPane.setCenter(mvPane);
                    borderPane.setBottom(mediaBar);
                    
                    Scene newScene = new Scene(borderPane);
                    newStage.setScene(newScene);
                    //Workaround for disposing stage when exit fullscreen
                    newStage.setX(-100000);
                    newStage.setY(-100000);
                    
                    newStage.setFullScreen(true);
                    fullScreen = true;
                    newStage.show();
                    
                }
                    else{
                        //toggle FullScreen
                        fullScreen = false;
                        newStage.setFullScreen(false);
                        
                    }
                }
                
            });
            mediaBar.getChildren().add(buttonFullScreen);
            
            // Volume label
            Label volumeLabel = new Label("Vol");
            volumeLabel.setMinWidth(Control.USE_PREF_SIZE);
            mediaBar.getChildren().add(volumeLabel);
            
            // Volume slider
            volumeSlider = SliderBuilder.create()
                    .prefWidth(70)
                    .minWidth(30)
                    .maxWidth(Region.USE_PREF_SIZE)
                    .build();
            volumeSlider.valueProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                }
            });
            volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (volumeSlider.isValueChanging()) {
                        mp.setVolume(volumeSlider.getValue() / 100.0);
                    }
                }
            });
            mediaBar.getChildren().add(volumeSlider);
            
            setBottom(mediaBar);
            
        }

        protected void onFullScreen(){
            if (!newStage.isFullScreen()){
                
                fullScreen = false;
                setCenter(mvPane);
                setBottom(mediaBar);
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        newStage.close();
                        }
                    });
                
            }
        }
        
        protected void updateValues() {
            if (playTime != null && timeSlider != null && volumeSlider != null && duration != null) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        Duration currentTime = mp.getCurrentTime();
                        playTime.setText(formatTime(currentTime, duration));
                        timeSlider.setDisable(duration.isUnknown());
                        if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
                            timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
                        }
                        if (!volumeSlider.isValueChanging()) {
                            volumeSlider.setValue((int) Math.round(mp.getVolume() * 100));
                        }
                    }
                });
            }
        }

        
        private String formatTime(Duration elapsed, Duration duration) {
            int intElapsed = (int)Math.floor(elapsed.toSeconds());
            int elapsedHours = intElapsed / (60 * 60);
            if (elapsedHours > 0) {
                intElapsed -= elapsedHours * 60 * 60;
            }
            int elapsedMinutes = intElapsed / 60;
            int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

            if (duration.greaterThan(Duration.ZERO)) {
                int intDuration = (int)Math.floor(duration.toSeconds());
                int durationHours = intDuration / (60 * 60);
                if (durationHours > 0) {
                    intDuration -= durationHours * 60 * 60;
                }
                int durationMinutes = intDuration / 60;
                int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

                if (durationHours > 0) {
                    return String.format("%d:%02d:%02d/%d:%02d:%02d",
                                         elapsedHours, elapsedMinutes, elapsedSeconds,
                                         durationHours, durationMinutes, durationSeconds);
                } else {
                    return String.format("%02d:%02d/%02d:%02d",
                                         elapsedMinutes, elapsedSeconds,
                                         durationMinutes, durationSeconds);
                }
            } else {
                if (elapsedHours > 0) {
                    return String.format("%d:%02d:%02d",
                                         elapsedHours, elapsedMinutes, elapsedSeconds);
                } else {
                    return String.format("%02d:%02d",
                                         elapsedMinutes, elapsedSeconds);
                }
            }
        }
    }

    //methode de lancement de l'application
    @Override public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
        //play();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX 
     * application. main() serves only as fallback in case the 
     * application can not be launched through deployment artifacts,
     * e.g., in IDEs with limited FX support. NetBeans ignores main().
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
