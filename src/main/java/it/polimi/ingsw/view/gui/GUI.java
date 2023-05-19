package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientController;
import it.polimi.ingsw.view.gui.scenes.GenericScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {
    //TODO: Spostare creazione client nel costruttore di GUI e CLI

    private Stage stage;
    private Scene scene;
    private FXMLLoader currentLoader;

    public static void main(String[] args) {

        launch();
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        try{
            //TODO: setup scena iniziale;
            FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("Username.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 300,300);
            this.scene = scene;
            stage.setTitle("MyShelfie");
            stage.setFullScreen(true);
            stage.setScene(scene);
            stage.show();
            GUIManager guiManager = new GUIManager(this);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    protected void loadScene(String filename){
        currentLoader = new FXMLLoader(GUI.class.getResource(filename));
        try {
            scene.setRoot(currentLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    protected GenericScene getController(){
        return currentLoader.getController();
    }

}
