package asundukov.collibra.engine.impl;

import asundukov.collibra.engine.CommandHandler;
import asundukov.collibra.engine.SessionHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

public class CommandHandlerGreeting extends SessionCommandHandler {
    private static final String HELLO_PATTERN = "HI, I AM %s";
    private static final Pattern HI_PATTERN = compile("^HI, I AM ([a-zA-Z0-9-]+)$");

    public CommandHandlerGreeting(SessionHandler sessionHandler) {
        super(sessionHandler);
        sessionHandler.toClient(getHelloMessage(sessionHandler.getSessionId()));
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
            return new CommandHandlerGraph(sessionHandler);
        } else {
            sessionHandler.toClient(DONT_KNOW_ANSWER);
        }
        return this;
    }

    private String getHelloMessage(String sessionId) {
        return format(HELLO_PATTERN, sessionId);
    }

}
