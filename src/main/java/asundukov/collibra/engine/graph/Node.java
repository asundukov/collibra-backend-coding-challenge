package asundukov.collibra.engine.graph;

import java.util.HashMap;

public class Node {
    final HashMap<Node, Integer> outcomingEdges = new HashMap<>();
    final HashMap<Node, Integer> incomingEdges = new HashMap<>();

    public void addIncoming(Node from, int weight) {
        incomingEdges
                .compute(from, (k, v) -> v == null ? weight : Math.min(weight, v));
    }

    public void deleteIncoming(Node from) {
        incomingEdges.remove(from);
    }

    public void addOutcoming(Node to, int weight) {
        outcomingEdges
                .compute(to, (k, v) -> v == null ? weight : Math.min(weight, v));
    }

    public void deleteOutcoming(Node from) {
        outcomingEdges.remove(from);
    }
}
