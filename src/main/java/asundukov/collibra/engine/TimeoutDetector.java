package asundukov.collibra.engine;

public interface TimeoutDetector {
    void renew(SessionHandler sessionHandler);

    void cancel(SessionHandler sessionHandler);
}
