package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.entities.Board;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientController;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.util.BoardCell;
import it.polimi.ingsw.util.Cell;
import it.polimi.ingsw.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CLI implements Runnable, View {
    private final Scanner scanner;
    private final ClientController controller;
    private BoardCell[][] board;
    private Cell[][] bookshelf;
    private int[][] selection;

    public CLI(Client client) {
        scanner = new Scanner(System.in);
        controller = new ClientController(this, client);
        bookshelf = new Cell[6][5];
        for (int i = 0; i < bookshelf.length; i++) {
            for (int j = 0; j < bookshelf[i].length; j++) {
                bookshelf[i][j] = new Cell();
            }
        }
    }

    @Override
    public void run() {

        //TODO: Stampa a schermo titolo di gioco da metodo di CliUtils

        connection();
        while(true){
            String read = scanner.nextLine();
            String[] splitted = read.split(" ");

            switch (splitted[0]){
                case "select" -> {
                    String[] coordinate = splitted[1].split(",");
                    int[][] coordinates = new int[coordinate.length][2];
                    for(int i=0; i< coordinate.length; i++){
                        coordinates[i] = parseCoordinates(coordinate[i]);
                    }
                    selection = coordinates;
                    controller.sendSelection(coordinates);
                }
                case "show" -> {
                    if(splitted[1].equals("board")) showBoard();
                    else if(splitted[1].equals("bookshelf")) showBookshelf();
                    else System.out.println("comando non corretto"); //TODO: cambiare;
                }
                case "help" -> {}
                case "insert" -> {
                    ArrayList<Card> cards = new ArrayList<Card>();
                    for(int i=0; i<selection.length; i++) cards.add(board[selection[i][0]][selection[i][0]].getCard());
                    controller.sendInsertion(cards, Integer.parseInt(splitted[1]));
                }

                case "" -> {}
            }
        }

    }

    public static int[] parseCoordinates(String input) {
        String[] parts = input.substring(1, input.length() - 1).split(";");

        int[] result = new int[2];
        result[0] = Integer.parseInt(parts[0].trim());
        result[1] = Integer.parseInt(parts[1].trim());

        return result;
    }

    public void connection(){
        int choice = requestLobby();
        String username = requestUsername();

        if(choice == 0){
            controller.createLobby(username);
            String read;
            do{
                read = scanner.nextLine();
            }while(!read.equals("start"));
            controller.startGame();
        }
        else if(choice == 1){
            System.out.println("Enter lobby id:");
            int id = Integer.parseInt(scanner.nextLine());
            controller.joinLobby(username, id);
        }
    }

    @Override
    public void showError(String content) {
        System.out.println(CliUtil.makeErrorMessage(content));
    }

    @Override
    public void refreshConnectedPlayers(ArrayList<String> playerUsernames) {
        System.out.println("Lista dei players connessi:");
        System.out.println(CliUtil.makePlayersList(playerUsernames));
    }

    @Override
    public void showSuccessfulConnection(int lobbyId) {
        System.out.println("Connessione alla loby riuscita con successo! Lobby id: " + lobbyId);
    }

    @Override
    public void showConfirmation(MessageType type) {
        switch (type){
            case GAME_START -> System.out.println(CliUtil.makeConfirmationMessage("Now in game!"));
            case SELECTION_RESPONSE -> {
                String sel = Arrays.toString(selection);
                System.out.println(CliUtil.makeConfirmationMessage("Selezione valida!"));
            }
            case INSERTION_RESPONSE -> {
                String ins = Arrays.toString(selection);
                System.out.println(CliUtil.makeConfirmationMessage("Inserimento avvenuto con successo!"));
            }
        }
    }

    @Override
    public void showRemovedCards(int[][] coordinates) {

    }

    @Override
    public void showRefilledBoard(BoardCell[][] boardCells) {
        this.board = boardCells;
        showBoard();
    }

    @Override
    public void sendResponse(boolean response, MessageType responseType) {

    }

    @Override
    public void sendNotYourTurn() {

    }

    //region PRIVATE METHODS
    /**
     * The client interface asks the player his username
     */
    private String requestUsername() {
        String username;
        do{
            System.out.println("Enter username:");
            username = scanner.nextLine();
            if (username.replace(" ", "").equals("")) System.out.println(CliUtil.makeErrorMessage("Enter valid username:"));
        }while(username.replace(" ", "").equals(""));

        return username;
    }

    /**
     * The client interface asks the player if he wants to create a new lobby and, if he doesn't,
     * the ID of the lobby he wants to join
     */
    public int requestLobby() {
        int selection;
        do {
            System.out.println("[0] Create new lobby");
            System.out.println("[1] Join existing lobby");
            selection = Integer.parseInt(scanner.nextLine());
            if(selection != 0 && selection != 1) System.out.println(CliUtil.makeErrorMessage("Enter valid number."));
        }while (selection != 0 && selection != 1);
        return selection;
    }

    private void showBookshelf() {
        System.out.println(CliUtil.makeTitle("Bookshelf"));
        System.out.println(CliUtil.makeBookshelf(CliUtil.bookshelfConverter(bookshelf)));
        System.out.println(CliUtil.makeLegend());
    }

    private void showBoard() {
        System.out.println(CliUtil.makeTitle("Livingroom"));
        System.out.println(CliUtil.makeBoard(CliUtil.boardConverter(board)));
        System.out.println(CliUtil.makeLegend());
    }

    //endregion

}
