package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;

public class PrivateGoal implements Goal{
    private static int SCORE = 1; //TODO:inserire valore del goal (attenzione in questo caso non è final)
    private final String fileXML;

    public PrivateGoal(String fileXML){
        this.fileXML = fileXML;
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        return SCORE;
    }
}
