package asundukov.sockets.graph.socket.impl;

import asundukov.sockets.graph.engine.Engine;
import asundukov.sockets.graph.engine.impl.EngineDefault;
import asundukov.sockets.graph.engine.impl.IdGeneratorUUID4;
import asundukov.sockets.graph.engine.impl.TimeoutDetectorImpl;
import asundukov.sockets.graph.socket.Server;
import asundukov.sockets.graph.socket.SocketConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerImpl implements Server {
    private static final Logger log = LoggerFactory.getLogger(ServerImpl.class);

    private final long timeout;
    private final ServerSocket serverSocket;

    public ServerImpl(int port, long timeout) throws IOException {
        this.timeout = timeout;
        serverSocket = new ServerSocket(port);
        log.info("Start listening on port {}", port);
    }

    @Override
    public void start() throws Exception {

        Engine engine = new EngineDefault(new IdGeneratorUUID4(), new TimeoutDetectorImpl(timeout));

        while (!Thread.interrupted()) {
            Socket clientSocket = serverSocket.accept();
            log.info("New connection accepted on port {}", clientSocket.getPort());
            SocketConnection socketConnection = new SocketConnectionBlockCurrentThreadBased(clientSocket);
            SocketConnection socketConnectionThreaded = new SocketConnectionBlockingThreadExecutorBased(socketConnection);
            socketConnectionThreaded.start(engine);
            log.info("Socket connection handler started {}", clientSocket.getPort());
       }
       close();
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
