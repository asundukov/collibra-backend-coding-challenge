package asundukov.sockets.graph.engine;

public interface MessageSender {
    void send(String message);
    void close();
}
