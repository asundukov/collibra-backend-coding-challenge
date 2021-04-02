package asundukov.sockets.graph.engine.graph;

public interface Graph {
    void add(String nodeId);

    void remove(String nodeId);

    void addEdge(String from, String to, int weight);

    void removeEdge(String from, String to);

    int shortestPath(String from, String to);
}
