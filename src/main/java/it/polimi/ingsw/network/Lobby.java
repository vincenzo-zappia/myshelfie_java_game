package it.polimi.ingsw.network;

import it.polimi.ingsw.mechanics.Game;
import it.polimi.ingsw.mechanics.GameController;
import it.polimi.ingsw.network.messages.GameStarted;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.ArrayList;
//TODO: classe che crea effettivamente la partita: crea istanza di Game, crea i CommonGoal da associare a Game, associa Game a GameController
//TODO: e lega i Client ai corrispettivi Player e username
public class Lobby {

    //TODO: verificare quali attributi servono effettivamente alla lobby
    //region ATTRIBUTES
    private final Server server;
    private ArrayList<Client> clients; //TODO: perche ci serve la lista dei client?
    private ArrayList<String> playerList;
    private boolean inGame;
    //endregion

    private GameController gameController;

    //TODO: DA REVISIONARE (quando la lobby viene creata non si conoscono gli username di tutti i player)
    //TODO: verificare quali attributi servono alla creazione della lobby

    public Lobby(Server server, String creatorUsername) {
        inGame=false;
        this.server = server;
        addPlayer(creatorUsername);
    }

    public void addPlayer(String username){
        //TODO: implementare la verifica che la lobby non sia piena e restituzione messaggio di accettazione o errore
        playerList.add(username);
    }
    public Game startMatch(){
        assert (playerList.size()>1 && playerList.size()<=4);

        //TODO: implementare invio messaggio di startgame (dopo verifica dei prerequisiti)
        gameController.getClHndl().sendMessage(new GameStarted(gameController.getCurrentPlayer(), MessageType.GAME_STARTED));

        return new Game(playerList.size());
    }
}
