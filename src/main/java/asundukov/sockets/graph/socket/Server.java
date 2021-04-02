package asundukov.sockets.graph.socket;

public interface Server extends AutoCloseable {
    void start() throws Exception;
}
