package asundukov.collibra.engine.impl;

import asundukov.collibra.engine.CommandHandler;
import asundukov.collibra.engine.SessionHandler;
import asundukov.collibra.engine.graph.Graph;
import asundukov.collibra.engine.impl.graph.GraphCommandChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandlerGraph extends SessionCommandHandler {
    private static final Logger log = LoggerFactory.getLogger(CommandHandlerGraph.class);
    private static final String BYE_REQUEST = "BYE MATE!";
    private final Graph graph;

    protected CommandHandlerGraph(SessionHandler sessionHandler, Graph graph) {
        super(sessionHandler);
        this.graph = graph;
    }

    @Override
    public CommandHandler handle(String message) {
        GraphCommandChecker checker = new GraphCommandChecker(message);
        if (BYE_REQUEST.equals(message)) {
            return new CommandHandlerBye(sessionHandler);
        } else if (checker.isGraphCommand()) {
            sessionHandler.toClient(checker.executeAndGetResult(graph));
        } else {
            sessionHandler.toClient(DONT_KNOW_ANSWER);
        }
        return this;
    }
}
