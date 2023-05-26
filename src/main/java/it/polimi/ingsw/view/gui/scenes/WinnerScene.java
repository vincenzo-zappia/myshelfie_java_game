package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.entities.util.SerializableTreeMap;
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
    public void displayScoreboard(SerializableTreeMap<String, Integer> scoreboardMap){

        //TODO da verificare funzionamento
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(scoreboardMap.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                // Ordine decrescente
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });
        TreeMap<String, Integer> ordered = new TreeMap<>();

        for (Map.Entry<String, Integer> entry : entryList) {
            ordered.put(entry.getKey(), entry.getValue());
        }

        String[] usernames = ordered.keySet().toArray(new String[0]);
        Integer[] scores = ordered.values().toArray(new Integer[0]);

        for (int i=0; i< ordered.size(); i++) {
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
