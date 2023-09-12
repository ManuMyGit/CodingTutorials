package org.mjjaen.datastructure.graphs.impl;

import org.mjjaen.datastructure.graphs.Vertex;
import org.mjjaen.datastructure.graphs.WeightedGraph;

import java.util.*;

public class AdjacencyMatrixWeightedGraph<T> implements WeightedGraph<T> {
    private final List<Vertex<T>> vertices;
    private final Integer[][] adjacencyMatrix;
    private final int vertexCount;
    private final boolean directed;

    public AdjacencyMatrixWeightedGraph(int vertexCount, boolean directed) {
        vertices = new ArrayList<>(vertexCount);
        adjacencyMatrix = new Integer[vertexCount][vertexCount];
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
    public void addEdge(Vertex<T> originVertex, Vertex<T> destinationVertex, int weight) {
        int i = vertices.indexOf(originVertex);
        int j = vertices.indexOf(destinationVertex);
        if(i == -1 || j == -1)
            throw new IndexOutOfBoundsException("Incorrect edge (" + i + ", " + j + ")");
        adjacencyMatrix[i][j] = weight;
        if(!directed)
            adjacencyMatrix[j][i] = weight;
    }

    @Override
    public void removeEdge(Vertex<T> originVertex, Vertex<T> destinationVertex) {
        int i = vertices.indexOf(originVertex);
        int j = vertices.indexOf(destinationVertex);
        if(i < 0 || i >= vertexCount || j < 0 || j >= vertexCount)
            throw new IndexOutOfBoundsException("Incorrect edge (" + i + ", " + j + ")");
        adjacencyMatrix[i][j] = null;
        if(!directed)
            adjacencyMatrix[j][i] = null;
    }

    @Override
    public Integer getEdge(Vertex<T> originVertex, Vertex<T> destinationVertex) {
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
            if(adjacencyMatrix[i][j] != null)
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
    public Map<Vertex<T>, Integer> getAdjacentWeights(Vertex<T> vertex) {
        int i = vertices.indexOf(vertex);
        if(i < 0)
            throw new IndexOutOfBoundsException("Incorrect vertex " + vertex);
        Map<Vertex<T>, Integer> adjacentVertices = new LinkedHashMap<>();
        for(int j = 0; j < vertexCount; j++)
            if(adjacencyMatrix[i][j] != null)
                adjacentVertices.put(vertices.get(j), adjacencyMatrix[i][j]);
        return adjacentVertices;
    }

    @Override
    public boolean isDirected() {
        return this.directed;
    }
}

