package org.mjjaen.datastructure.trees.examples;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.datastructure.trees.BinarySearchTree;
import org.mjjaen.datastructure.trees.impl.BinarySearchTreeImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BinarySearchTreeExample extends Example {
    @Override
    public void run() {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTreeImpl<>();
        binarySearchTree.insert(10);
        binarySearchTree.insert(5);
        binarySearchTree.insert(15);
        binarySearchTree.insert(3);
        binarySearchTree.insert(7);
        binarySearchTree.insert(12);
        binarySearchTree.insert(17);

        List<Integer> depthFirstSearchInOrder = binarySearchTree.depthFirstSearchInOrder();
        assert depthFirstSearchInOrder.get(0) == 3;
        assert depthFirstSearchInOrder.get(1) == 5;
        assert depthFirstSearchInOrder.get(2) == 7;
        assert depthFirstSearchInOrder.get(3) == 10;
        assert depthFirstSearchInOrder.get(4) == 12;
        assert depthFirstSearchInOrder.get(5) == 15;
        assert depthFirstSearchInOrder.get(6) == 17;
        List<Integer> depthFirstSearchPreOrder = binarySearchTree.depthFirstSearchPreOrder();
        assert depthFirstSearchPreOrder.get(0) == 10;
        assert depthFirstSearchPreOrder.get(1) == 5;
        assert depthFirstSearchPreOrder.get(2) == 3;
        assert depthFirstSearchPreOrder.get(3) == 7;
        assert depthFirstSearchPreOrder.get(4) == 15;
        assert depthFirstSearchPreOrder.get(5) == 12;
        assert depthFirstSearchPreOrder.get(6) == 17;
        List<Integer> depthFirstSearchPostOrder = binarySearchTree.depthFirstSearchPostOrder();
        assert depthFirstSearchPostOrder.get(0) == 3;
        assert depthFirstSearchPostOrder.get(1) == 7;
        assert depthFirstSearchPostOrder.get(2) == 5;
        assert depthFirstSearchPostOrder.get(3) == 12;
        assert depthFirstSearchPostOrder.get(4) == 17;
        assert depthFirstSearchPostOrder.get(5) == 15;
        assert depthFirstSearchPostOrder.get(6) == 10;
        List<Integer> breadthFirstSearch = binarySearchTree.breadthFirstSearch();
        assert breadthFirstSearch.get(0) == 10;
        assert breadthFirstSearch.get(1) == 5;
        assert breadthFirstSearch.get(2) == 15;
        assert breadthFirstSearch.get(3) == 3;
        assert breadthFirstSearch.get(4) == 7;
        assert breadthFirstSearch.get(5) == 12;
        assert breadthFirstSearch.get(6) == 17;
    }
}