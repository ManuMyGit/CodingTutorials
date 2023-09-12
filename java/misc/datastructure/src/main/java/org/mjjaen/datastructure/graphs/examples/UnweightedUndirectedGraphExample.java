package org.mjjaen.datastructure.graphs.examples;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.datastructure.graphs.UnweightedGraph;
import org.mjjaen.datastructure.graphs.impl.AdjacencyListUnweightedGraph;
import org.mjjaen.datastructure.graphs.impl.AdjacencyMatrixUnweightedGraph;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UnweightedUndirectedGraphExample extends GraphExample {
    @Override
    public void run() {
        log.info("Creating general vertex ...");
        log.info("Testing adjacency matrix unweighted undirected graph ...");
        UnweightedGraph<String> unweightedUndirectedGraph = new AdjacencyMatrixUnweightedGraph<>(8, false);
        prepareGraph(unweightedUndirectedGraph);
        test(unweightedUndirectedGraph);
        log.info("Testing adjacency list unweighted undirected graph ...");
        UnweightedGraph<String> unweightedUndirectedGraph2 = new AdjacencyListUnweightedGraph<>(8, false);
        prepareGraph(unweightedUndirectedGraph2);
        test(unweightedUndirectedGraph2);
    }

    private void prepareGraph(UnweightedGraph<String> unweightedUndirectedGraph) {
        unweightedUndirectedGraph.addVertex(vertexA);
        unweightedUndirectedGraph.addVertex(vertexB);
        unweightedUndirectedGraph.addVertex(vertexC);
        unweightedUndirectedGraph.addVertex(vertexD);
        unweightedUndirectedGraph.addVertex(vertexE);
        unweightedUndirectedGraph.addVertex(vertexF);
        unweightedUndirectedGraph.addVertex(vertexG);
        unweightedUndirectedGraph.addVertex(vertexH);
        unweightedUndirectedGraph.addEdge(vertexA, vertexB);
        unweightedUndirectedGraph.addEdge(vertexB, vertexC);
        unweightedUndirectedGraph.addEdge(vertexB, vertexH);
        unweightedUndirectedGraph.addEdge(vertexC, vertexE);
        unweightedUndirectedGraph.addEdge(vertexC, vertexD);
        unweightedUndirectedGraph.addEdge(vertexH, vertexE);
        unweightedUndirectedGraph.addEdge(vertexE, vertexF);
        unweightedUndirectedGraph.addEdge(vertexE, vertexG);
    }

    private void test(UnweightedGraph<String> unweightedUndirectedGraph) {
        assert !unweightedUndirectedGraph.isEdge(vertexA, vertexA);
        assert unweightedUndirectedGraph.isEdge(vertexA, vertexB);
        assert !unweightedUndirectedGraph.isEdge(vertexA, vertexC);
        assert !unweightedUndirectedGraph.isEdge(vertexA, vertexC);
        assert !unweightedUndirectedGraph.isEdge(vertexA, vertexE);
        assert !unweightedUndirectedGraph.isEdge(vertexA, vertexF);
        assert !unweightedUndirectedGraph.isEdge(vertexA, vertexG);
        assert !unweightedUndirectedGraph.isEdge(vertexA, vertexH);
        assert unweightedUndirectedGraph.isEdge(vertexB, vertexA);
        assert !unweightedUndirectedGraph.isEdge(vertexB, vertexB);
        assert unweightedUndirectedGraph.isEdge(vertexB, vertexC);
        assert !unweightedUndirectedGraph.isEdge(vertexB, vertexD);
        assert !unweightedUndirectedGraph.isEdge(vertexB, vertexE);
        assert !unweightedUndirectedGraph.isEdge(vertexB, vertexF);
        assert !unweightedUndirectedGraph.isEdge(vertexB, vertexG);
        assert unweightedUndirectedGraph.isEdge(vertexB, vertexH);
        assert !unweightedUndirectedGraph.isEdge(vertexC, vertexA);
        assert unweightedUndirectedGraph.isEdge(vertexC, vertexB);
        assert !unweightedUndirectedGraph.isEdge(vertexC, vertexC);
        assert unweightedUndirectedGraph.isEdge(vertexC, vertexD);
        assert unweightedUndirectedGraph.isEdge(vertexC, vertexE);
        assert !unweightedUndirectedGraph.isEdge(vertexC, vertexF);
        assert !unweightedUndirectedGraph.isEdge(vertexC, vertexG);
        assert !unweightedUndirectedGraph.isEdge(vertexC, vertexH);
        assert !unweightedUndirectedGraph.isEdge(vertexD, vertexA);
        assert !unweightedUndirectedGraph.isEdge(vertexD, vertexB);
        assert unweightedUndirectedGraph.isEdge(vertexD, vertexC);
        assert !unweightedUndirectedGraph.isEdge(vertexD, vertexD);
        assert !unweightedUndirectedGraph.isEdge(vertexD, vertexE);
        assert !unweightedUndirectedGraph.isEdge(vertexD, vertexF);
        assert !unweightedUndirectedGraph.isEdge(vertexD, vertexG);
        assert !unweightedUndirectedGraph.isEdge(vertexD, vertexH);
        assert !unweightedUndirectedGraph.isEdge(vertexE, vertexA);
        assert !unweightedUndirectedGraph.isEdge(vertexE, vertexB);
        assert unweightedUndirectedGraph.isEdge(vertexE, vertexC);
        assert !unweightedUndirectedGraph.isEdge(vertexE, vertexD);
        assert !unweightedUndirectedGraph.isEdge(vertexE, vertexE);
        assert unweightedUndirectedGraph.isEdge(vertexE, vertexF);
        assert unweightedUndirectedGraph.isEdge(vertexE, vertexG);
        assert unweightedUndirectedGraph.isEdge(vertexE, vertexH);
        assert !unweightedUndirectedGraph.isEdge(vertexF, vertexA);
        assert !unweightedUndirectedGraph.isEdge(vertexF, vertexB);
        assert !unweightedUndirectedGraph.isEdge(vertexF, vertexC);
        assert !unweightedUndirectedGraph.isEdge(vertexF, vertexD);
        assert unweightedUndirectedGraph.isEdge(vertexF, vertexE);
        assert !unweightedUndirectedGraph.isEdge(vertexF, vertexF);
        assert !unweightedUndirectedGraph.isEdge(vertexF, vertexG);
        assert !unweightedUndirectedGraph.isEdge(vertexF, vertexH);
        assert !unweightedUndirectedGraph.isEdge(vertexG, vertexA);
        assert !unweightedUndirectedGraph.isEdge(vertexG, vertexB);
        assert !unweightedUndirectedGraph.isEdge(vertexG, vertexC);
        assert !unweightedUndirectedGraph.isEdge(vertexG, vertexD);
        assert unweightedUndirectedGraph.isEdge(vertexG, vertexE);
        assert !unweightedUndirectedGraph.isEdge(vertexG, vertexF);
        assert !unweightedUndirectedGraph.isEdge(vertexG, vertexG);
        assert !unweightedUndirectedGraph.isEdge(vertexG, vertexH);
        assert !unweightedUndirectedGraph.isEdge(vertexH, vertexA);
        assert unweightedUndirectedGraph.isEdge(vertexH, vertexB);
        assert !unweightedUndirectedGraph.isEdge(vertexH, vertexC);
        assert !unweightedUndirectedGraph.isEdge(vertexH, vertexD);
        assert unweightedUndirectedGraph.isEdge(vertexH, vertexE);
        assert !unweightedUndirectedGraph.isEdge(vertexH, vertexF);
        assert !unweightedUndirectedGraph.isEdge(vertexH, vertexG);
        assert !unweightedUndirectedGraph.isEdge(vertexH, vertexH);

        unweightedUndirectedGraph.removeEdge(vertexE, vertexG);
        assert !unweightedUndirectedGraph.isEdge(vertexE, vertexA);
        assert !unweightedUndirectedGraph.isEdge(vertexE, vertexB);
        assert unweightedUndirectedGraph.isEdge(vertexE, vertexC);
        assert !unweightedUndirectedGraph.isEdge(vertexE, vertexD);
        assert !unweightedUndirectedGraph.isEdge(vertexE, vertexE);
        assert unweightedUndirectedGraph.isEdge(vertexE, vertexF);
        assert !unweightedUndirectedGraph.isEdge(vertexE, vertexG);
        assert unweightedUndirectedGraph.isEdge(vertexE, vertexH);
        assert !unweightedUndirectedGraph.isEdge(vertexG, vertexA);
        assert !unweightedUndirectedGraph.isEdge(vertexG, vertexB);
        assert !unweightedUndirectedGraph.isEdge(vertexG, vertexC);
        assert !unweightedUndirectedGraph.isEdge(vertexG, vertexD);
        assert !unweightedUndirectedGraph.isEdge(vertexG, vertexE);
        assert !unweightedUndirectedGraph.isEdge(vertexG, vertexF);
        assert !unweightedUndirectedGraph.isEdge(vertexG, vertexG);
        assert !unweightedUndirectedGraph.isEdge(vertexG, vertexH);
    }
}
