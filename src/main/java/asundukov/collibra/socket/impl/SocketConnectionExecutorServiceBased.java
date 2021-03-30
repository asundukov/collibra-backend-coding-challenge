package asundukov.collibra.socket.impl;

import asundukov.collibra.engine.Engine;
import asundukov.collibra.engine.MessageSender;
import asundukov.collibra.engine.SessionHandler;
import asundukov.collibra.engine.impl.MessageSenderImpl;
import asundukov.collibra.socket.SocketConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class SocketConnectionExecutorServiceBased implements SocketConnection {
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
        System.out.println("Session handler started " + sessionHandler.getSessionId());

        executorService.submit(() -> {
            readNextMessage(sessionHandler);
        });
    }

    private void readNextMessage(SessionHandler sessionHandler) {
        try {
            String received = in.readLine();
            if (received != null) {
                System.out.println(sessionHandler.getSessionId() + ": " + received);
                sessionHandler.fromClient(received);
            }
            executorService.submit(() -> readNextMessage(sessionHandler));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
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

