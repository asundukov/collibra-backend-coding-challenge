package asundukov.collibra.engine.impl;

import asundukov.collibra.engine.CommandHandler;
import asundukov.collibra.engine.SessionHandler;

public class CommandHandlerGraph extends SessionCommandHandler {
    private static final String BYE_REQUEST = "BYE MATE!";

    protected CommandHandlerGraph(SessionHandler sessionHandler) {
        super(sessionHandler);
    }

    @Override
    public CommandHandler handle(String message) {
        if (BYE_REQUEST.equals(message)) {
            return new CommandHandlerBye(sessionHandler);
        } else {
            sessionHandler.toClient(DONT_KNOW_ANSWER);
        }
        return this;
    }
}
