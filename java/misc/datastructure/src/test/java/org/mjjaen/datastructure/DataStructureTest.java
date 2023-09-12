package org.mjjaen.datastructure;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mjjaen.datastructure.graphs.examples.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DataStructureTest {
    @Autowired
    private UnweightedUndirectedGraphExample unweightedUndirectedGraphExample;

    @Autowired
    private UnweightedDirectedGraphExample unweightedDirectedGraphExample;

    @Autowired
    private WeightedUndirectedGraphExample weightedUndirectedGraphExample;

    @Autowired
    private WeightedDirectedGraphExample weightedDirectedGraphExample;

    @Autowired
    private DepthFirstSearchExample depthFirstSearchExample;

    @Autowired
    private BreadthFirstSearchExample breadthFirstSearchExample;
    @Test
    public void testAdjacencyMatrixUnweightedUndirected() {
        unweightedUndirectedGraphExample.run();
    }

    @Test
    public void testAdjacencyMatrixUnweightedDirected() {
        unweightedDirectedGraphExample.run();
    }

    @Test
    public void testAdjacencyMatrixWeightedUndirected() {
        weightedUndirectedGraphExample.run();
    }

    @Test
    public void testAdjacencyMatrixWeightedDirected() {
        weightedDirectedGraphExample.run();
    }

    @Test
    public void testDepthFirstSearch() {
        depthFirstSearchExample.run();
    }

    @Test
    public void testBreadthFirstSearch() {
        breadthFirstSearchExample.run();
    }
}
