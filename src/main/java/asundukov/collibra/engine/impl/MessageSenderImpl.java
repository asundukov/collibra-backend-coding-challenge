package asundukov.collibra.engine.impl;

import asundukov.collibra.engine.MessageSender;
import asundukov.collibra.socket.SocketConnection;

import java.io.PrintWriter;

public class MessageSenderImpl implements MessageSender {
    private final SocketConnection socketConnection;
    private final PrintWriter out;

    public MessageSenderImpl(SocketConnection socketConnection, PrintWriter out) {
        this.socketConnection = socketConnection;
        this.out = out;
    }

    @Override
    public void send(String message) {
        out.println(message);
    }

    @Override
    public void close() {
        try {
            socketConnection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
