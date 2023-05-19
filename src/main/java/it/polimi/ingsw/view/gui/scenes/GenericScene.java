package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.ClientController;

/**
 * Controller set common between scenes (static)
 */
public abstract class GenericScene {
    protected static ClientController controller;

    public static void setController(ClientController contr){
        controller = contr;
    }
}
