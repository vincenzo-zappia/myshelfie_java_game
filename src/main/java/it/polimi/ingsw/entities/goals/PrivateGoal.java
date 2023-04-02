package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;

public class PrivateGoal implements Goal{
    private static int SCORE = 0; //TODO:inserire valore del goal (attenzione in questo caso non è final)
    private final String fileXML;

    public PrivateGoal(String fileXML){
        this.fileXML = fileXML;
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        return SCORE;
    }
}
