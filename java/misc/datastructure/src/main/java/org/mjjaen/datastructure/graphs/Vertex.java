package org.mjjaen.datastructure.graphs;

public interface Vertex<T> {
    T getValue();
    void setValue(T value);
    String toString();
}
