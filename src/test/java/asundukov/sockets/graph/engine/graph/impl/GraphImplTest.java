package asundukov.sockets.graph.engine.graph.impl;

import asundukov.sockets.graph.engine.graph.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphImplTest {

    private Graph graph;

    @BeforeEach
    void beforeEach() {
        graph = new GraphImpl();
    }

    @Test
    void theSame() {
        addAllNodes("A");

        assertEquals(0, graph.shortestPath("A", "A"));
    }

    @Test
    void shortestPathDirect() {
        addAllNodes("A", "B", "C", "D");

        graph.addEdge("A", "B", 2);
        graph.addEdge("A", "D", 3);
        graph.addEdge("A", "C", 1);
        graph.addEdge("C", "D", 1);

        assertEquals(2, graph.shortestPath("A", "D"));
    }

    @Test
    void shortestPathNotConnected() {
        addAllNodes("A", "B", "C", "D");

        graph.addEdge("A", "B", 2);
        graph.addEdge("A", "C", 1);
        graph.addEdge("D", "C", 1);

        assertEquals(Integer.MAX_VALUE, graph.shortestPath("A", "D"));
    }

    @Test
    void shortestPathNotConnectedDirectly() {
        addAllNodes("A", "B", "C", "D", "E");

        graph.addEdge("A", "B", 20);
        graph.addEdge("A", "C", 11);
        graph.addEdge("B", "D", 1);
        graph.addEdge("B", "E", 3);
        graph.addEdge("C", "D", 5);
        graph.addEdge("D", "B", 1);
        graph.addEdge("D", "E", 6);

        assertEquals(11, graph.shortestPath("A", "C"));
        assertEquals(4, graph.shortestPath("D", "E"));
        assertEquals(20, graph.shortestPath("A", "E"));
    }

    @Test
    void getCloserThan() {
        addAllNodes("A", "B", "C", "D", "E");

        graph.addEdge("A", "B", 20);
        graph.addEdge("A", "C", 11);
        graph.addEdge("B", "D", 1);
        graph.addEdge("B", "E", 3);
        graph.addEdge("C", "D", 5);
        graph.addEdge("D", "B", 1);
        graph.addEdge("D", "E", 6);

        assertEquals(Arrays.asList("B", "C", "D"), graph.closerThan(20, "A"));
        assertEquals(Arrays.asList("B", "C", "D", "E"), graph.closerThan(50, "A"));
    }

    private void addAllNodes(String... nodeId) {
        for (String s : nodeId) {
            graph.add(s);
        }
    }
}