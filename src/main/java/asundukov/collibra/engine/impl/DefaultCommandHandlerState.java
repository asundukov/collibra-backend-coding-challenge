package asundukov.collibra.engine.impl;

import asundukov.collibra.engine.CommandHandler;

public class DefaultCommandHandlerState implements CommandHandler {
    @Override
    public CommandHandler handle(String message) {
        return this;
    }
}
