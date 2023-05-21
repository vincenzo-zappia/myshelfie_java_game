module proj.ingsw.rj {
    requires java.xml;
    requires javafx.fxml;
    requires javafx.controls;

    opens it.polimi.ingsw.view.gui to javafx.fxml;
    opens it.polimi.ingsw.view.gui.scenes to javafx.fxml;

    exports it.polimi.ingsw.entities.util;
    exports it.polimi.ingsw.entities.goals;
    exports it.polimi.ingsw.view.gui;
    exports it.polimi.ingsw.view.gui.scenes;
}
