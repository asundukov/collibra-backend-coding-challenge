package asundukov.sockets.graph.engine.impl;

import asundukov.sockets.graph.engine.Engine;
import asundukov.sockets.graph.engine.IdGenerator;
import asundukov.sockets.graph.engine.MessageSender;
import asundukov.sockets.graph.engine.SessionHandler;
import asundukov.sockets.graph.engine.TimeoutDetector;

public class EngineDefault implements Engine {

    private final IdGenerator idGenerator;
    private final TimeoutDetector timeoutDetector;
    private final CommandHandlerGraphFactory commandHandlerGraphFactory = new CommandHandlerGraphFactory();

    public EngineDefault(IdGenerator idGenerator, TimeoutDetector timeoutDetector) {
        this.idGenerator = idGenerator;
        this.timeoutDetector = timeoutDetector;
    }

    @Override
    public SessionHandler createSession(MessageSender messageSender) {
        String sessionId = idGenerator.getNewId();
        return new SessionHandlerImpl(sessionId, messageSender, timeoutDetector, commandHandlerGraphFactory);
    }

}
