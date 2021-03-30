package asundukov.collibra.engine;

public interface Engine {
    SessionHandler createSession(MessageSender messageSender);
}
