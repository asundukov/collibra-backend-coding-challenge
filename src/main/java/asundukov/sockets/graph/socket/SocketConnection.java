package asundukov.sockets.graph.socket;

import asundukov.sockets.graph.engine.Engine;

public interface SocketConnection extends AutoCloseable {
    void start(Engine engine);
}
