package it.polimi.ingsw;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.gui.GUI;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        //Making the player choose the user interface
        System.out.println("Select user interface:");
        System.out.println("[0] CLI \n[1] GUI");
        String selection = in.nextLine();

        //Starting the chosen user interface
        if (selection.equals("0")){

            //TODO: Scelta IP e port e verifica
            Client client;
            try {
                client = new Client("localhost", 2023);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            CLI cli = new CLI(client);
            new Thread(cli).start();
        }

        else if (selection.equals("1")) {
            GUI.main(args);
        }

        //Shutting off if the selection doesn't match any user interface
        else{
            System.out.println("Incorrect selection!");
            exit(0);
        }

    }

    /**
     * @return The path of the resources root
     */
    public static String getResourcePath() {
        String basePath;
        URI uri;

        //Getting the base path of the Main class
        try {
            uri = Objects.requireNonNull(Main.class.getResource("")).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Path path = Paths.get(uri);

        //Going down three directory levels (it/polimi/ingsw)
        basePath = path.getParent().getParent().getParent().toString();

        return basePath;
    }

}
