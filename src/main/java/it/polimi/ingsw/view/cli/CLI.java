package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.entities.SerializableTreeMap;
import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientController;
import it.polimi.ingsw.entities.util.BoardTile;
import it.polimi.ingsw.entities.util.Tile;
import it.polimi.ingsw.view.UserInterface;
import it.polimi.ingsw.view.VirtualModel;

import java.util.*;

public class CLI implements Runnable, UserInterface {

    //region ATTRIBUTES
    private Scanner scanner;
    private final ClientController controller;
    private final VirtualModel virtualModel;
    private final Object lock;
    private final Object lock1;


    //region FLAGS
    private boolean usernameAccepted;
    private boolean lobbyJoined;
    //endregion
    //endregion

    public CLI(Client client) {
        scanner = new Scanner(System.in);
        controller = new ClientController(this, client);
        virtualModel = new VirtualModel();
        lock = new Object();
        lock1 = new Object();

        usernameAccepted = false;
        lobbyJoined = false;
    }

    @Override
    public void run() {
        //TODO: CliUtil stampa titolo ascii art
        connectionHandler();
        gameHandler();
    }

    /**
     * Manages the choosing of a username and either the creation of a new lobby or the joining of an existing one
     */
    private void connectionHandler() {

        //Asking the player for his username and sending it to server to check for its availability
        while(!usernameAccepted) {
            String username = requestUsername();
            controller.checkUsername(username);

            //Thread waits until notified
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //Asking the player whether to join or create a new lobby and waiting for server feedback
        while (!lobbyJoined) {
            String selection = requestLobby();
            switch (selection) {

                //Creation of a new lobby
                case "0" -> {
                    //Sending a lobby creation request
                    controller.createLobby();

                    //Thread waits until notified
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    //Waiting for the lobby master to type "start"
                    System.out.println("Type *start* when you want to start the game!");
                    String read;
                    do {
                        read = scanner.nextLine();
                    }
                    while (!read.equals("start"));

                    //Starting the game
                    controller.startGame();

                    //Thread waits until notified
                    synchronized (lock1) {
                        try {
                            lock1.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }

                //Joining an existing lobby
                case "1" -> {
                    System.out.println("Enter lobby ID:");
                    try {
                        int id = Integer.parseInt(scanner.nextLine());
                        controller.joinLobby(id);
                    } catch (NumberFormatException e) {
                        System.out.println(CliUtil.makeErrorMessage("Incorrect command syntax!"));
                        connectionHandler();
                    }

                    //Thread waits until notified
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    /**
     * Manages the sending of player commands to the game
     */
    private void gameHandler() {
        scanner = new Scanner(System.in);

        //TODO: Implementare stop del loop attraverso chiamata di scoreboard
        //While loop to read the user keyboard input (until the game ends)
        while(!virtualModel.getEndGame()){
            String read = scanner.nextLine();

            if (!read.contains(" ")){
                System.out.println(CliUtil.makeErrorMessage("Incorrect command syntax!"));
                continue;
            }

            String[] splitted = read.split(" ", 2);

            switch (splitted[0]){

                //Card selection command eg: "select (x;y),(x;y),(x,y)"
                case "select" -> {

                    //Parsing of the input command
                    String[] strCoordinates = splitted[1].split(",");
                    int[][] coordinates = new int[strCoordinates.length][2];

                    //Checking command syntax
                    boolean validFormat = false;
                    for(int i=0; i < strCoordinates.length; i++) {
                        if(!checkFormat(strCoordinates[i].trim())){
                            System.out.println(CliUtil.makeErrorMessage("Incorrect command syntax!"));
                            validFormat = false;
                            break;
                        }
                        else coordinates[i] = parseCoordinates(strCoordinates[i].trim());
                        validFormat = true;
                    }

                    //Sending the selection to the server only if the syntax is correct
                    if(validFormat) controller.sendSelection(coordinates);
                }

                //Card insertion command (bookshelf column n) eg: "insert n"
                case "insert" -> {
                    int column;

                    try {
                        //Parsing the input command (chosen column)
                        column = Integer.parseInt(splitted[1]);

                        //Sending the insertion to the server only if the player has already selected his cards
                        controller.sendInsertion(column);
                    }
                    catch (NumberFormatException e){
                        System.out.println(CliUtil.makeErrorMessage("Incorrect command syntax!"));
                    }
                }

                //Show command to prompt the printing of game entities or details
                case "show" -> {
                    switch (splitted[1]){
                        case "board" -> showBoard();
                        case "bookshelf" -> showBookshelf();
                        case "commongoals" -> showCommonGoals();
                        case "privategoal" -> showPrivateGoal();

                        //default -> System.out.println(CliUtil.makeErrorMessage("Incorrect command syntax.\nType help for a list of commands."));
                    }
                }

                //Help command for syntax aid
                case "help" -> {
                    System.out.println("Command list:");
                    System.out.println(CliUtil.makeCommandList());
                }

                default -> System.out.println(CliUtil.makeErrorMessage("Incorrect command syntax.\nType help for a list of commands."));
            }
        }
        //System.out.println(CliUtil.makeTitle("Game Over!"));
    }

    //region PRIVATE METHODS
    /**
     * Asks the player his username
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
     * Asks the player if he wants to create a new lobby or join an existing one
     */
    private String requestLobby() {

        //Taking the input and checking its syntax
        String selection;
        do {
            System.out.println("[0] Create new lobby\n[1] Join existing lobby");
            selection = scanner.nextLine();
            if(!selection.equals("0") && !selection.equals("1")) System.out.println(CliUtil.makeErrorMessage("Enter valid number."));
        }while (!selection.equals("0") && !selection.equals("1"));



        return selection;
    }

    /**
     * Prints the bookshelf of the player on screen
     */
    private void showBookshelf() {
        System.out.println(CliUtil.makeTitle("Bookshelf"));
        System.out.println(CliUtil.makeBookshelf(CliUtil.bookshelfConverter(virtualModel.getBookshelf())));
        System.out.println(CliUtil.makeLegend());
    }

    /**
     * Prints the board on screen
     */
    private void showBoard() {
        System.out.println(CliUtil.makeTitle("Livingroom"));
        System.out.println(CliUtil.makeBoard(CliUtil.boardConverter(virtualModel.getBoard())));
        System.out.println(CliUtil.makeLegend());
    }

    /**
     * Prints the details of the two game common goals on screen
     */
    private void showCommonGoals(){
        Goal[] goals = virtualModel.getCommonGoals();


    }

    /**
     * Prints the details of the private goal of the player on screen
     */
    private void showPrivateGoal() {
        PrivateGoal privateGoal = virtualModel.getPrivateGoal();
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
     * Checks if a String is of the format: (x;y), where x and y are two int
     * @param str The String to check
     * @return true if and only if the String is in the correct format.
     */
    private boolean checkFormat(String str){ return str.matches("\\(\\d+;\\d+\\)"); }
    //endregion

    //region USER INTERFACE
    @Override
    public void confirmUsername(boolean response) {
        //Receiving feedback about username availability
        if (response) usernameAccepted = true;

        //Notifying the waiting thread
        synchronized (lock) {
            lock.notify();
        }
    }

    @Override
    public void confirmAccess(boolean response) {
        //Receiving feedback about lobby creation/join
        lobbyJoined = response;

        //Notifying the waiting thread
        synchronized (lock) {
            lock.notify();
        }
    }

    @Override
    public void refreshConnectedPlayers(ArrayList<String> playerUsernames) {
        System.out.println("Connected players: ");
        System.out.println(CliUtil.makePlayersList(playerUsernames));
    }

    @Override
    public void confirmStartGame(boolean response) {
        //Notifying the waiting thread
        synchronized (lock1) {
            lock1.notify();
        }
    }
    //endregion

    //region VIEW
    @Override
    public void sendGenericResponse(boolean response, String content) {
        if(response) System.out.println(CliUtil.makeConfirmationMessage(content));
        else System.out.println(CliUtil.makeErrorMessage(content));
    }

    @Override
    public void showCurrentPlayer(String currentPlayer){
        //TODO: Formattare carino CliUtil
        System.out.println("Current player: " + currentPlayer);
    }

    @Override
    public void sendCheckedCoordinates(int[][] coordinates){
        virtualModel.setCoordinates(coordinates);
    }

    @Override
    public void showRemovedCards(int[][] coordinates) {
        virtualModel.refreshBoard(coordinates);
        showBoard();
    }

    @Override
    public void showUpdatedBookshelf(Tile[][] bookshelf) {
        virtualModel.setBookshelf(bookshelf);
        showBookshelf();
    }

    @Override
    public void showRefilledBoard(BoardTile[][] boardTiles) {
        virtualModel.setBoard(boardTiles);
        showBoard();
    }

    @Override
    public void showCommonGoals(Goal[] commonGoals) {
        virtualModel.setCommonGoals(commonGoals);
    }

    @Override
    public void showPrivateGoal(PrivateGoal privateGoal) {
        virtualModel.setPrivateGoal(privateGoal);
    }

    @Override
    public void showScoreboard(SerializableTreeMap<String, Integer> scoreboard) {
        System.out.println(CliUtil.makeTitle("Scoreboard"));
        for (Map.Entry<String, Integer> entry : scoreboard.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        virtualModel.setEndGame();
    }

    @Override
    public void showToken(String content) {
        CliUtil.makeConfirmationMessage(content);
    }

    //endregion

}
