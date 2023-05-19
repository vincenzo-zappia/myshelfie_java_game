package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientController;
import it.polimi.ingsw.util.BoardTile;
import it.polimi.ingsw.util.Tile;
import it.polimi.ingsw.view.UserInterface;
import it.polimi.ingsw.view.gui.scenes.*;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.HashMap;

public class GUIManager implements UserInterface {

    //region ATTRIBUTES
    private final GUI gui;
    private GenericScene controller;
    private String currentScene;
    //endregion

    public GUIManager(GUI gui){
        this.gui = gui;
        ClientController clientController = new ClientController(this, new Client("localhost", 2023));
        GenericScene.setController(clientController);
        GenericScene.setGui(gui);
    }


    //region USER INTERFACE
    @Override
    public void confirmUsername(boolean response) {
        UsernameScene usernameScene = (UsernameScene) gui.getController();
        if(response) gui.loadScene("connection.fxml");
        //TODO: Gestire else (chiamate metodi di usernameScene)
    }

    @Override
    public void confirmAccess(boolean response) {
        Platform.runLater(() -> {
            AccessScene accessScene = (AccessScene) gui.getController();
            if(response) gui.loadScene("lobby.fxml");
            //TODO: gestire else (id lobby non esistente)
        });

    }

    @Override
    public void refreshConnectedPlayers(ArrayList<String> playerUsernames) {
        //TODO: Check con enum?
        LobbyScene lobbyScene = (LobbyScene) gui.getController();
        lobbyScene.showRefreshedConnectedPlayers(playerUsernames);
    }
    //endregion

    //region VIEW
    @Override
    public void sendGenericResponse(boolean response, String content) {
        Platform.runLater(() -> gui.getController().showMessage(response, content));
    }

    @Override
    public void showCurrentPlayer(String currentPlayer) {
        GameScene gameScene = (GameScene) gui.getController();
        //TODO: Print a schermo current player nella text box notifiche di gioco
    }

    @Override
    public void sendCheckedCoordinates(int[][] coordinates) {

    }

    @Override
    public void showRemovedCards(int[][] coordinates) {

    }

    @Override
    public void showUpdatedBookshelf(Tile[][] bookshelf) {

    }

    @Override
    public void showRefilledBoard(BoardTile[][] boardTiles) {

    }

    @Override
    public void showGoalsDetails(Goal[] commonGoals, PrivateGoal privateGoal) {

    }

    @Override
    public void showScoreboard(HashMap<String, Integer> scoreboard) {

    }
    //endregion

}
