package org.mjjaen.datastructure.graphs.impl;

import org.mjjaen.datastructure.graphs.Vertex;
import org.mjjaen.datastructure.graphs.WeightedGraph;

import java.util.*;

public class AdjacencyListWeightedGraph<T> implements WeightedGraph<T> {
    private final Map<Vertex<T>, Map<Vertex<T>, Integer>> adjacencyListAndWeights;
    private final boolean directed;
    private final int vertexCount;

    public AdjacencyListWeightedGraph(int vertexCount, boolean directed) {
        this.directed = directed;
        this.vertexCount = vertexCount;
        adjacencyListAndWeights = new HashMap<>(vertexCount);
    }

    @Override
    public void addVertex(Vertex<T> vertex) {
        if(!adjacencyListAndWeights.containsKey(vertex)) {
            if (adjacencyListAndWeights.size() < vertexCount)
                adjacencyListAndWeights.put(vertex, new LinkedHashMap<>());
            else
                throw new IllegalArgumentException("Number of vertex higher than the size of the graph");
        }
    }

    @Override
    public void addEdge(Vertex<T> originVertex, Vertex<T> destinationVertex, int weight) {
        if(!adjacencyListAndWeights.containsKey(originVertex))
            throw new IllegalArgumentException("Vertex does not exist");

        adjacencyListAndWeights.get(originVertex).put(destinationVertex, weight);
        if(!directed)
            adjacencyListAndWeights.get(destinationVertex).put(originVertex, weight);
    }

    @Override
    public void removeEdge(Vertex<T> originVertex, Vertex<T> destinationVertex) {
        if(!adjacencyListAndWeights.containsKey(originVertex))
            throw new IllegalArgumentException("Vertex does not exist");

        adjacencyListAndWeights.get(originVertex).remove(destinationVertex);
        if(!directed)
            adjacencyListAndWeights.get(destinationVertex).remove(originVertex);
    }

    @Override
    public boolean isDirected() {
        return directed;
    }

    @Override
    public Integer getEdge(Vertex<T> originVertex, Vertex<T> destinationVertex) {
        return adjacencyListAndWeights.get(originVertex).get(destinationVertex);
    }

    @Override
    public Set<Vertex<T>> getAdjacentVertices(Vertex<T> vertex) {
        return new HashSet<>(adjacencyListAndWeights.get(vertex).keySet());
    }

    @Override
    public int getNumberOfVertices() {
        return vertexCount;
    }

    @Override
    public boolean isVertex(Vertex<T> vertex) {
        return adjacencyListAndWeights.containsKey(vertex);
    }

    @Override
    public Map<Vertex<T>, Integer> getAdjacentWeights(Vertex vertex) {
        return adjacencyListAndWeights.get(vertex);
    }
}
