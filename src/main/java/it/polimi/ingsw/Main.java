package it.polimi.ingsw;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.gui.GUI;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        argsCheck(args);

        //Making the player choose the user interface
        System.out.println("Select user interface:");
        System.out.println("[0] CLI \n[1] GUI");
        String selection = in.nextLine();

        //Starting the CLI
        if (selection.equals("0")) {
            CLI cli = new CLI(requestNetworkInformation());
            new Thread(cli).start();
        }

        //Starting the GUI
        else if (selection.equals("1")) {
            GUI.main(args);
        }

        //Shutting off if the selection doesn't match any user interface
        else{
            System.out.println("Incorrect selection!");
            exit(0);
        }

    }

    //region METHODS
    private static void argsCheck(String[] args){
        if(args.length>0){
            switch (args[0]){
                case "--server" -> {
                    Server.main(args);
                    return;
                }
            }
        }
    }

    /**
     * Requests the user the server IP and port and creates a new Client instance
     * @return the new Client instance
     */
    private static Client requestNetworkInformation(){

        do {
            //Asking and checking the IP
            String ip;
            System.out.println("Insert the IP of the server:");
            ip = in.nextLine();

            //Asking and checking the port
            int port;
            System.out.println("Insert the port of the server:");

            //Creating a new Client instance out of the previously selected ip and port
            try {
                port = Integer.parseInt(in.nextLine());
                return new Client(ip, port);
            } catch (UnknownHostException u) {
                System.out.println("IP not valid!");
            } catch (IOException | NumberFormatException e) {
                System.out.println("Port not valid!");
            }
        }while(true);

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
    //endregion

}
