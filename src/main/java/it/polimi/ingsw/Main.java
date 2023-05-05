package it.polimi.ingsw;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.util.CardType;
import it.polimi.ingsw.view.cli.CLI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Cosa vuoi avvire?");
        System.out.println("[0] CLI");
        int selezione = Integer.parseInt(in.nextLine());
        Client client = new Client("localhost", 2023);
        CLI cli = new CLI(client);
        if (selezione==0) new Thread(cli).start();
    }
}
