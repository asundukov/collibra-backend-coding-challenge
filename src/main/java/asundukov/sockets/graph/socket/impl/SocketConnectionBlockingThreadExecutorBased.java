package asundukov.sockets.graph.socket.impl;

import asundukov.sockets.graph.engine.Engine;
import asundukov.sockets.graph.socket.SocketConnection;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketConnectionBlockingThreadExecutorBased implements SocketConnection {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private final SocketConnection socketConnection;

    public SocketConnectionBlockingThreadExecutorBased(SocketConnection socketConnection) {
        this.socketConnection = socketConnection;
    }

    @Override
    public void start(Engine engine) {
        executorService.submit(() -> {
            socketConnection.start(engine);
        });
    }

    @Override
    public void close() {
        try {
            socketConnection.close();
        } catch (Exception ignored) {
        }
    }
}

