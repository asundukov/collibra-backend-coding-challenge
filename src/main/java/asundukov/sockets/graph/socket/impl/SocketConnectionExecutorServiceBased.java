package asundukov.sockets.graph.socket.impl;

import asundukov.sockets.graph.engine.Engine;
import asundukov.sockets.graph.engine.SessionHandler;
import asundukov.sockets.graph.engine.impl.MessageSenderImpl;
import asundukov.sockets.graph.socket.SocketConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class SocketConnectionExecutorServiceBased implements SocketConnection {
    private static final Logger log = LoggerFactory.getLogger(SocketConnectionExecutorServiceBased.class);
    private final Socket clientSocket;
    private final ExecutorService executorService;
    private PrintWriter out;
    private BufferedReader in;
    private SessionHandler sessionHandler;


    public SocketConnectionExecutorServiceBased(Socket clientSocket, ExecutorService executorService) {
        this.clientSocket = clientSocket;
        this.executorService = executorService;
    }

    @Override
    public void start(Engine engine) throws IOException {
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        sessionHandler = engine.createSession(new MessageSenderImpl(this, this.out));
        sessionHandler.start();
        log.info("Session handler started {}", sessionHandler.getSessionId());

        executorService.submit(() -> {
            readNextMessage(sessionHandler);
        });
    }

    private void readNextMessage(SessionHandler sessionHandler) {
        try {
            String received = in.readLine();
            if (received != null) {
                log.info("{}: {}", sessionHandler.getSessionId(), received);
                sessionHandler.fromClient(received);
            }
            executorService.submit(() -> readNextMessage(sessionHandler));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error: {}", e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception ignored) {
        }
    }
}

