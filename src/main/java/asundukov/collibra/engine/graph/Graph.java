package asundukov.collibra.engine.graph;

public interface Graph {
    void add(String nodeId);

    void remove(String nodeId);

    void addEdge(String from, String to, int weight);

    void removeEdge(String from, String to);
}
