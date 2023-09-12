package org.mjjaen.datastructure.graphs.impl;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.datastructure.graphs.BreadthFirstSearch;
import org.mjjaen.datastructure.graphs.Graph;
import org.mjjaen.datastructure.graphs.Vertex;

import java.util.*;

@Slf4j
public class BreadthFirstSearchImpl<T> implements BreadthFirstSearch<T> {
    private final Set<Vertex<T>> visited;
    private final Graph<T> graph;
    private final Queue<Vertex<T>> queue;

    public BreadthFirstSearchImpl(Graph<T> graph) {
        this.graph = graph;
        this.visited = new HashSet<>(graph.getNumberOfVertices());
        this.queue = new LinkedList<>();
    }

    public void bfs(Vertex<T> vertex) {
        if(!graph.isVertex(vertex))
            throw new IllegalArgumentException("The vertex " + vertex + " is not present in the graph.");
        visited.add(vertex);
        queue.add(vertex);
        while(queue.size() != 0) {
            Vertex<T> currentVertex = queue.poll();
            doSomething(currentVertex);
            Iterator<Vertex<T>> iterator = graph.getAdjacentVertices(currentVertex).iterator();
            while (iterator.hasNext()) {
                Vertex<T> n = iterator.next();
                if (!visited.contains(n)) {
                    visited.add(n);
                    queue.add(n);
                }
            }
        }
    }

    private void doSomething(Vertex<T> vertex) {
        log.info(vertex.toString());
    }
}
