package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.entities.Scoreboard;
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

/**
 * Manages the reception of messages from the server and calls their needed implementation relatively to the scene
 */
public class GUIManager implements UserInterface {

    //region ATTRIBUTES
    private final GUI gui;
    //endregion

    //region CONSTRUCTOR
    public GUIManager(GUI gui){
        this.gui = gui;
        GenericScene.setGui(gui);
    }

    public void start(Client client){
        ClientController clientController;
        clientController = new ClientController(this, client);
        GenericScene.setController(clientController);
    }
    //endregion

    //region USER INTERFACE
    @Override
    public void confirmUsername(boolean response) {
        UsernameScene usernameScene = (UsernameScene) gui.getController();
        if(response) gui.loadScene("connection.fxml");
        else usernameScene.setEnable();
    }

    @Override
    public void confirmCreation(String content) {
        Platform.runLater(() -> {
            LobbyScene lobbyScene = (LobbyScene) gui.getController();
            lobbyScene.showStart();
            lobbyScene.showLobbyID(content);
        });
    }

    @Override
    public void confirmAccess(boolean response, String content) {
        Platform.runLater(() -> {
            if(response) {
                gui.loadScene("lobby.fxml");
                LobbyScene lobbyScene = (LobbyScene) gui.getController();
                lobbyScene.showLobbyID(content);
            }
        });

    }

    @Override
    public void refreshConnectedPlayers(ArrayList<String> playerUsernames) {
        Platform.runLater(() -> {
            LobbyScene lobbyScene = (LobbyScene) gui.getController();
            lobbyScene.showRefreshedConnectedPlayers(playerUsernames);
        });

    }

    @Override
    public void showDisconnection() {
        Platform.runLater(() -> {
            gui.loadScene("disconnection.fxml");
            gui.getController().showMessage(false, "A player has disconnected!");
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
            else{
                LobbyScene lobbyScene = (LobbyScene) gui.getController();
                lobbyScene.enableStartButton(true);
            }
        });
    }

    @Override
    public void showChat(String chat) {
        Platform.runLater(() -> {
            GameScene gameScene = (GameScene) gui.getController();
            gameScene.showChat(chat);
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
            gameScene.showMessage(true, "Current player: " + currentPlayer);
        });

    }

    @Override
    public void sendCheckedCoordinates(boolean valid, int[][] coordinates) {
        Platform.runLater(() -> {
            GameScene gameScene = (GameScene) gui.getController();
            gameScene.setSelectable(!valid);
            if (!valid) gameScene.resetBoardCardOpacity();
            else gameScene.switchTab(1); //Switching to the bookshelf tab
        });

    }

    @Override
    public void showRemovedCards(int[][] coordinates) {
        Platform.runLater(() -> {
            GameScene gameScene = (GameScene) gui.getController();
            gameScene.removeCards(coordinates);

            gameScene.setSelectable(true);
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
    public void showScoreboard(Scoreboard scoreboard) {
        Platform.runLater(() -> {
            gui.loadScene("winner.fxml");
            WinnerScene winnerScene = (WinnerScene) gui.getController();
            winnerScene.displayScoreboard(scoreboard);
        });
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
