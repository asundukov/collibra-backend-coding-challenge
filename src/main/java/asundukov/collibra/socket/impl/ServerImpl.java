package asundukov.collibra.socket.impl;

import asundukov.collibra.engine.Engine;
import asundukov.collibra.engine.impl.EngineDefault;
import asundukov.collibra.engine.impl.IdGeneratorUUID4;
import asundukov.collibra.engine.impl.TimeoutDetectorImpl;
import asundukov.collibra.socket.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerImpl implements Server {

    private final int port;
    private final long timeout;
    private ServerSocket serverSocket;

    public ServerImpl(int port, long timeout) throws IOException {
        this.port = port;
        this.timeout = timeout;

        System.out.println("Start listening on port " + port);
        serverSocket = new ServerSocket(port);
        System.out.println("Started");
    }

    @Override
    public void start() throws Exception {

        Engine engine = new EngineDefault(new IdGeneratorUUID4(), new TimeoutDetectorImpl(timeout));

        while (!Thread.interrupted()) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New connection accepted on port " + clientSocket.getPort());
            try (SocketConnectionBlockingThreadBased socketConnection = new SocketConnectionBlockingThreadBased(clientSocket)) {
                socketConnection.start(engine);
                System.out.println("Socket connection handler started " + clientSocket.getPort());
            }
        }
        close();
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
