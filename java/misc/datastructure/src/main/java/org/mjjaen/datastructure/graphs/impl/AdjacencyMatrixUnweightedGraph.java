package org.mjjaen.datastructure.graphs.impl;

import org.mjjaen.datastructure.graphs.UnweightedGraph;
import org.mjjaen.datastructure.graphs.Vertex;

import java.util.*;

public class AdjacencyMatrixUnweightedGraph<T> implements UnweightedGraph<T> {
    private final List<Vertex<T>> vertices;
    private final boolean[][] adjacencyMatrix;
    private final int vertexCount;
    private final boolean directed;

    public AdjacencyMatrixUnweightedGraph(int vertexCount, boolean directed) {
        vertices = new ArrayList<>(vertexCount);
        adjacencyMatrix = new boolean[vertexCount][vertexCount];
        this.directed = directed;
        this.vertexCount = vertexCount;
    }

    @Override
    public void addVertex(Vertex<T> vertex) {
        if(!vertices.contains(vertex)) {
            if(vertices.size() < vertexCount)
                vertices.add(vertex);
            else
                throw new IllegalArgumentException("Number of vertex higher than the size of the graph");
        }
    }

    @Override
    public void addEdge(Vertex<T> originVertex, Vertex<T> destinationVertex) {
        int i = vertices.indexOf(originVertex);
        int j = vertices.indexOf(destinationVertex);
        if(i == -1 || j == -1)
            throw new IndexOutOfBoundsException("Incorrect edge (" + i + ", " + j + ")");
        adjacencyMatrix[i][j] = true;
        if(!directed)
            adjacencyMatrix[j][i] = true;
    }

    @Override
    public void removeEdge(Vertex<T> originVertex, Vertex<T> destinationVertex) {
        int i = vertices.indexOf(originVertex);
        int j = vertices.indexOf(destinationVertex);
        if(i < 0 || i >= vertexCount || j < 0 || j >= vertexCount)
            throw new IndexOutOfBoundsException("Incorrect edge (" + i + ", " + j + ")");
        adjacencyMatrix[i][j] = false;
        if(!directed)
            adjacencyMatrix[j][i] = false;
    }

    @Override
    public boolean isEdge(Vertex<T> originVertex, Vertex<T> destinationVertex) {
        int i = vertices.indexOf(originVertex);
        int j = vertices.indexOf(destinationVertex);
        if(i < 0 || i >= vertexCount || j < 0 || j >= vertexCount)
            throw new IndexOutOfBoundsException("Incorrect edge (" + i + ", " + j + ")");
        return adjacencyMatrix[i][j];
    }

    @Override
    public Set<Vertex<T>> getAdjacentVertices(Vertex<T> vertex) {
        int i = vertices.indexOf(vertex);
        if(i < 0)
            throw new IndexOutOfBoundsException("Incorrect vertex " + vertex);
        Set<Vertex<T>> adjacentVertices = new LinkedHashSet<>();
        for(int j = 0; j < vertexCount; j++)
            if(adjacencyMatrix[i][j])
                adjacentVertices.add(vertices.get(j));
        return adjacentVertices;
    }

    @Override
    public int getNumberOfVertices() {
        return vertexCount;
    }

    @Override
    public boolean isVertex(Vertex<T> vertex) {
        return vertices.contains(vertex);
    }

    @Override
    public boolean isDirected() {
        return this.directed;
    }
}
