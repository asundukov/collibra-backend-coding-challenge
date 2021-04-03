package asundukov.sockets.graph.engine.graph.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ShortestPathCalculator {
    private final Node startNode;
    private Node targetNode;

    private final Map<Node, Integer> shortestPathes = new HashMap<>();
    private int currentTheShortestDistance = Integer.MAX_VALUE;

    public ShortestPathCalculator(Node startNode) {
        this.startNode = startNode;
    }

    public int calculateDistance(Node targetNode) {
        this.targetNode = targetNode;
        if (startNode.equals(targetNode)) {
            return 0;
        }

        findShortestPathToChilds(startNode, 0);
        return currentTheShortestDistance;
    }

    public List<String> getAllCloserThan(int distance) {
        this.currentTheShortestDistance = distance;
        findShortestPathToChilds(startNode, 0);
        return shortestPathes.entrySet().stream()
                .filter(e -> e.getValue() < distance)
                .map(e -> e.getKey().getNodeId())
                .filter(s -> !s.equals(startNode.getNodeId()))
                .sorted()
                .collect(Collectors.toList());
    }

    private void findShortestPathToChilds(Node toNode, int currentDistance) {
        for (Map.Entry<Node, Integer> entry : toNode.outcomingEdges.entrySet()) {
            Node currentNode = entry.getKey();
            int distanceToNode = currentDistance + entry.getValue();
            if (currentNode.equals(targetNode) && distanceToNode < currentTheShortestDistance) {
                currentTheShortestDistance = distanceToNode;
            }

            Integer existedLengthToNode = shortestPathes.get(currentNode);
            if (existedLengthToNode == null || existedLengthToNode > distanceToNode) {
                shortestPathes.put(currentNode, distanceToNode);
            }
        }

        for (Map.Entry<Node, Integer> entry : toNode.outcomingEdges.entrySet()) {
            Node currentNode = entry.getKey();
            int distanceToNode = currentDistance + entry.getValue();
            int foundPath = shortestPathes.get(currentNode);

            if (distanceToNode <= foundPath && distanceToNode < (currentTheShortestDistance - 1)) {
                findShortestPathToChilds(currentNode, distanceToNode);
            }
        }
    }
}
