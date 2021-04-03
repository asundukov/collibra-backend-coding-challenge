package asundukov.sockets.graph.engine.impl.command;

import asundukov.sockets.graph.engine.CommandHandler;
import asundukov.sockets.graph.engine.SessionHandler;

abstract class SessionCommandHandler implements CommandHandler {
    protected final SessionHandler sessionHandler;

    protected SessionCommandHandler(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }
}
