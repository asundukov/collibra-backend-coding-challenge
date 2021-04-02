package asundukov.sockets.graph.engine.graph.impl;

import asundukov.sockets.graph.engine.graph.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphImplTest {


    private Graph graph;

    @BeforeEach
    public void beforeEach() {
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

        assertEquals(20, graph.shortestPath("A", "E"));
    }

    private void addAllNodes(String... nodeId) {
        for (String s : nodeId) {
            graph.add(s);
        }
    }
}