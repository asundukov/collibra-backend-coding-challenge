package asundukov.sockets.graph.engine.impl;

import asundukov.sockets.graph.engine.CommandHandler;
import asundukov.sockets.graph.engine.MessageSender;
import asundukov.sockets.graph.engine.SessionHandler;
import asundukov.sockets.graph.engine.TimeoutDetector;
import asundukov.sockets.graph.engine.impl.command.CommandHandlerGraphFactory;
import asundukov.sockets.graph.engine.impl.command.CommandHandlerGreeting;
import asundukov.sockets.graph.engine.impl.command.DefaultCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionHandlerImpl implements SessionHandler {
    private static final Logger log = LoggerFactory.getLogger(SessionHandlerImpl.class);

    private final String sessionId;
    private final MessageSender messageSender;
    private final TimeoutDetector timeoutHandler;
    private long sessionStartedTime;

    private final CommandHandlerGraphFactory commandHandlerGraphFactory;
    private CommandHandler commandHandler = new DefaultCommandHandler();
    private String clientName = "";

    public SessionHandlerImpl(String sessionId, MessageSender messageSender,
                              TimeoutDetector timeoutDetector, CommandHandlerGraphFactory commandHandlerGraphFactory) {
        this.sessionId = sessionId;
        this.messageSender = messageSender;
        this.timeoutHandler = timeoutDetector;
        this.commandHandlerGraphFactory = commandHandlerGraphFactory;
    }

    @Override
    public void start() {
        sessionStartedTime = System.currentTimeMillis();
        timeoutHandler.renew(this);
        CommandHandler initState = new CommandHandlerGreeting(this, commandHandlerGraphFactory);
        setCommandHandler(initState);
    }

    @Override
    public void fromClient(String text) {
        log.info("{} -> {}", getSessionId(), text);
        timeoutHandler.renew(this);
        commandHandler = commandHandler.handle(text);
    }

    @Override
    public void toClient(String text) {
        log.info("{} <- {}", getSessionId(), text);
        messageSender.send(text);
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public String getClientName() {
        return clientName;
    }

    @Override
    public void setClientName(String name) {
        clientName = name;
    }

    @Override
    public void setCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void closeSession() {
        timeoutHandler.cancel(this);
        messageSender.close();
    }

    @Override
    public long getSessionStartTime() {
        return sessionStartedTime;
    }

}
