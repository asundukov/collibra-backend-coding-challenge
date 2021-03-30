package asundukov.collibra.integration;

import asundukov.collibra.engine.impl.TimeoutDetectorImpl;
import asundukov.collibra.socket.impl.ServerImpl;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class ServerStarter {
    private static ServerImpl server;
    private static int port = 0;
    private static long timeout = 0;

    public static int startServer(long timeout) throws InterruptedException {
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
            } catch (Exception e) {
                return;
            }
        });
        thread.setDaemon(false);
        thread.start();

        sleep(300);

        return port;
    }

    public static void stopServer() throws IOException {
        server.close();
    }
}
