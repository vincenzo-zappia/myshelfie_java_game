package it.polimi.ingsw.network.messages;

public enum MessageType {

    //LOBBY
    //client2server
    JOIN_LOBBY,
    CREATE_LOBBY,

    //server2client
    LOBBY_ACCESS_RESPONSE,
    LOBBY_CREATION_RESPONSE,

    //GAME LOGIC
    //client2server
    START_GAME,
    SELECTION_MESSAGE,
    INSERTION_MESSAGE,

    //server2client
    GAME_START,

}
