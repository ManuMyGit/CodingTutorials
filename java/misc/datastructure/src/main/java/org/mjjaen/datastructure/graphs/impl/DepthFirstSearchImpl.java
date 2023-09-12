package org.mjjaen.datastructure.graphs.impl;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.datastructure.graphs.DepthFirstSearch;
import org.mjjaen.datastructure.graphs.Graph;
import org.mjjaen.datastructure.graphs.Vertex;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class DepthFirstSearchImpl<T> implements DepthFirstSearch<T> {
    private final Set<Vertex<T>> visited;
    private final Graph<T> graph;

    public DepthFirstSearchImpl(Graph<T> graph) {
        this.graph = graph;
        this.visited = new HashSet<>(graph.getNumberOfVertices());
    }

    public void dfs(Vertex<T> vertex) {
        if(!graph.isVertex(vertex))
            throw new IllegalArgumentException("The vertex " + vertex + " is not present in the graph.");
        doSomething(vertex);
        visited.add(vertex);
        Set<Vertex<T>> adjacents = graph.getAdjacentVertices(vertex);
        adjacents.forEach(adjacent -> {
            if(!visited.contains(adjacent))
                dfs(adjacent);
        });
    }

    private void doSomething(Vertex<T> vertex) {
        log.info(vertex.toString());
    }
}
