package asundukov.collibra;

import asundukov.collibra.socket.impl.ServerImpl;

public class Application {

    public static void main(String[] args) throws Exception {
        ServerImpl server = new ServerImpl(50000, 30000);
        server.start();
    }
}
