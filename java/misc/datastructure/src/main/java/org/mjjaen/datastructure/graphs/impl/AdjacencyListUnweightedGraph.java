package org.mjjaen.datastructure.graphs.impl;

import org.mjjaen.datastructure.graphs.UnweightedGraph;
import org.mjjaen.datastructure.graphs.Vertex;

import java.util.*;

public class AdjacencyListUnweightedGraph<T> implements UnweightedGraph<T> {
    private final Map<Vertex<T>, Set<Vertex<T>>> adjacencyList;
    private final boolean directed;
    private final int vertexCount;

    public AdjacencyListUnweightedGraph(int vertexCount, boolean directed) {
        adjacencyList = new HashMap<>(vertexCount);
        this.directed = directed;
        this.vertexCount = vertexCount;
    }

    @Override
    public void addVertex(Vertex<T> vertex) {
        if(!adjacencyList.containsKey(vertex)) {
            if(adjacencyList.size() < vertexCount) {
                adjacencyList.put(vertex, new LinkedHashSet<>());
            } else
                throw new IllegalArgumentException("Number of vertex higher than the size of the graph");
        }
    }

    @Override
    public void addEdge(Vertex<T> originVertex, Vertex<T> destinationVertex) {
        if (!adjacencyList.containsKey(originVertex) || !adjacencyList.containsKey(destinationVertex)) {
            throw new IllegalArgumentException("Vertex does not exist");
        }
        adjacencyList.get(originVertex).add(destinationVertex);
        if(!directed)
            adjacencyList.get(destinationVertex).add(originVertex);
    }

    @Override
    public void removeEdge(Vertex<T> originVertex, Vertex<T> destinationVertex) {
        if (!adjacencyList.containsKey(originVertex) || !adjacencyList.containsKey(destinationVertex)) {
            throw new IllegalArgumentException("Vertex does not exist");
        }
        adjacencyList.get(originVertex).remove(destinationVertex);
        if(!directed)
            adjacencyList.get(destinationVertex).remove(originVertex);
    }

    @Override
    public boolean isDirected() {
        return directed;
    }

    @Override
    public boolean isEdge(Vertex<T> originVertex, Vertex<T> destinationVertex) {
        return adjacencyList.get(originVertex).contains(destinationVertex);
    }

    @Override
    public Set<Vertex<T>> getAdjacentVertices(Vertex<T> vertex) {
        return adjacencyList.get(vertex);
    }

    @Override
    public int getNumberOfVertices() {
        return vertexCount;
    }

    @Override
    public boolean isVertex(Vertex<T> vertex) {
        return adjacencyList.containsKey(vertex);
    }
}