package it.polimi.ingsw;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.gui.GUI;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Select user interface:");
        System.out.println("[0] CLI \n[1] GUI");
        String selection = in.nextLine();

        Client client = new Client("localhost", 2023);


        //Selection of the CLI as the user interface
        if (selection.equals("0")){
            CLI cli = new CLI(client);
            new Thread(cli).start();
        }

        //Selection of the GUI as the user interface
        else if (selection.equals("1")) {
            GUI.main(args);
        }

        //Shutting off if the selection doesn't match
        else{
            System.out.println("Incorrect selection!");
            exit(0);
        }

    }

    private String requestIp(){
        return "";
    }

    //questo metodo dovrebbe servire per verificare la porta e l'ip
    private boolean gh(){
        return true;
    }

    public static String getResourcePath() {
        String basePath;
        URI uri;

        try {
            uri = Main.class.getResource("").toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Path path = Paths.get(uri);
        basePath = path.getParent().getParent().getParent().toString();

        return basePath;
    }
}
