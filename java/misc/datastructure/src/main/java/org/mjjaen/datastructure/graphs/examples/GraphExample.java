package org.mjjaen.datastructure.graphs.examples;

import org.mjjaen.datastructure.graphs.Vertex;
import org.mjjaen.datastructure.graphs.impl.VertexImpl;

import java.util.ArrayList;
import java.util.List;

public abstract class GraphExample {
    Vertex<String> vertexA = getListOfVertices().get(0);
    Vertex<String> vertexB = getListOfVertices().get(1);
    Vertex<String> vertexC = getListOfVertices().get(2);
    Vertex<String> vertexD = getListOfVertices().get(3);
    Vertex<String> vertexE = getListOfVertices().get(4);
    Vertex<String> vertexF = getListOfVertices().get(5);
    Vertex<String> vertexG = getListOfVertices().get(6);
    Vertex<String> vertexH = getListOfVertices().get(7);

    protected List<Vertex<String>> getListOfVertices() {
        List<Vertex<String>> list = new ArrayList<>();
        list.add(new VertexImpl<>("VertexA"));
        list.add(new VertexImpl<>("VertexB"));
        list.add(new VertexImpl<>("VertexC"));
        list.add(new VertexImpl<>("VertexD"));
        list.add(new VertexImpl<>("VertexE"));
        list.add(new VertexImpl<>("VertexF"));
        list.add(new VertexImpl<>("VertexG"));
        list.add(new VertexImpl<>("VertexH"));
        list.add(new VertexImpl<>("VertexI"));
        list.add(new VertexImpl<>("VertexJ"));
        list.add(new VertexImpl<>("VertexK"));
        list.add(new VertexImpl<>("VertexL"));
        list.add(new VertexImpl<>("VertexM"));
        list.add(new VertexImpl<>("VertexN"));
        list.add(new VertexImpl<>("VertexO"));
        list.add(new VertexImpl<>("VertexP"));
        list.add(new VertexImpl<>("VertexQ"));
        list.add(new VertexImpl<>("VertexR"));
        list.add(new VertexImpl<>("VertexS"));
        list.add(new VertexImpl<>("VertexT"));
        list.add(new VertexImpl<>("VertexU"));
        list.add(new VertexImpl<>("VertexV"));
        list.add(new VertexImpl<>("VertexW"));
        list.add(new VertexImpl<>("VertexX"));
        list.add(new VertexImpl<>("VertexY"));
        list.add(new VertexImpl<>("VertexZ"));
        return list;
    }

    public abstract void run();
}
