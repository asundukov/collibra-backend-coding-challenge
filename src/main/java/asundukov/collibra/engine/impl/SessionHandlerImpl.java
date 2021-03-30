package asundukov.collibra.engine.impl;

import asundukov.collibra.engine.CommandHandler;
import asundukov.collibra.engine.MessageSender;
import asundukov.collibra.engine.SessionHandler;
import asundukov.collibra.engine.TimeoutDetector;

public class SessionHandlerImpl implements SessionHandler {
    private final String sessionId;
    private final MessageSender messageSender;
    private final TimeoutDetector timeoutHandler;
    private long sessionStartedTime;

    private CommandHandler commandHandler = new DefaultCommandHandlerState();
    private String clientName = "";

    public SessionHandlerImpl(String sessionId, MessageSender messageSender, TimeoutDetector timeoutDetector) {
        this.sessionId = sessionId;
        this.messageSender = messageSender;
        this.timeoutHandler = timeoutDetector;
    }

    @Override
    public void start() {
        sessionStartedTime = System.currentTimeMillis();
        timeoutHandler.renew(this);
        CommandHandler initState = new CommandHandlerGreeting(this);
        setCommandHandler(initState);
    }

    @Override
    public void fromClient(String text) {
        System.out.println(getSessionId() + " -> " + text);
        timeoutHandler.renew(this);
        commandHandler = commandHandler.handle(text);
    }

    @Override
    public void toClient(String text) {
        System.out.println(getSessionId() + " <- " + text);
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
