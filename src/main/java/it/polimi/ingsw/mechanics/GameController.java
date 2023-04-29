
package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.network.messages.InsertionMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SelectionMessage;

import java.util.ArrayList;

public class GameController {

    //region ATTRIBUTES
    private final Game game;
    private final ArrayList<String> playerUsernames;
    private String currentPlayer;
    private boolean endGame;

    private ClientHandler clientHandler;

    //endregion

    //region CONSTRUCTOR
    public GameController(ArrayList<String> playerUsernames, Game game, ClientHandler clientHandler){
        this.playerUsernames = playerUsernames;
        currentPlayer = playerUsernames.get(0); //TODO: the couch
        this.game = game;
        endGame = false;

    }
    //endregion


    //region METHODS

    /**
     * switch case method called by ClientHandler called by Lobby
     * @param message received message
     */
    //TODO:
    public void receiveMessage(Message message){
        switch (message.getType()){

            //TODO: Logica di gioco conseguentemente gestita esternamente alla gestione turni
            case SELECTION_MESSAGE -> {
                cardSelection((SelectionMessage) message);

            }
            case INSERTION_MESSAGE -> {
                cardInsertion((InsertionMessage) message);
            }
        };
    }

    //method that extracts the coordinates from the XML command, checks the validity of the selection and turns the
    //coordinates into their corresponding Cards
    public void cardSelection(SelectionMessage message){
        if(game.isSelectable(message.getCoordinates())) {
            game.removeCardFromBoard(message.getCoordinates()); //Removal of the selected cards form the game board
        }

        //TODO: notifica gli altri player (chiamata metodi VirtualView) (Observer) oppure in Game (meglio in Game)

        //TODO: invio eccezione
    }

    //method that extracts the chosen column from the XML and inserts the cards previously selected into the player's bookshelf
    public void cardInsertion(InsertionMessage message){
        //Insertion of the cards removed from the board into the player's bookshelf
        try {
            game.addCardToBookshelf(currentPlayer, message.getSelectedColumn(), message.getSelectedCards());
        } catch (AddCardException e) {
            throw new RuntimeException(e);
        }
    }

    //method that creates the final scoreboard
    public void findWinner(){
        game.scoreCommonGoal();
        game.scorePrivateGoal();

        //TODO: creation of the scoreboard based on the calculated scores for each one of the players
        //TODO: calling of Game method that creates ordered ArrayList of Players
    }

    public ClientHandler getClientHandler(){return clientHandler;}
    public String getCurrentPlayer(){return currentPlayer;}

    //endregion
}
