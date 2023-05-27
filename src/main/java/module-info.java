module proj.ingsw.rj {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.xml;

    opens it.polimi.ingsw.view.gui to javafx.fxml;
    opens it.polimi.ingsw.view.gui.scenes to javafx.fxml;

    exports it.polimi.ingsw.exceptions;
    exports it.polimi.ingsw.mechanics;
    exports it.polimi.ingsw.entities;
    exports it.polimi.ingsw.entities.util;
    exports it.polimi.ingsw.entities.goals;
    exports it.polimi.ingsw.view;
    exports it.polimi.ingsw.view.gui;
    exports it.polimi.ingsw.view.gui.scenes;
    exports it.polimi.ingsw.network;
    exports it.polimi.ingsw.network.messages;
    exports it.polimi.ingsw.network.messages.client2server;
    exports it.polimi.ingsw.observer;
}
