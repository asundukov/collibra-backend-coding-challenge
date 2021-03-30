package asundukov.collibra.engine.impl;

import asundukov.collibra.engine.CommandHandler;
import asundukov.collibra.engine.SessionHandler;

abstract class SessionCommandHandler implements CommandHandler {
    protected final SessionHandler sessionHandler;

    protected SessionCommandHandler(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }
}
