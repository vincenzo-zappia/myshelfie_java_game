package it.polimi.ingsw.network;

import it.polimi.ingsw.mechanics.VirtualView;

/**
 * Class that links the username of the player to the ClientHandler and VirtualView of their relative Client
 */
public class NetworkPlayer {

    //region ATTRIBUTES
    private final String username;
    private final ClientHandler clientHandler;
    private VirtualView virtualView;
    //endregion

    //region CONSTRUCTOR
    public NetworkPlayer(String username, ClientHandler clientHandler) {
        this.username = username;
        this.clientHandler = clientHandler;
    }
    //endregion

    //region METHODS
    public String getUsername() {
        return username;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }

    public void setVirtualView(VirtualView virtualView){
        this.virtualView = virtualView;
    }
    //endregion

}
