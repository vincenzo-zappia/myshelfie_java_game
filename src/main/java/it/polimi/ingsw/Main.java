package it.polimi.ingsw;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.view.cli.CLI;

import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Select user interface:");
        System.out.println("[0] CLI \n[1] GUI");
        String selection = in.nextLine();

        Client client = new Client("localhost", 2023);
        CLI cli = new CLI(client);

        //Selection of the CLI as the user interface
        if (selection.equals("0")) new Thread(cli).start();

        //Selection of the GUI as the user interface
        else if (selection.equals("1")) System.out.println("GUI"); //TODO: Parte GUI

        //Shitting off if the selection doesn't match
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

}
