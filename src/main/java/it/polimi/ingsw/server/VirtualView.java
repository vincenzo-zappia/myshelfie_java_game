package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observer;

public class VirtualView implements Observer {

    private final ServerSideController serverSideController;

    public VirtualView(ServerSideController serverSideController){
        this.serverSideController = serverSideController;
    }

    @Override
    public void update(){
        //TODO: codice che serializza cambiamento e lo invia al Client (dovrà chiamare metodo di ServerSideController)
        //TODO: VirtualView deve essere osservare GameController che sarà responsabile della serializzazione degli update da inviare
    }
}
