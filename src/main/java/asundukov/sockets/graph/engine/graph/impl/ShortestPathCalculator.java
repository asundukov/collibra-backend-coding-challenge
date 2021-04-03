package asundukov.sockets.graph.engine.graph.impl;

import asundukov.sockets.graph.engine.graph.GraphCalculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShortestPathCalculator implements GraphCalculator {
    private final Node startNode;
    private Node targetNode;

    private final Map<Node, Integer> shortestPaths = new HashMap<>();
    private int currentTheShortestDistance = Integer.MAX_VALUE;

    public ShortestPathCalculator(Node startNode) {
        this.startNode = startNode;
    }

    @Override
    public int calculateDistance(Node targetNode) {
        this.targetNode = targetNode;
        if (startNode.equals(targetNode)) {
            return 0;
        }

        findShortestPathToChildren(startNode, 0);
        return currentTheShortestDistance;
    }

    @Override
    public List<String> getAllCloserThan(int distance) {
        this.currentTheShortestDistance = distance;
        findShortestPathToChildren(startNode, 0);
        return shortestPaths.entrySet().stream()
                .filter(e -> e.getValue() < distance)
                .map(e -> e.getKey().getNodeId())
                .filter(s -> !s.equals(startNode.getNodeId()))
                .sorted()
                .collect(Collectors.toList());
    }

    private void findShortestPathToChildren(Node toNode, int currentDistance) {
        checkNeighborNodes(toNode, currentDistance);

        checkNeighborsDeeper(toNode, currentDistance);
    }

    private void checkNeighborsDeeper(Node toNode, int currentDistance) {
        for (Map.Entry<Node, Integer> entry : toNode.outcomingEdges.entrySet()) {
            Node currentNode = entry.getKey();
            int distanceToNode = currentDistance + entry.getValue();
            int foundPath = shortestPaths.get(currentNode);

            if (distanceToNode <= foundPath && distanceToNode < (currentTheShortestDistance - 1)) {
                findShortestPathToChildren(currentNode, distanceToNode);
            }
        }
    }

    private void checkNeighborNodes(Node toNode, int currentDistance) {
        for (Map.Entry<Node, Integer> entry : toNode.outcomingEdges.entrySet()) {
            Node currentNode = entry.getKey();
            int distanceToNode = currentDistance + entry.getValue();
            if (currentNode.equals(targetNode) && distanceToNode < currentTheShortestDistance) {
                currentTheShortestDistance = distanceToNode;
            }

            Integer existedLengthToNode = shortestPaths.get(currentNode);
            if (existedLengthToNode == null || existedLengthToNode > distanceToNode) {
                shortestPaths.put(currentNode, distanceToNode);
            }
        }
    }
}
