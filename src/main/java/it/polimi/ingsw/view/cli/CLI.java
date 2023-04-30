package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.messages.CreateLobby;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.ResponseMessage;

import java.util.Scanner;

public class CLI implements Runnable{

    private final Client client;

    public CLI() {
        client = new Client("10.0.0.3", 2023);
    }

    @Override
    public void run() {
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
                ResponseMessage response = (ResponseMessage) msg;
                if (response.getResponse()) System.out.println("bravooo!!");
            }
        }
    }
}
