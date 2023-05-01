package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.messages.CreateLobby;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.ResponseMessage;
import it.polimi.ingsw.observer.Subject;

import java.util.Scanner;

public class CLI extends Subject implements Runnable, SubjectView{
    private final Client client;
    private final Scanner scanner;

    public CLI() {
        client = new Client("10.0.0.3", 2023);
        scanner = new Scanner(System.in);
        new Thread(client).start(); //Loop execution of receiveMessage by Client
    }

    //TODO: Registrare il nuovo client appena dopo inserimento username/creazione lobby con this.register(new ClientActionManager(this))
    @Override
    public void run() {
    /*
        //TODO: Come fa la CLI a tenere traccia dei cmabiamenti che avvengono lato Client con l'invio di comandi in base ai messaggi ricevuti?

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

    @Override
    public void askUsername() {
        String username;
        System.out.println("Enter username:");
        do{
            username = scanner.nextLine();
            if (username.equals("")) System.out.println("Enter valid username:");
        }while(username.equals(""));

        //TODO: Come generare messaggio di invio username/create lobby al client tramite il ClientActionManager?
        //notifyObserver();
    }

    @Override
    public void askLobby() {
        System.out.println("[0] Create new lobby");
        System.out.println("[1] Join existing lobby");
        int selection = Integer.parseInt(scanner.nextLine());
        if(selection == 0){

            //TODO: Creazione messaggio di creazione lobby
            //notifyObserver();

            //TODO: Attesa riscontro server oppure gestione attraverso altro metodo showLobbyConfirmation()?
        }
    }

    @Override
    public void askNumberOfPlayers() {
        System.out.println("How many players do you want in Lobby?");
        System.out.println("[0] Two");
        System.out.println("[1] Three");
        System.out.println("[2] Four");
        int num = Integer.parseInt(scanner.nextLine());
        //TODO messaggio con numero di giocatori nella lobby o aggiungere ad askLobby?
    }

    @Override
    public void askCardSelection() {

    }

    @Override
    public void askCardInsertion() {

    }

    @Override
    public void showResponse(String content, boolean response) {

    }

    @Override
    public void showBoardRefill(String content) {

    }
}
