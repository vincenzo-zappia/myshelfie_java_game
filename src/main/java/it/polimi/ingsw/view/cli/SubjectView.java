package it.polimi.ingsw.view.cli;

/**
 * Interface implemented by both CLI and GUI with the methods necessary for the creation of the same
 * Message objects that the Client has to send to the Server. It is the actual "client interface".
 */
public interface SubjectView {

    /**
     * The client interface asks the player his username
     */
    public void askUsername();

    /**
     * The client interface asks the player if he wants to create a new lobby and, if he doesn't,
     * the ID of the lobby he wants to join
     */
    public void askLobby();

    //TODO: Decidere se separare o meno da askLobby() la scelta del numero dei giocatori
    /**
     * The client interface asks the creator of the lobby the number of players that have to join
     * before starting the game
     */
    public void askNumberOfPlayers();

    //TODO: BIG JIM
    /**
     * The client interface asks the player to select the cards
     */
    public void askCardSelection();

    //TODO: BIG JIM
    /**
     * The client interface asks the player to choose the order of the cards to insert into his
     * bookshelf and the column where to insert them
     */
    public void askCardInsertion();

    //TODO: Facciamo in modo che il server invii messaggi di inizio turno per chiedere al client di fare la mossa?

    /**
     * Shows the generic feedback of the server to the client (it can be either the feedback from a
     * selection move or an insertion one)
     * @param content general info of Message abstract class
     * @param response if positive feedback
     */
    public void showResponse(String content, boolean response);

    //TODO: Con che tipo di struttura dati viene fatta vedere l'informazione rilevante di Board alla CLI/GUI?
    public void showBoardRefill(String content);
}
