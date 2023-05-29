package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Controller of the scene where the user has to choose IP and port of the server before starting the game application
 */
public class NetworkScene extends GenericScene {

    @FXML private TextField ipTextField;
    @FXML private TextField portTextField;

    @FXML private void onConnectClick() {

        try {
            Client client = new Client(ipTextField.getText(), Integer.parseInt(portTextField.getText()));
            gui.startConnection(client);
        } catch (UnknownHostException u){
            showMessage(false, "IP not valid!");
        } catch (IOException e) {
            showMessage(false, "Port not valid!");
        }
        ipTextField.clear();
        portTextField.clear();
    }

}
