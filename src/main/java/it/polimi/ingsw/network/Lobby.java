package it.polimi.ingsw.network;

import it.polimi.ingsw.mechanics.Game;
import it.polimi.ingsw.mechanics.GameController;
import it.polimi.ingsw.mechanics.VirtualView;
import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class Lobby {

    //TODO: verificare quali attributi servono effettivamente alla lobby
    //region ATTRIBUTES
    private final int lobbyId;
    private final Server server;
    private ArrayList<ClientHandler> clientHandlers;
    private ArrayList<String> playerUsernames;
    private ArrayList<NetworkPlayer> networkPlayers;
    private boolean inGame;
    private final GameController gameController;
    //endregion

    //TODO: DA REVISIONARE (quando la lobby viene creata non si conoscono gli username di tutti i player)
    //TODO: verificare quali attributi servono alla creazione della lobby

    public Lobby(Server server, String creatorUsername, int lobbyId) {
        this.lobbyId = lobbyId;
        this.server = server;

        addPlayer(creatorUsername);
        inGame = false;

    }

    //TODO: Metodo chiamato da clientHandler per aggiungere se stesso alla Lobby
    public void joinLobby(ClientHandler clientHandler){
        //TODO: Come e quando sceglie lo username il giocatore?
        //addPlayer();
    }

    //TODO: Fondere con joinLobby()
    public void addPlayer(String username){
        //TODO: implementare la verifica che la lobby non sia piena e restituzione messaggio di accettazione o errore
        playerUsernames.add(username);

        //TODO: Creazione hashmap (username, virtualView). Come viene passato il parametro clientHandler al costruttore di VirtualView?
        //networkPlayers.add(new NetworkPlayer(username, new VirtualView()));

        //TODO: Una volta aggiunto l'ultimo player procedere con la chiamata di startGame()
    }

    //TODO: Metodo che deve inizializzare Game e GameController (a cui deve essere passata l'hashmap)
    public void startGame(){
        assert (playerUsernames.size()>1 && playerUsernames.size()<=4);
        //TODO: implementare invio messaggio di startgame (dopo verifica dei prerequisiti)

        gameController = new GameController(playerUsernames, new Game(playerUsernames), );
    }

    public void sendToController(Message msg){
        gameController.messageHandler(msg);
    }
}
