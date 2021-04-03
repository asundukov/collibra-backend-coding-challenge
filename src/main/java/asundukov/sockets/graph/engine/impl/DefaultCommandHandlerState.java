package asundukov.sockets.graph.engine.impl;

import asundukov.sockets.graph.engine.CommandHandler;

public class DefaultCommandHandlerState implements CommandHandler {
    @Override
    public CommandHandler handle(String message) {
        return this;
    }
}
