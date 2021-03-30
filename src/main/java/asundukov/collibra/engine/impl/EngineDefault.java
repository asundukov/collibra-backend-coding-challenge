package asundukov.collibra.engine.impl;

import asundukov.collibra.engine.CommandHandler;
import asundukov.collibra.engine.Engine;
import asundukov.collibra.engine.IdGenerator;
import asundukov.collibra.engine.MessageSender;
import asundukov.collibra.engine.SessionHandler;
import asundukov.collibra.engine.TimeoutDetector;

public class EngineDefault implements Engine {

    private final IdGenerator idGenerator;
    private final TimeoutDetector timeoutDetector;

    public EngineDefault(IdGenerator idGenerator, TimeoutDetector timeoutDetector) {
        this.idGenerator = idGenerator;
        this.timeoutDetector = timeoutDetector;
    }

    @Override
    public SessionHandler createSession(MessageSender messageSender) {
        String sessionId = idGenerator.getNewId();
        return new SessionHandlerImpl(sessionId, messageSender, timeoutDetector);
    }

}
