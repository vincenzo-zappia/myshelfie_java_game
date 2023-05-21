package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.entities.util.SerializableTreeMap;
import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.entities.util.BoardTile;
import it.polimi.ingsw.entities.util.Tile;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientController;
import it.polimi.ingsw.view.UserInterface;
import it.polimi.ingsw.view.gui.scenes.*;
import javafx.application.Platform;

import java.util.ArrayList;

public class GUIManager implements UserInterface {
    private final GUI gui;
    private String currentScene;

    public GUIManager(GUI gui){
        this.gui = gui;
        ClientController clientController = new ClientController(this, new Client("10.0.0.4", 2023));
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
            //AccessScene accessScene = (AccessScene) gui.getController();
            if(response) gui.loadScene("lobby.fxml");
            //TODO: gestire else (id lobby non esistente)
        });

    }

    @Override
    public void refreshConnectedPlayers(ArrayList<String> playerUsernames) {
        Platform.runLater(() -> {
            //TODO: Check con enum?
            LobbyScene lobbyScene = (LobbyScene) gui.getController();
            lobbyScene.showRefreshedConnectedPlayers(playerUsernames);
        });

    }

    @Override
    public void confirmStartGame(boolean response) {
        Platform.runLater(() -> {
            if(response) {
                gui.loadScene("game.fxml");
                GameScene gameScene = (GameScene) gui.getController();
                gameScene.initGame();
            }
        });
    }
    //endregion

    //region VIEW
    @Override
    public void sendGenericResponse(boolean response, String content) {
        Platform.runLater(() -> gui.getController().showMessage(response, content));
    }

    @Override
    public void showCurrentPlayer(String currentPlayer) {
        Platform.runLater(() -> {
            GameScene gameScene = (GameScene) gui.getController();
            //TODO: Print a schermo current player nella text box notifiche di gioco
        });

    }

    @Override
    public void sendCheckedCoordinates(int[][] coordinates) {
        Platform.runLater(() -> {
            GameScene gameScene = (GameScene) gui.getController();
        });

    }

    @Override
    public void showRemovedCards(int[][] coordinates) {
        Platform.runLater(() -> {
            GameScene gameScene = (GameScene) gui.getController();
            gameScene.removeCards(coordinates);
        });

    }

    @Override
    public void showUpdatedBookshelf(Tile[][] bookshelf) {
        Platform.runLater(() -> {
            GameScene gameScene = (GameScene) gui.getController();
            gameScene.displayBookshelf(bookshelf);
        });

    }

    @Override
    public void showRefilledBoard(BoardTile[][] boardTiles) {
        Platform.runLater(() -> {
            GameScene gameScene = (GameScene) gui.getController();
            gameScene.displayBoard(boardTiles);
        });

    }

    @Override
    public void showCommonGoals(Goal[] commonGoals) {
        Platform.runLater(() -> {
            GameScene gameScene = (GameScene) gui.getController();
            gameScene.displayCommonGoals(commonGoals);
        });
    }

    @Override
    public void showPrivateGoal(PrivateGoal privateGoal) {
        Platform.runLater(() -> {
            GameScene gameScene = (GameScene) gui.getController();
            gameScene.displayPrivateGoal(privateGoal);
        });
    }

    @Override
    public void showScoreboard(SerializableTreeMap<String, Integer> scoreboard) {

    }

    @Override
    public void showToken(String content) {
        Platform.runLater(() -> {
            GameScene gameScene = (GameScene) gui.getController();
            gameScene.removeToken(content);
        });
    }
    //endregion

}
