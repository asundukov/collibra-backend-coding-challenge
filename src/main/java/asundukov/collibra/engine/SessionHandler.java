package asundukov.collibra.engine;

public interface SessionHandler {
    void start();

    void fromClient(String text);

    void toClient(String text);

    String getSessionId();

    String getClientName();

    void setClientName(String name);

    void setCommandHandler(CommandHandler initState);

    void closeSession();

    long getSessionStartTime();
}
