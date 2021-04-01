package asundukov.collibra.socket.impl;

import asundukov.collibra.engine.Engine;
import asundukov.collibra.engine.impl.EngineDefault;
import asundukov.collibra.engine.impl.IdGeneratorUUID4;
import asundukov.collibra.engine.impl.TimeoutDetectorImpl;
import asundukov.collibra.socket.Server;
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
            SocketConnectionBlockingThreadBased socketConnection = new SocketConnectionBlockingThreadBased(clientSocket);
            socketConnection.start(engine);
            log.info("Socket connection handler started {}", clientSocket.getPort());
       }
       close();
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
