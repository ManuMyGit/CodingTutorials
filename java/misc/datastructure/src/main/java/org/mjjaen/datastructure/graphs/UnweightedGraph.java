package org.mjjaen.datastructure.graphs;

import java.util.Set;

public interface UnweightedGraph<T> extends Graph<T>{
    void addEdge(Vertex<T> originVertex, Vertex<T> destinationVertex);
    boolean isEdge(Vertex<T> originVertex, Vertex<T> destinationVertex);
}
