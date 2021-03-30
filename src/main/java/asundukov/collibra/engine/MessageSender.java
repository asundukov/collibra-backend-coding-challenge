package asundukov.collibra.engine;

public interface MessageSender {
    void send(String message);
    void close();
}
