package asundukov.collibra.socket;

import asundukov.collibra.engine.Engine;

import java.io.IOException;

public interface SocketConnection extends AutoCloseable {
    void start(Engine engine) throws IOException;
}
