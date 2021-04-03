package asundukov.sockets.graph.engine.impl.command.graph;

import asundukov.sockets.graph.engine.graph.Graph;
import asundukov.sockets.graph.engine.graph.exception.NodeDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

class GraphCommandCheckerTest {

    private final Graph graph = mock(Graph.class);

    @BeforeEach
    public void beforeEach() {
        reset(graph);
    }

    @Test
    void isGraphCommandAddNode() {
        GraphCommandChecker checker = new GraphCommandChecker("ADD NODE some-node-2");
        assertTrue(checker.isGraphCommand());
    }

    @Test
    void isGraphCommandRemoveNode() {
        GraphCommandChecker checker = new GraphCommandChecker("REMOVE NODE some-node-2");
        assertTrue(checker.isGraphCommand());
    }

    @Test
    void isGraphCommandAddEdge() {
        GraphCommandChecker checker = new GraphCommandChecker("ADD EDGE some-node-2 some-node-3 23");
        assertTrue(checker.isGraphCommand());
    }

    @Test
    void isGraphCommandRemoveEdge() {
        GraphCommandChecker checker = new GraphCommandChecker("REMOVE EDGE some-node-2 some-node-3");
        assertTrue(checker.isGraphCommand());
    }

    @Test
    void isNotGraphCommand() {
        GraphCommandChecker checker = new GraphCommandChecker("REMOVE GRAPH");
        assertFalse(checker.isGraphCommand());
    }

    @Test
    void callAddNode() {
        GraphCommandChecker checker = createAndCheck("ADD NODE starship-SN-191");
        String result = checker.executeAndGetResult(graph);

        verify(graph, only()).add("starship-SN-191");
        assertEquals("NODE ADDED", result);
    }

    @Test
    void callRemoveNode() {
        Mockito.doThrow(new NodeDoesNotExistException()).when(graph).remove("starship-SN-191");
        GraphCommandChecker checker = createAndCheck("REMOVE NODE starship-SN-191");
        String result = checker.executeAndGetResult(graph);

        verify(graph, only()).remove("starship-SN-191");
        assertEquals("ERROR: NODE NOT FOUND", result);
    }

    private GraphCommandChecker createAndCheck(String command) {
        GraphCommandChecker checker = new GraphCommandChecker(command);
        checker.isGraphCommand();
        return checker;
    }
}
