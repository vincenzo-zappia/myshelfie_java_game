package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.entities.Scoreboard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.*;

public class WinnerScene extends GenericScene {

    //region ATTRIBUTES
    @FXML private GridPane scoreboard;
    //endregion

    //region CLICK
    @FXML public void onNewGameClick(){
        controller.sendNewGame(true);
        gui.loadScene("connection.fxml");
    }

    @FXML public void onQuitClick(){
        controller.sendNewGame(false);
        controller.stopClientConnection();
        gui.stop();
    }
    //endregion

    //region EXTERNAL METHOD
    public void displayScoreboard(Scoreboard s){

        for (int i=0; i< s.getUser_scores().length; i++) {
            Label username = (Label) getNodeByRowColumnIndex(i+1,0,scoreboard),
            score = (Label) getNodeByRowColumnIndex(i+1,1,scoreboard);

            username.setVisible(true);
            score.setVisible(true);

            username.setText(s.getUsername(i));
            score.setText(String.valueOf(s.getScores(i)));
        }
    }
    //endregion

}
