package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.entities.Board;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientController;
import it.polimi.ingsw.network.messages.CreateLobby;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.observer.Subject;
import it.polimi.ingsw.observer.SubjectView;

import java.util.Scanner;

public class CLI extends SubjectView implements Runnable, View {

    private final Client client; //TODO: Perché la CLI ha un'istanza di client?
    private final Scanner scanner;

    public CLI() {
        client = new Client("10.0.0.3", 2023);
        scanner = new Scanner(System.in);
        new Thread(client).start(); //Loop execution of receiveMessage() by Client
        this.register(new ClientController(this));
    }

    @Override
    public void run() {


        //TODO: Come fa la CLI a tenere traccia dei cambiamenti che avvengono lato Client con l'invio di comandi in base ai messaggi ricevuti?

        //TODO: Stampa a schermo titolo di gioco da metodo di CliUtils

        requestUsername();
        requestLobby();
        requestNumberOfPlayers();



        /*
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
    public void requestUsername() {
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
    public void requestLobby() {
        int selection;
        do {
            System.out.println("[0] Create new lobby");
            System.out.println("[1] Join existing lobby");
            selection = Integer.parseInt(scanner.nextLine());
            if(selection != 0 && selection != 1) System.out.println("Enter valid number.");
        }while (selection != 0 && selection != 1);
        if(selection == 0){

            //TODO: Creazione messaggio di creazione lobby
            notifyObserver(new CreateLobby());

            //TODO: Attesa riscontro server oppure gestione attraverso altro metodo showLobbyConfirmation()?
        }
    }

    @Override
    public void requestNumberOfPlayers() {
        int num;
        do {
            System.out.println("How many players do you want in Lobby?");
            System.out.println("[0] Two");
            System.out.println("[1] Three");
            System.out.println("[2] Four");
            num = Integer.parseInt(scanner.nextLine());
            if (num != 0 && num != 1 && num != 2) System.out.println("Enter valid number.");
        }while (num != 0 && num != 1 && num != 2);
        //TODO messaggio con numero di giocatori nella lobby o aggiungere ad askLobby?
    }

    @Override
    public void requestCardSelection() {

    }

    @Override
    public void requestCardInsertion() {

    }

    @Override
    public void showRemovedCards(int[][] coordinates) {

    }

    @Override
    public void showRefilledBoard(Board board) {
        //TODO: CliUtil.makeBoard() con che parametro?
    }

    @Override
    public void sendResponse(boolean response, MessageType responseType) {

    }

    @Override
    public void sendNotYourTurn() {

    }


}
