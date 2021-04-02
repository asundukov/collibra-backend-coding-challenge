package asundukov.collibra.engine.impl;

import asundukov.collibra.engine.CommandHandler;
import asundukov.collibra.engine.SessionHandler;
import asundukov.collibra.engine.graph.Graph;
import asundukov.collibra.engine.graph.impl.GraphImpl;
import asundukov.collibra.engine.graph.impl.GraphSynchronized;

public class CommandHandlerGraphFactory {

    private final Graph graph = new GraphSynchronized(new GraphImpl());

    public CommandHandler create(SessionHandler sessionHandler) {
        return new CommandHandlerGraph(sessionHandler, graph);
    }
}
