package wad.knockknock.server;

public interface KnockKnockServerListener {
    void serverMessageSent(String message);
    void clientMessageReceived(String message);
}
