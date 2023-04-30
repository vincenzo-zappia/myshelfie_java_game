package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.messages.CreateLobby;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.ResponseMessage;

import java.util.Scanner;

public class CLI implements Runnable{

    //region ATTRIBUTES
    private final Client client;
    //endregion

    public CLI() {
        client = new Client("10.0.0.3", 2023);
        new Thread(client).start(); //Loop execution of receiveMessage by Client
    }
    
    @Override
    public void run() {
    /*
        //TODO: Come fa la CLI a tenere traccia dei cmabiamenti che avvengono lato Client
        //TODO:con l'invio di comandi in base ai messaggi ricevuti?
        Scanner in = new Scanner(System.in);

        System.out.println("[0] Create new lobby");
        System.out.println("[1] Connect to existing lobby");

        int selection = Integer.parseInt(in.nextLine());

        System.out.println("Inserisci il tuo username:");
        String username = in.nextLine();

        if (selection == 0){
            client.sendMessage(new CreateLobby(username));
            Message msg = client.receiveMessage();
            if(msg.getType() == MessageType.LOBBY_CREATION_RESPONSE){
                System.out.println("bravooo!!");
            }
        }

     */

    }
}
