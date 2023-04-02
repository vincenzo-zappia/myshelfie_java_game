package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;

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
