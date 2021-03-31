package asundukov.collibra.engine.graph;

import asundukov.collibra.engine.graph.exception.NodeAlreadyExistsException;
import asundukov.collibra.engine.graph.exception.NodeDoesNotExistException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Graph {
    final Object edgesManipulationLock = new Object();
    final Map<String, Node> nodes = new ConcurrentHashMap<>();

    public void add(String nodeId) {
        synchronized (nodeId.intern()) {
            if (nodes.containsKey(nodeId)) {
                throw new NodeAlreadyExistsException();
            }
            nodes.put(nodeId, new Node());
        }
    }

    public void delete(String nodeId) {
        synchronized (nodeId.intern()) {
            Node node = nodes.get(nodeId);

            if (!nodes.containsKey(nodeId)) {
                throw new NodeDoesNotExistException();
            }

            synchronized (edgesManipulationLock) {
                for (Node n : node.outcomingEdges.keySet()) {
                    n.incomingEdges.remove(node);
                }
                for (Node n : node.incomingEdges.keySet()) {
                    n.outcomingEdges.remove(node);
                }
                nodes.remove(nodeId);
            }
        }
    }

    public void addEdge(String from, String to, int weight) {
        synchronized (edgesManipulationLock) {
            Node nodeFrom = nodes.get(from);
            Node nodeTo = nodes.get(to);

            if (nodeFrom == null || nodeTo == null) {
                throw new NodeDoesNotExistException();
            }

            nodeFrom.addOutcoming(nodeTo, weight);
            nodeTo.addIncoming(nodeFrom, weight);
        }
    }

    public void removeEdge(String from, String to) {
        synchronized (edgesManipulationLock) {
            Node nodeFrom = nodes.get(from);
            Node nodeTo = nodes.get(to);

            if (nodeFrom == null || nodeTo == null) {
                throw new NodeDoesNotExistException();
            }

            nodeFrom.deleteOutcoming(nodeTo);
            nodeTo.deleteIncoming(nodeFrom);
        }
    }

}
