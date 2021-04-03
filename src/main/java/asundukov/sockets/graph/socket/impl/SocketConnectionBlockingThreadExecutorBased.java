package asundukov.sockets.graph.socket.impl;

import asundukov.sockets.graph.engine.Engine;
import asundukov.sockets.graph.socket.SocketConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketConnectionBlockingThreadExecutorBased implements SocketConnection {
    private static final Logger log = LoggerFactory.getLogger(SocketConnectionBlockingThreadExecutorBased.class);
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private final SocketConnection socketConnection;


    public SocketConnectionBlockingThreadExecutorBased(SocketConnection socketConnection) {
        this.socketConnection = socketConnection;
    }

    @Override
    public void start(Engine engine) throws IOException {
        executorService.submit(() -> {
            try {
                socketConnection.start(engine);
            } catch (IOException e) {
                close();
            }
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

