package asundukov.sockets.graph;

import asundukov.sockets.graph.socket.Server;
import asundukov.sockets.graph.socket.impl.ServerImpl;

public class Application {

    public static void main(String[] args) throws Exception {
        Server server = new ServerImpl(50000, 30000);
        server.start();
    }
}
