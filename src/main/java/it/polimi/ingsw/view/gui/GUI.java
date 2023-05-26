package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.Main;
import it.polimi.ingsw.view.gui.scenes.GenericScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {
    private Scene scene;
    private FXMLLoader currentLoader;
    private GenericScene currentController;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try{
            //TODO: Scena iniziale IP e port
            currentLoader = new FXMLLoader(GUI.class.getResource("username.fxml"));
            Scene scene = new Scene(currentLoader.load(), 900,600);
            this.currentController = currentLoader.getController();

            this.scene = scene;
            startupStage(stage);
            stage.setScene(scene);
            stage.show();
            GUIManager guiManager = new GUIManager(this);
            guiManager.start();

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

    private void startupStage(Stage stage){
        stage.setTitle("MyShelfie");
        stage.getIcons().add(new Image(Main.getResourcePath() + "\\assets\\misc\\icon.png"));
        stage.setFullScreen(true);
        stage.setMinWidth(1000);
        stage.setMinHeight(600);
    }

    /**
     * Changes the scene root to the selected one by loading its fxml
     * @param filename name of the fxml file
     */
    public void loadScene(String filename){
        currentLoader = new FXMLLoader(GUI.class.getResource(filename));
        try {
            scene.setRoot(currentLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.currentController = currentLoader.getController();

    }

    /**
     * Returns the controller of the current scene
     * @return controller of the current scene
     */
    public GenericScene getController(){
        return currentController;
    }

    
}
