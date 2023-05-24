package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * Controller of the scene where at most four players wait for the lobby master (one of them) to start the game
 */
public class LobbyScene extends GenericScene{

    //An object for each player icon and username
    @FXML private Pane pane1;
    @FXML private Pane pane2;
    @FXML private Pane pane3;
    @FXML private Pane pane4;
    @FXML private Label label1;
    @FXML private Label label2;
    @FXML private Label label3;
    @FXML private Label label4;

    @FXML private Label lobbyID;
    @FXML private Label playerNum;

    @FXML private Button start;


    /**
     * Sends the request to the server to start the game
     */
    @FXML protected void onStartGameClick(){
        controller.startGame();
        enableStartButton(false);
    }

    public void showStart(){
        start.setVisible(true);
    }
    public void enableStartButton(boolean enabled){
        start.setDisable(!enabled);
    }

    public void showLobbyID(String lobbyID){
        this.lobbyID.setText(lobbyID);
        this.lobbyID.setVisible(true);
    }

    /**
     * Displays on screen the updated username list
     * @param playerUsernames username list of the players in the lobby up to that point
     */
    public void showRefreshedConnectedPlayers(ArrayList<String> playerUsernames){

        //Setting the username of the lobby master
        pane1.setVisible(true);
        label1.setText(playerUsernames.get(0));
        this.playerNum.setText("Connected players: 1/4");

        int playerNum = playerUsernames.size();
        //Setting the username of the second player
        if (playerNum > 1){
            pane2.setVisible(true);
            label2.setText(playerUsernames.get(1));
            this.playerNum.setText("Connected players: 2/4");
        }
        //Setting the username of the third player
        if (playerNum > 2){
            pane3.setVisible(true);
            label3.setText(playerUsernames.get(2));
            this.playerNum.setText("Connected players: 3/4");
        }

        //Setting the username of the fourth player
        if (playerNum > 3){
            pane4.setVisible(true);
            label4.setText(playerUsernames.get(3));
            this.playerNum.setText("Connected players: 4/4");
        }

        refreshPlayerNum(playerNum);
    }

    private void refreshPlayerNum(int num){
        playerNum.setText("Connected users: " + num + "/4");
    }

}
