package asundukov.sockets.graph.engine.graph.impl;

import asundukov.sockets.graph.engine.graph.Graph;
import asundukov.sockets.graph.engine.graph.exception.NodeAlreadyExistsException;
import asundukov.sockets.graph.engine.graph.exception.NodeDoesNotExistException;

import java.util.Collection;
import java.util.List;
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
        nodes.put(nodeId, new Node(nodeId));
    }

    @Override
    public void remove(String nodeId) {
        Node node = getExistedNode(nodeId);

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
        Node nodeFrom = getExistedNode(from);
        Node nodeTo = getExistedNode(to);

        nodeFrom.addOutcoming(nodeTo, weight);
        nodeTo.addIncoming(nodeFrom, weight);
    }

    @Override
    public void removeEdge(String from, String to) {
        Node nodeFrom = getExistedNode(from);
        Node nodeTo = getExistedNode(to);

        nodeFrom.deleteOutcoming(nodeTo);
        nodeTo.deleteIncoming(nodeFrom);
    }

    @Override
    public int shortestPath(String from, String to) {
        return (new ShortestPathCalculator(getExistedNode(from)))
                .calculateDistance(getExistedNode(to));
    }

    @Override
    public List<String> closerThan(int distance, String from) {
        return (new ShortestPathCalculator(getExistedNode(from)))
                .getAllCloserThan(distance);
    }

    private Node getExistedNode(String nodeId) {
        Node node = nodes.get(nodeId);
        if (node == null) {
            throw new NodeDoesNotExistException();
        }
        return node;
    }


}
