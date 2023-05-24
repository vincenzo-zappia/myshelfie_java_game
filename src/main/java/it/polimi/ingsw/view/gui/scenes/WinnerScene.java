package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.entities.util.SerializableTreeMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Map;

public class WinnerScene extends GenericScene {

    //region ATTRIBUTES
    @FXML private GridPane scoreboard;
    //endregion

    //region CLICK
    @FXML public void onNewGameClick(){
        //todo: vedere se mantenere
    }

    @FXML public void onQuitClick(){
        //todo: inserire un client.disconnect()
        gui.stop();
    }
    //endregion

    //region EXTERNAL METHOD
    public void displayScoreboard(SerializableTreeMap<String, Integer> scoreboardMap){

        String[] usernames = scoreboardMap.keySet().toArray(new String[0]);
        Integer[] scores = scoreboardMap.values().toArray(new Integer[0]);

        for (int i=0; i< scoreboardMap.size(); i++) {
            Label username = (Label) getNodeByRowColumnIndex(i+1,0,scoreboard),
            score = (Label) getNodeByRowColumnIndex(i+1,1,scoreboard);

            username.setVisible(true);
            score.setVisible(true);

            username.setText(usernames[i]);
            score.setText(String.valueOf(scores[i]));
        }
    }
    //endregion

}
