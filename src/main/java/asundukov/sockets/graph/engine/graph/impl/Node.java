package asundukov.sockets.graph.engine.graph.impl;

import java.util.HashMap;

public class Node {
    private final String nodeId;
    HashMap<Node, Integer> outcomingEdges = new HashMap<>();
    HashMap<Node, Integer> incomingEdges = new HashMap<>();

    public Node(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeId() {
        return nodeId;
    }

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
