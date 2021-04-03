package asundukov.sockets.graph.engine.impl.command;

import asundukov.sockets.graph.engine.CommandHandler;
import asundukov.sockets.graph.engine.SessionHandler;
import asundukov.sockets.graph.engine.graph.Graph;
import asundukov.sockets.graph.engine.impl.command.graph.GraphCommandChecker;

public class CommandHandlerGraph extends SessionCommandHandler {
    private static final String BYE_REQUEST = "BYE MATE!";
    private final Graph graph;

    CommandHandlerGraph(SessionHandler sessionHandler, Graph graph) {
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
