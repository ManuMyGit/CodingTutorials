package org.mjjaen.datastructure.graphs.examples;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.datastructure.graphs.UnweightedGraph;
import org.mjjaen.datastructure.graphs.impl.AdjacencyListUnweightedGraph;
import org.mjjaen.datastructure.graphs.impl.AdjacencyMatrixUnweightedGraph;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UnweightedDirectedGraphExample extends GraphExample {
    @Override
    public void run() {
        log.info("Creating general vertex ...");
        log.info("Testing adjacency matrix new unweighted directed graph ...");
        UnweightedGraph<String> unweightedDirectedGraph = new AdjacencyMatrixUnweightedGraph<>(8, true);
        prepareGraph(unweightedDirectedGraph);
        test(unweightedDirectedGraph);
        log.info("Testing adjacency list new unweighted directed graph ...");
        UnweightedGraph<String> unweightedDirectedGraph2 = new AdjacencyListUnweightedGraph<>(8, true);
        prepareGraph(unweightedDirectedGraph2);
        test(unweightedDirectedGraph2);
    }

    private void prepareGraph(UnweightedGraph<String> unweightedDirectedGraph) {
        unweightedDirectedGraph.addVertex(vertexA);
        unweightedDirectedGraph.addVertex(vertexB);
        unweightedDirectedGraph.addVertex(vertexC);
        unweightedDirectedGraph.addVertex(vertexD);
        unweightedDirectedGraph.addVertex(vertexE);
        unweightedDirectedGraph.addVertex(vertexF);
        unweightedDirectedGraph.addVertex(vertexG);
        unweightedDirectedGraph.addVertex(vertexH);
        unweightedDirectedGraph.addEdge(vertexA, vertexB);
        unweightedDirectedGraph.addEdge(vertexB, vertexC);
        unweightedDirectedGraph.addEdge(vertexB, vertexH);
        unweightedDirectedGraph.addEdge(vertexC, vertexE);
        unweightedDirectedGraph.addEdge(vertexC, vertexD);
        unweightedDirectedGraph.addEdge(vertexH, vertexE);
        unweightedDirectedGraph.addEdge(vertexE, vertexF);
        unweightedDirectedGraph.addEdge(vertexE, vertexG);
    }

    private void test(UnweightedGraph<String> unweightedDirectedGraph) {
        assert !unweightedDirectedGraph.isEdge(vertexA, vertexA);
        assert unweightedDirectedGraph.isEdge(vertexA, vertexB);
        assert !unweightedDirectedGraph.isEdge(vertexA, vertexC);
        assert !unweightedDirectedGraph.isEdge(vertexA, vertexC);
        assert !unweightedDirectedGraph.isEdge(vertexA, vertexE);
        assert !unweightedDirectedGraph.isEdge(vertexA, vertexF);
        assert !unweightedDirectedGraph.isEdge(vertexA, vertexG);
        assert !unweightedDirectedGraph.isEdge(vertexA, vertexH);
        assert !unweightedDirectedGraph.isEdge(vertexB, vertexA);
        assert !unweightedDirectedGraph.isEdge(vertexB, vertexB);
        assert unweightedDirectedGraph.isEdge(vertexB, vertexC);
        assert !unweightedDirectedGraph.isEdge(vertexB, vertexD);
        assert !unweightedDirectedGraph.isEdge(vertexB, vertexE);
        assert !unweightedDirectedGraph.isEdge(vertexB, vertexF);
        assert !unweightedDirectedGraph.isEdge(vertexB, vertexG);
        assert unweightedDirectedGraph.isEdge(vertexB, vertexH);
        assert !unweightedDirectedGraph.isEdge(vertexC, vertexA);
        assert !unweightedDirectedGraph.isEdge(vertexC, vertexB);
        assert !unweightedDirectedGraph.isEdge(vertexC, vertexC);
        assert unweightedDirectedGraph.isEdge(vertexC, vertexD);
        assert unweightedDirectedGraph.isEdge(vertexC, vertexE);
        assert !unweightedDirectedGraph.isEdge(vertexC, vertexF);
        assert !unweightedDirectedGraph.isEdge(vertexC, vertexG);
        assert !unweightedDirectedGraph.isEdge(vertexC, vertexH);
        assert !unweightedDirectedGraph.isEdge(vertexD, vertexA);
        assert !unweightedDirectedGraph.isEdge(vertexD, vertexB);
        assert !unweightedDirectedGraph.isEdge(vertexD, vertexC);
        assert !unweightedDirectedGraph.isEdge(vertexD, vertexD);
        assert !unweightedDirectedGraph.isEdge(vertexD, vertexE);
        assert !unweightedDirectedGraph.isEdge(vertexD, vertexF);
        assert !unweightedDirectedGraph.isEdge(vertexD, vertexG);
        assert !unweightedDirectedGraph.isEdge(vertexD, vertexH);
        assert !unweightedDirectedGraph.isEdge(vertexE, vertexA);
        assert !unweightedDirectedGraph.isEdge(vertexE, vertexB);
        assert !unweightedDirectedGraph.isEdge(vertexE, vertexC);
        assert !unweightedDirectedGraph.isEdge(vertexE, vertexD);
        assert !unweightedDirectedGraph.isEdge(vertexE, vertexE);
        assert unweightedDirectedGraph.isEdge(vertexE, vertexF);
        assert unweightedDirectedGraph.isEdge(vertexE, vertexG);
        assert !unweightedDirectedGraph.isEdge(vertexE, vertexH);
        assert !unweightedDirectedGraph.isEdge(vertexF, vertexA);
        assert !unweightedDirectedGraph.isEdge(vertexF, vertexB);
        assert !unweightedDirectedGraph.isEdge(vertexF, vertexC);
        assert !unweightedDirectedGraph.isEdge(vertexF, vertexD);
        assert !unweightedDirectedGraph.isEdge(vertexF, vertexE);
        assert !unweightedDirectedGraph.isEdge(vertexF, vertexF);
        assert !unweightedDirectedGraph.isEdge(vertexF, vertexG);
        assert !unweightedDirectedGraph.isEdge(vertexF, vertexH);
        assert !unweightedDirectedGraph.isEdge(vertexG, vertexA);
        assert !unweightedDirectedGraph.isEdge(vertexG, vertexB);
        assert !unweightedDirectedGraph.isEdge(vertexG, vertexC);
        assert !unweightedDirectedGraph.isEdge(vertexG, vertexD);
        assert !unweightedDirectedGraph.isEdge(vertexG, vertexE);
        assert !unweightedDirectedGraph.isEdge(vertexG, vertexF);
        assert !unweightedDirectedGraph.isEdge(vertexG, vertexG);
        assert !unweightedDirectedGraph.isEdge(vertexG, vertexH);
        assert !unweightedDirectedGraph.isEdge(vertexH, vertexA);
        assert !unweightedDirectedGraph.isEdge(vertexH, vertexB);
        assert !unweightedDirectedGraph.isEdge(vertexH, vertexC);
        assert !unweightedDirectedGraph.isEdge(vertexH, vertexD);
        assert unweightedDirectedGraph.isEdge(vertexH, vertexE);
        assert !unweightedDirectedGraph.isEdge(vertexH, vertexF);
        assert !unweightedDirectedGraph.isEdge(vertexH, vertexG);
        assert !unweightedDirectedGraph.isEdge(vertexH, vertexH);

        unweightedDirectedGraph.removeEdge(vertexE, vertexG);
        assert !unweightedDirectedGraph.isEdge(vertexE, vertexA);
        assert !unweightedDirectedGraph.isEdge(vertexE, vertexB);
        assert !unweightedDirectedGraph.isEdge(vertexE, vertexC);
        assert !unweightedDirectedGraph.isEdge(vertexE, vertexD);
        assert !unweightedDirectedGraph.isEdge(vertexE, vertexE);
        assert unweightedDirectedGraph.isEdge(vertexE, vertexF);
        assert !unweightedDirectedGraph.isEdge(vertexE, vertexG);
        assert !unweightedDirectedGraph.isEdge(vertexE, vertexH);
        assert !unweightedDirectedGraph.isEdge(vertexG, vertexA);
        assert !unweightedDirectedGraph.isEdge(vertexG, vertexB);
        assert !unweightedDirectedGraph.isEdge(vertexG, vertexC);
        assert !unweightedDirectedGraph.isEdge(vertexG, vertexD);
        assert !unweightedDirectedGraph.isEdge(vertexG, vertexE);
        assert !unweightedDirectedGraph.isEdge(vertexG, vertexF);
        assert !unweightedDirectedGraph.isEdge(vertexG, vertexG);
        assert !unweightedDirectedGraph.isEdge(vertexG, vertexH);
    }
}
