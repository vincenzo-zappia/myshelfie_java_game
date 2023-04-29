package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.entities.Board;

public class BoardRefillMessage extends Message{
    private Board board;
    public BoardRefillMessage(String sender, MessageType type, Board board) {
        super(sender, type);
        this.board = board;
    }
}
