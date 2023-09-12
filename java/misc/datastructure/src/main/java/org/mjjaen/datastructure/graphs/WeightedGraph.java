package org.mjjaen.datastructure.graphs;

import java.util.Map;
import java.util.Set;

public interface WeightedGraph<T> extends Graph<T>{
    void addEdge(Vertex<T> originVertex, Vertex<T> destinationVertex, int weight);
    Integer getEdge(Vertex<T> originVertex, Vertex<T> destinationVertex);
    Map<Vertex<T>, Integer> getAdjacentWeights(Vertex<T> vertex);
}
