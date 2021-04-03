package asundukov.sockets.graph.engine;

public interface Engine {
    SessionHandler createSession(MessageSender messageSender);
}
