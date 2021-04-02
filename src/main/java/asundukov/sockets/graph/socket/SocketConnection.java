package asundukov.sockets.graph.socket;

import asundukov.sockets.graph.engine.Engine;

import java.io.IOException;

public interface SocketConnection extends AutoCloseable {
    void start(Engine engine) throws IOException;
}
