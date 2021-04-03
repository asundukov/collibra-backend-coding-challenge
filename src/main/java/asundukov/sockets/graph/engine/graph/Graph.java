package asundukov.sockets.graph.engine.graph;

import java.util.List;

public interface Graph {
    void add(String nodeId);

    void remove(String nodeId);

    void addEdge(String from, String to, int weight);

    void removeEdge(String from, String to);

    int shortestPath(String from, String to);

    List<String> closerThan(int distance, String from);
}
