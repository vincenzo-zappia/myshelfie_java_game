package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.Main;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.view.gui.scenes.GenericScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {

    //region ATTRIBUTES
    private GUIManager guiManager;
    private Scene scene;
    private FXMLLoader currentLoader;
    private GenericScene currentController;
    //endregion

    //region JAVAFX
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try{
            //Loading the scene where the player is asked to insert server IP and port
            currentLoader = new FXMLLoader(GUI.class.getResource("network.fxml"));
            Scene scene = new Scene(currentLoader.load(), 1000,600);
            this.currentController = currentLoader.getController();

            this.scene = scene;
            startupStage(stage);
            stage.setScene(scene);
            stage.show();
            guiManager = new GUIManager(this);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
    //endregion

    //region METHODS
    /**
     * Initializes the stage
     * @param stage the stage to initialize
     */
    private void startupStage(Stage stage){
        stage.setTitle("MyShelfie");
        stage.getIcons().add(new Image(Main.getResourcePath() + "/assets/misc/icon.png"));
        stage.setFullScreen(true);
        stage.setMinWidth(1000);
        stage.setMinHeight(600);
    }

    /**
     * Starts the game application
     * @param client who uses the application
     */
    public void startConnection(Client client){
        guiManager.start(client);
        loadScene("username.fxml");
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
    //endregion

}
