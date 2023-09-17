package org.mjjaen.datastructure.trees;

import java.util.List;

public interface BinarySearchTree<T extends Comparable<T>> {
    void insert(T value);
    boolean search(T value);
    void delete(T value);

    List<T> depthFirstSearchInOrder();
    List<T> depthFirstSearchPreOrder();
    List<T> depthFirstSearchPostOrder();
    List<T> breadthFirstSearch();
}
