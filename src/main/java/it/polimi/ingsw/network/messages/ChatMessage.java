package it.polimi.ingsw.network.messages;

public class ChatMessage extends Message{
    public ChatMessage(String sender, String chat) {
        super(sender, MessageType.CHAT);
        setContent(chat);
    }
}
