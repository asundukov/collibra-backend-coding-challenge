package asundukov.sockets.graph.engine;

@FunctionalInterface
public interface CommandHandler {
    String DONT_KNOW_ANSWER = "SORRY, I DID NOT UNDERSTAND THAT";

    CommandHandler handle(String message);
}
