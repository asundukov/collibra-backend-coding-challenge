package asundukov.sockets.graph.engine.graph.impl;

import java.util.HashMap;
import java.util.Map;

public class ShortestPathCalculator {
        private final Node from;
        private final Node to;

        private final Map<Node, Integer> shortestPathes = new HashMap<>();
        private int currentTheShortestDistance = Integer.MAX_VALUE;
        
        public ShortestPathCalculator(Node from, Node to) {
            this.from = from;
            this.to = to;
        }

        public int calculate() {
            if (from.equals(to)) {
                return 0;
            }

            findShortestPathToChilds(from, 0);
            return currentTheShortestDistance;
        }

        private void findShortestPathToChilds(Node toNode, int currentDistance) {
            for (Map.Entry<Node, Integer> entry : toNode.outcomingEdges.entrySet()) {
                Node currentNode = entry.getKey();
                Integer distanceToNode = currentDistance + entry.getValue();
                if (currentNode.equals(to) && distanceToNode < currentTheShortestDistance) {
                    currentTheShortestDistance = distanceToNode;
                }

                Integer existedLengthToNode = shortestPathes.get(currentNode);
                if (existedLengthToNode == null) {
                    shortestPathes.put(currentNode, distanceToNode);
                } else if (existedLengthToNode > distanceToNode) {
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