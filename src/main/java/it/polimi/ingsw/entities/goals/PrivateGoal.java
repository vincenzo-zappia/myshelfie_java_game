package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;

public class PrivateGoal implements Goal{
    private final String fileXML;

    public PrivateGoal(String fileXML){
        this.fileXML = fileXML;
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        return 0;
    }
}
