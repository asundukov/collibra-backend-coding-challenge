package asundukov.sockets.graph.engine.graph.impl;

import asundukov.sockets.graph.engine.graph.Graph;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GraphReadWriteSynchronized implements Graph {

    private final Graph graph;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public GraphReadWriteSynchronized(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void add(String nodeId) {
        lock.writeLock().lock();
        try {
            graph.add(nodeId);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void remove(String nodeId) {
        lock.writeLock().lock();
        try {
            graph.remove(nodeId);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void addEdge(String from, String to, int weight) {
        lock.writeLock().lock();
        try {
            graph.addEdge(from, to, weight);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public synchronized void removeEdge(String from, String to) {
        lock.writeLock().lock();
        try {
            graph.removeEdge(from, to);
        } finally {
           lock.writeLock().unlock();
        }
    }

    @Override
    public synchronized int shortestPath(String from, String to) {
        lock.readLock().lock();
        try {
            return graph.shortestPath(from, to);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public synchronized List<String> closerThan(int distance, String from) {
        lock.readLock().lock();
        try {
            return graph.closerThan(distance, from);
        } finally {
            lock.readLock().unlock();
        }
    }
}
