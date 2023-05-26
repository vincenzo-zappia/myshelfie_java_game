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

        //Shutting off if the selection doesn't match
        else{
            System.out.println("Incorrect selection!");
            exit(0);
        }

    }

    public static String getResourcePath() {
        String basePath;
        URI uri;

        try {
            uri = Objects.requireNonNull(Main.class.getResource("")).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Path path = Paths.get(uri);
        basePath = path.getParent().getParent().getParent().toString();

        return basePath;
    }
}
