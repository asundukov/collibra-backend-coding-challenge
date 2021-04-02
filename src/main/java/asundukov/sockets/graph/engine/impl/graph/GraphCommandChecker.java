package asundukov.sockets.graph.engine.impl.graph;

import asundukov.sockets.graph.engine.graph.Graph;

import java.util.regex.Matcher;

public class GraphCommandChecker {
    private final String command;

    private Matcher matcher;
    private GraphCommand foundCommand;

    public GraphCommandChecker(String command) {
        this.command = command;
    }

    public boolean isGraphCommand() {
        for (GraphCommand graphCommand : GraphCommand.values()) {
            Matcher m = graphCommand.match(command);
            if (m.find()) {
                this.matcher = m;
                this.foundCommand = graphCommand;
                return true;
            }
        }
        return false;
    }

    public String executeAndGetResult(Graph graph) {
        if (foundCommand == null) {
            throw new RuntimeException("Command did not found before executing");
        }
        return foundCommand.execute(graph, matcher);
    }
}
