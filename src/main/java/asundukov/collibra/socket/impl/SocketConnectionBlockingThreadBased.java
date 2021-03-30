package asundukov.collibra.socket.impl;

import asundukov.collibra.engine.Engine;
import asundukov.collibra.engine.SessionHandler;
import asundukov.collibra.engine.impl.MessageSenderImpl;
import asundukov.collibra.socket.SocketConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketConnectionBlockingThreadBased implements SocketConnection {
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;


    public SocketConnectionBlockingThreadBased(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void start(Engine engine) throws IOException {

        Thread thread = new Thread(() -> {
            try {
                this.out = new PrintWriter(clientSocket.getOutputStream(), true);
                this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                System.out.println("Cannot open streams: " + e.getMessage());
                e.printStackTrace();
                close();
                return;
            }

            SocketConnectionBlockingThreadBased socketConnection = this;

            SessionHandler sessionHandler = engine.createSession(new MessageSenderImpl(this, this.out));
            sessionHandler.start();
            System.out.println(sessionHandler.getSessionId() + ": <started>");

            String received;
            try {
                while ((received = in.readLine()) != null) {
                    sessionHandler.fromClient(received);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                if (!e.getMessage().equals("Socket closed")) {
                    e.printStackTrace();
                }
            } finally {
                sessionHandler.closeSession();
                close();
                System.out.println(sessionHandler.getSessionId() + ": <closed>");
            }
        });

        thread.start();
    }

    @Override
    public void close() {
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (Exception ignored) {
        }
    }
}

