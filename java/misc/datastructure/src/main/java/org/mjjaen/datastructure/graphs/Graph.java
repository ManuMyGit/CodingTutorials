package org.mjjaen.datastructure.graphs;

import java.util.Set;

public interface Graph<T> {
    void addVertex(Vertex<T> vertex);
    void removeEdge(Vertex<T> originVertex, Vertex<T> destinationVertex);
    boolean isDirected();
    Set<Vertex<T>> getAdjacentVertices(Vertex<T> vertex);
    int getNumberOfVertices();
    boolean isVertex(Vertex<T> vertex);
}
