package asundukov.collibra.engine.impl;

import asundukov.collibra.engine.CommandHandler;
import asundukov.collibra.engine.SessionHandler;

public class CommandHandlerBye extends SessionCommandHandler {
    protected CommandHandlerBye(SessionHandler sessionHandler) {
        super(sessionHandler);

        String name = sessionHandler.getClientName();
        long totalMs = System.currentTimeMillis() - sessionHandler.getSessionStartTime();
        String message = "BYE " + name + ", WE SPOKE FOR " + totalMs + " MS";

        sessionHandler.toClient(message);
    }

    public static void sendBye(SessionHandler sessionHandler) {
        new CommandHandlerBye(sessionHandler);
    }

    @Override
    public CommandHandler handle(String message) {
        return this;
    }
}
