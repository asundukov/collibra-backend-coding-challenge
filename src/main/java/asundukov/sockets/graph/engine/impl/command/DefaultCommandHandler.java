package asundukov.sockets.graph.engine.impl.command;

import asundukov.sockets.graph.engine.CommandHandler;

public class DefaultCommandHandler implements CommandHandler {
    @Override
    public CommandHandler handle(String message) {
        return this;
    }
}
