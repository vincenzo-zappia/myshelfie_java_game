package it.polimi.ingsw;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.view.cli.CLI;

import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Select user interface:");
        System.out.println("[0] CLI");
        String selezione = in.nextLine();
        Client client = new Client("10.0.0.4", 2023);
        CLI cli = new CLI(client);
        if (selezione.equals("0")) new Thread(cli).start();
        else{
            System.out.println("Incorrect selection!");
            exit(0);
        }
    }

    //questo metodo dovrebbe servire per verificare la porta e l'ip
    private boolean gh(){
        return true;
    }

}
