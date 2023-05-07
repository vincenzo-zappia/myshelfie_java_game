package it.polimi.ingsw;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.view.cli.CLI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Cosa vuoi avvire?");
        System.out.println("[0] CLI");
        int selezione = Integer.parseInt(in.nextLine());
        Client client = new Client("10.0.0.2", 2023);
        CLI cli = new CLI(client);
        if (selezione==0) new Thread(cli).start();
    }

    private boolean gh(){
        return true;
    }

}
