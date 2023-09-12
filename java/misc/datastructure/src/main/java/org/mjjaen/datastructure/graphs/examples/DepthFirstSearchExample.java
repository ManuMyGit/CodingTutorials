package org.mjjaen.datastructure.graphs.examples;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.datastructure.graphs.DepthFirstSearch;
import org.mjjaen.datastructure.graphs.UnweightedGraph;
import org.mjjaen.datastructure.graphs.WeightedGraph;
import org.mjjaen.datastructure.graphs.impl.*;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DepthFirstSearchExample extends GraphExample {
    @Override
    public void run() {
        log.info("Creating general vertex ...");
        log.info("Testing DFS with adjacency matrix unweighted undirected graph...");
        UnweightedGraph<String> unweightedUndirectedGraph = new AdjacencyMatrixUnweightedGraph<>(8, false);
        prepareGraph(unweightedUndirectedGraph);
        DepthFirstSearch<String> depthFirstSearch = new DepthFirstSearchImpl<>(unweightedUndirectedGraph);
        depthFirstSearch.dfs(vertexA);
        log.info("Testing DFS with adjacency list unweighted undirected graph...");
        UnweightedGraph<String> unweightedUndirectedGraph2 = new AdjacencyListUnweightedGraph<>(8, false);
        prepareGraph(unweightedUndirectedGraph2);
        depthFirstSearch = new DepthFirstSearchImpl<>(unweightedUndirectedGraph2);
        depthFirstSearch.dfs(vertexA);
        log.info("Testing DFS with adjacency matrix unweighted directed graph...");
        UnweightedGraph<String> unweightedDirectedGraph = new AdjacencyMatrixUnweightedGraph<>(8, true);
        prepareGraph(unweightedDirectedGraph);
        depthFirstSearch = new DepthFirstSearchImpl<>(unweightedDirectedGraph);
        depthFirstSearch.dfs(vertexA);
        log.info("Testing DFS with adjacency list unweighted directed graph...");
        UnweightedGraph<String> unweightedDirectedGraph2 = new AdjacencyListUnweightedGraph<>(8, true);
        prepareGraph(unweightedDirectedGraph2);
        depthFirstSearch = new DepthFirstSearchImpl<>(unweightedDirectedGraph2);
        depthFirstSearch.dfs(vertexA);

        log.info("Testing DFS with adjacency matrix weighted undirected graph ...");
        WeightedGraph<String> weightedUndirectedGraph = new AdjacencyMatrixWeightedGraph<>(8, false);
        prepareWeightedGraph(weightedUndirectedGraph);
        depthFirstSearch = new DepthFirstSearchImpl<>(weightedUndirectedGraph);
        depthFirstSearch.dfs(vertexA);
        log.info("Testing DFS with adjacency list weighted undirected graph ...");
        WeightedGraph<String> weightedUndirectedGraph2 = new AdjacencyListWeightedGraph<>(8, false);
        prepareWeightedGraph(weightedUndirectedGraph2);
        depthFirstSearch = new DepthFirstSearchImpl<>(weightedUndirectedGraph2);
        depthFirstSearch.dfs(vertexA);
        log.info("Testing DFS with adjacency matrix weighted directed graph ...");
        WeightedGraph<String> weightedDirectedGraph = new AdjacencyMatrixWeightedGraph<>(8, true);
        prepareWeightedGraph(weightedDirectedGraph);
        depthFirstSearch = new DepthFirstSearchImpl<>(weightedDirectedGraph);
        depthFirstSearch.dfs(vertexA);
        log.info("Testing DFS with adjacency list weighted directed graph ...");
        WeightedGraph<String> weightedDirectedGraph2 = new AdjacencyListWeightedGraph<>(8, true);
        prepareWeightedGraph(weightedDirectedGraph2);
        depthFirstSearch = new DepthFirstSearchImpl<>(weightedDirectedGraph2);
        depthFirstSearch.dfs(vertexA);
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

    private void prepareWeightedGraph(WeightedGraph<String> weightedDirectedGraph) {
        weightedDirectedGraph.addVertex(vertexA);
        weightedDirectedGraph.addVertex(vertexB);
        weightedDirectedGraph.addVertex(vertexC);
        weightedDirectedGraph.addVertex(vertexD);
        weightedDirectedGraph.addVertex(vertexE);
        weightedDirectedGraph.addVertex(vertexF);
        weightedDirectedGraph.addVertex(vertexG);
        weightedDirectedGraph.addVertex(vertexH);
        weightedDirectedGraph.addEdge(vertexA, vertexB, 5);
        weightedDirectedGraph.addEdge(vertexB, vertexC, 10);
        weightedDirectedGraph.addEdge(vertexB, vertexH, 15);
        weightedDirectedGraph.addEdge(vertexC, vertexE, 20);
        weightedDirectedGraph.addEdge(vertexC, vertexD, 25);
        weightedDirectedGraph.addEdge(vertexH, vertexE, 30);
        weightedDirectedGraph.addEdge(vertexE, vertexF, 35);
        weightedDirectedGraph.addEdge(vertexE, vertexG, 40);
    }
}
