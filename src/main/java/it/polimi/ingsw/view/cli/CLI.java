package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientController;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.util.BoardCell;
import it.polimi.ingsw.util.Cell;
import it.polimi.ingsw.view.UserInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class CLI implements Runnable, UserInterface {

    //region ATTRIBUTES
    private final Scanner scanner;
    private final ClientController controller;
    private BoardCell[][] board;
    private Cell[][] bookshelf;
    private int[][] selection;
    //endregion

    public CLI(Client client) {
        scanner = new Scanner(System.in);
        controller = new ClientController(this, client);
        bookshelf = new Cell[6][5];
        for(int i=0; i<6;i++) for(int j=0; j<5; j++) bookshelf[i][j] = new Cell();
    }

    @Override
    public void run() {
        //TODO: Stampa a schermo titolo di gioco da metodo di CliUtils

        connection(); //Creation or joining of a lobby and starting the game (initialization of all the data structures)

        //While loop to read the user keyboard input (until the game ends)
        while(true){
            String read = scanner.nextLine();
            String[] splitted = read.split(" ");

            switch (splitted[0]){

                //Card selection command eg: "select (x;y),(x;y),(x,y)"
                case "select" -> {
                    String[] coordinate = splitted[1].split(",");
                    int[][] coordinates = new int[coordinate.length][2];
                    for(int i=0; i< coordinate.length; i++){
                        coordinates[i] = parseCoordinates(coordinate[i]);
                    }
                    selection = coordinates;
                    controller.sendSelection(coordinates);
                }

                //Card insertion command (bookshelf column n) eg: "insert n"
                case "insert" -> {
                    int column;
                    try {
                        column = Integer.parseInt(splitted[1]);
                        ArrayList<Card> cards = new ArrayList<>();
                        for (int[] ints : selection) cards.add(board[ints[0]][ints[1]].getCard());
                        controller.sendInsertion(cards, column);
                    }
                    catch (NumberFormatException e){
                        System.out.println(CliUtil.makeErrorMessage("Incorrect command syntax.\nType help for a list of commands."));
                    }
                }

                //Show command to prompt the printing of either the board or the bookshelf of the player
                case "show" -> {
                    switch (splitted[1]){
                        case "board" -> showBoard();
                        case "bookshelf" -> showBookshelf();
                        case "commongoals" -> {}
                        case "privategoal" -> {}

                        default -> System.out.println(CliUtil.makeErrorMessage("Error")); //TOTO: cambiare il messaggio
                    }
                }

                //Help command for syntax aid
                case "help" -> {
                    System.out.println("lista comandi");
                }

                default -> System.out.println(CliUtil.makeErrorMessage("Comando non corretto, scrivi help per la lista comandi")); //TODO: tradurre
            }
        }

    }

    //region PRIVATE METHODS
    /**
     * Prompts the creation of either a lobby creation command or a lobby access request based on the user input
     */
    private void connection(){
        int choice = Integer.parseInt(requestLobby());
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
            int id = Integer.parseInt(scanner.nextLine()); //TODO: mettere controllo se intero
            controller.joinLobby(username, id);
        }
    }

    /**
     * Makes card coordinates (two int array) out of the user keyboard input
     * @param input user keyboard input for coordinates eg: "(x;y)"
     * @return actual card coordinates (two int array)
     */
    private int[] parseCoordinates(String input) {
        String[] parts = input.substring(1, input.length() - 1).split(";");

        int[] result = new int[2];
        result[0] = Integer.parseInt(parts[0].trim());
        result[1] = Integer.parseInt(parts[1].trim());

        return result;
    }

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
    private String requestLobby() {
        String selection;
        do {
            System.out.println("[0] Create new lobby");
            System.out.println("[1] Join existing lobby");
            selection = scanner.nextLine();
            if(!selection.equals("0") && !selection.equals("1")) System.out.println(CliUtil.makeErrorMessage("Enter valid number."));
        }while (selection.equals("0") && selection.equals("1"));
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

    //region USER INTERFACE
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
            case START_GAME_RESPONSE -> System.out.println(CliUtil.makeConfirmationMessage("Now in game!"));
            case SELECTION_RESPONSE -> {
                String sel = Arrays.toString(selection);
                System.out.println(CliUtil.makeConfirmationMessage("Selezione valida!"));
            }
            case INSERTION_RESPONSE -> {
                System.out.println(CliUtil.makeConfirmationMessage("Inserimento avvenuto con successo!"));
            }
        }
    }

    @Override
    public void showError(String content) {
        System.out.println(CliUtil.makeErrorMessage(content));
    }

    @Override
    public void refreshBoard(int[][] coordinates) {
        for (int[] coordinate : coordinates) {
            int row = coordinate[0];
            int column = coordinate[1];
            board[row][column].setCellEmpty();
        }
        showBoard();
    }

    @Override
    public void requestCardSelection() {

    }

    @Override
    public void requestCardInsertion() {

    }
    //endregion

    //region VIEW
    @Override
    public void showRemovedCards(int[][] coordinates) {

    }

    @Override
    public void showRefilledBoard(BoardCell[][] boardCells) {
        this.board = boardCells;
        showBoard();
    }

    @Override
    public void showCurrentPlayer(String currentPlayer){
        System.out.println("Current player: " + currentPlayer);
    }

    @Override
    public void showScoreboard(HashMap<String, Integer> scoreboard) {

    }

    @Override
    public void sendResponse(boolean response, MessageType responseType) {

    }

    @Override
    public void sendInsertionResponse(Cell[][] bookshelf, boolean response) {
        this.bookshelf = bookshelf;
        showBookshelf();
    }

    @Override
    public void sendNotYourTurn() {

    }
    //endregion

}
