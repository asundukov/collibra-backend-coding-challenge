package asundukov.sockets.graph.engine.graph;

import asundukov.sockets.graph.engine.graph.impl.Node;

import java.util.List;

public interface GraphCalculator {
    int calculateDistance(Node targetNode);

    List<String> getAllCloserThan(int distance);
}
