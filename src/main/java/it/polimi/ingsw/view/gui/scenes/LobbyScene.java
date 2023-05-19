package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class LobbyScene extends GenericScene{

    @FXML private Pane pane1;
    @FXML private Pane pane2;
    @FXML private Pane pane3;
    @FXML private Pane pane4;
    @FXML private Label label1;
    @FXML private Label label2;
    @FXML private Label label3;
    @FXML private Label label4;

    public LobbyScene(){

        //Making all the icons invisible before the players join the lobby
        pane1.setVisible(false);
        pane2.setVisible(false);
        pane3.setVisible(false);
        pane4.setVisible(false);

    }


    /**
     * Sends the start game request to the server after the button click
     */
    @FXML protected void onStartGameClick(){
        controller.startGame();
    }

    /**
     * Displays on screen the updated username list
     * @param playerUsernames username list of the players in the lobby up to that point
     */
    public void showRefreshedConnectedPlayers(ArrayList<String> playerUsernames){

        //Setting the username of the lobby master
        pane1.setVisible(true);
        label1.setText(playerUsernames.get(0));

        //Setting the username of the second player
        if (playerUsernames.size() > 1){
            pane2.setVisible(true);
            label2.setText(playerUsernames.get(1));
        }
        //Setting the username of the third player
        if (playerUsernames.size() > 2){
            pane3.setVisible(true);
            label3.setText(playerUsernames.get(2));
        }

        //Setting the username of the fourth player
        if (playerUsernames.size() > 3){
            pane4.setVisible(true);
            label4.setText(playerUsernames.get(3));
        }
    }

}
