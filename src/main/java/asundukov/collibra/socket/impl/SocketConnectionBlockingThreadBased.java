package asundukov.collibra.socket.impl;

import asundukov.collibra.engine.Engine;
import asundukov.collibra.engine.SessionHandler;
import asundukov.collibra.engine.impl.MessageSenderImpl;
import asundukov.collibra.socket.SocketConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketConnectionBlockingThreadBased implements SocketConnection {
    private static final Logger log = LoggerFactory.getLogger(SocketConnectionBlockingThreadBased.class);

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
                log.error("Cannot open streams: {}", e.getMessage());
                e.printStackTrace();
                close();
                return;
            }

            SessionHandler sessionHandler = engine.createSession(new MessageSenderImpl(this, this.out));
            sessionHandler.start();
            log.info("{}: <started>", sessionHandler.getSessionId());

            String received;
            try {
                while ((received = in.readLine()) != null) {
                    sessionHandler.fromClient(received);
                }
            } catch (IOException e) {
                log.info(e.getMessage());
                if (!e.getMessage().equals("Socket closed")) {
                    e.printStackTrace();
                }
            } finally {
                sessionHandler.closeSession();
                close();
                log.info("{}: <closed>", sessionHandler.getSessionId());
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

