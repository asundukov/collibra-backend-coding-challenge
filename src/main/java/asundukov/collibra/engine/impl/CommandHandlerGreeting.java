package asundukov.collibra.engine.impl;

import asundukov.collibra.engine.CommandHandler;
import asundukov.collibra.engine.SessionHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

public class CommandHandlerGreeting extends SessionCommandHandler {
    private static final String HELLO_PATTERN = "HI, I AM %s";
    private static final Pattern HI_PATTERN = compile("^HI, I AM ([\\w\\d-]+)$");

    private final CommandHandlerGraphFactory commandHandlerGraphFactory;

    public CommandHandlerGreeting(SessionHandler sessionHandler, CommandHandlerGraphFactory commandHandlerGraphFactory) {
        super(sessionHandler);
        sessionHandler.toClient(getHelloMessage(sessionHandler.getSessionId()));
        this.commandHandlerGraphFactory = commandHandlerGraphFactory;
    }

    @Override
    public CommandHandler handle(String message) {
        if (message == null) {
            return this;
        }
        Matcher matcher = HI_PATTERN.matcher(message);
        if (matcher.matches()) {
            sessionHandler.setClientName(matcher.group(1));
            sessionHandler.toClient("HI " + sessionHandler.getClientName());
            return commandHandlerGraphFactory.create(sessionHandler);
        } else {
            sessionHandler.toClient(DONT_KNOW_ANSWER);
        }
        return this;
    }

    private String getHelloMessage(String sessionId) {
        return format(HELLO_PATTERN, sessionId);
    }

}
