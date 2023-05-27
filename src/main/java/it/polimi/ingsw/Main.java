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

        //Starting the CLI
        if (selection.equals("0")){
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
    /**
     * Requests the user the server IP and port and creates a new Client instance
     * @return the new Client instance
     */
    private static Client requestNetworkInformation(){

        //Asking and checking the IP
        String ip;
        do{
            System.out.println("Insert the IP of the server:");
            ip = in.nextLine();
            if(!checkIP(ip)) System.out.println("The inserted IP is not valid");
        }while(!checkIP(ip));

        //Asking and checking the port
        int port;
        do {
            System.out.println("Insert the port of the server:");
            port = Integer.parseInt(in.nextLine());
            if(!checkPort(port)) System.out.println("The inserted port is not valid");
        }while (!checkPort(port));

        //Creating a new Client instance out of the previously selected ip and port
        try {
            return new Client(ip, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Checks if the inserted IP is valid
     * @param ip to check
     * @return true if the IP is valid
     */
    private static boolean checkIP(String ip){
        //todo fa le cos
        return true;
    }

    /**
     * Checks if the inserted port is valid
     * @param port to check
     * @return true if the port is valid
     */
    private static boolean checkPort(int port){
        //todo le cos fa
        return true;
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
