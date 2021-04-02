package asundukov.collibra.engine.graph.impl;

import asundukov.collibra.engine.graph.Graph;
import asundukov.collibra.engine.graph.exception.NodeAlreadyExistsException;
import asundukov.collibra.engine.graph.exception.NodeDoesNotExistException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * NOT THREAD SAFE
 */
public class GraphImpl implements Graph {
    final protected Map<String, Node> nodes = new ConcurrentHashMap<>();

    @Override
    public void add(String nodeId) {
        if (nodes.containsKey(nodeId)) {
            throw new NodeAlreadyExistsException();
        }
        nodes.put(nodeId, new Node());
    }

    @Override
    public void remove(String nodeId) {
        Node node = nodes.get(nodeId);

        if (!nodes.containsKey(nodeId)) {
            throw new NodeDoesNotExistException();
        }

        for (Node n : node.outcomingEdges.keySet()) {
            n.incomingEdges.remove(node);
        }
        for (Node n : node.incomingEdges.keySet()) {
            n.outcomingEdges.remove(node);
        }
        nodes.remove(nodeId);
    }

    @Override
    public void addEdge(String from, String to, int weight) {
        Node nodeFrom = nodes.get(from);
        Node nodeTo = nodes.get(to);

        if (nodeFrom == null || nodeTo == null) {
            throw new NodeDoesNotExistException();
        }

        nodeFrom.addOutcoming(nodeTo, weight);
        nodeTo.addIncoming(nodeFrom, weight);
    }

    @Override
    public void removeEdge(String from, String to) {
        Node nodeFrom = nodes.get(from);
        Node nodeTo = nodes.get(to);

        if (nodeFrom == null || nodeTo == null) {
            throw new NodeDoesNotExistException();
        }

        nodeFrom.deleteOutcoming(nodeTo);
        nodeTo.deleteIncoming(nodeFrom);
    }

}
