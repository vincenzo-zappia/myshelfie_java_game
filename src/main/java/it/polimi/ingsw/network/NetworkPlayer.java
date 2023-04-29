package it.polimi.ingsw.network;

import it.polimi.ingsw.mechanics.VirtualView;

public class NetworkPlayer {
    private final String username;
    private final ClientHandler clientHandler;
    private final VirtualView virtualView;

    public NetworkPlayer(String username, ClientHandler clientHandler, VirtualView virtualView) {
        this.username = username;
        this.clientHandler = clientHandler;
        this.virtualView = virtualView;
    }

    public String getUsername() {
        return username;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }
}
