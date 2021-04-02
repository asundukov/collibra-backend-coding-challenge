package asundukov.sockets.graph.engine;

public interface TimeoutDetector {
    void renew(SessionHandler sessionHandler);

    void cancel(SessionHandler sessionHandler);
}
