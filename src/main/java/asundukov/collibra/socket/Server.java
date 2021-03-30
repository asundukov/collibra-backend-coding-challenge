package asundukov.collibra.socket;

public interface Server extends AutoCloseable {
    void start() throws Exception;
}
