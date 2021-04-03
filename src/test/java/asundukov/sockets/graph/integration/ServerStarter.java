package asundukov.sockets.graph.integration;

import asundukov.sockets.graph.socket.Server;
import asundukov.sockets.graph.socket.impl.ServerImpl;

public class ServerStarter {
    private static Server server;
    private static int port = 0;

    public static int startServer(long timeout) {
        for (int i = 50000; i < 51000; i++) {
            try {
                server = new ServerImpl(i, timeout);
                port = i;
                break;
            } catch (Exception ignored) {
            }
        }
        if (port == 0) {
            throw new RuntimeException("No available ports found");
        }
        Thread thread = new Thread(() -> {
            try {
                server.start();
            } catch (Exception ignored) {
            }
        });
        thread.start();

        return port;
    }

    public static void stopServer() throws Exception {
        server.close();
    }
}
