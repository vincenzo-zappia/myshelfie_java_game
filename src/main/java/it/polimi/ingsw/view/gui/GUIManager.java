package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.util.BoardTile;
import it.polimi.ingsw.util.Tile;
import it.polimi.ingsw.view.UserInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class GUIManager implements UserInterface {
    private GUI gui;

    //region USER INTERFACE
    @Override
    public void confirmUsername(boolean response) {

    }

    @Override
    public void confirmAccess(boolean response) {

    }

    @Override
    public void refreshConnectedPlayers(ArrayList<String> playerUsernames) {

    }
    //endregion

    //region VIEW
    @Override
    public void sendGenericResponse(boolean response, String content) {

    }

    @Override
    public void showCurrentPlayer(String currentPlayer) {

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
