package org.mjjaen.datastructure.trees.impl;

import lombok.ToString;
import org.mjjaen.datastructure.trees.BinarySearchTree;
import org.mjjaen.datastructure.trees.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@ToString
public class BinarySearchTreeImpl<T extends Comparable<T>> implements BinarySearchTree<T> {
    private TreeNode<T> root;

    public void insert(T value) {
        root = insertRecursive(root, value);
    }

    private TreeNode<T> insertRecursive(TreeNode<T> node, T value) {
        if (node == null)
            return new TreeNodeImpl<>(value);
        if (value.compareTo(node.getValue()) < 0)
            node.setLeftNode(insertRecursive(node.getLeftNode(), value));
        else if (value.compareTo(node.getValue()) > 0)
            node.setRightNode(insertRecursive(node.getRightNode(), value));
        return node;
    }

    public boolean search(T value) {
        return searchRecursive(root, value);
    }

    private boolean searchRecursive(TreeNode<T> node, T value) {
        if (node == null)
            return false;
        if (value == node.getValue())
            return true;
        if (value.compareTo(node.getValue()) < 0)
            return searchRecursive(node.getLeftNode(), value);
        else
            return searchRecursive(node.getRightNode(), value);
    }

    public void delete(T value) {
        root = deleteRecursive(root, value);
    }

    private TreeNode<T> deleteRecursive(TreeNode<T> node, T value) {
        if (node == null)
            return null;
        if (value == node.getValue()) {
            if (node.getLeftNode() == null && node.getRightNode() == null)
                return null;
            if (node.getRightNode() == null)
                return node.getLeftNode();
            if (node.getLeftNode() == null)
                return node.getRightNode();
            T smallestValue = findSmallestValue(node.getRightNode());
            node.setValue(smallestValue);
            node.setRightNode(deleteRecursive(node.getRightNode(), smallestValue));
            return node;
        }
        if (value.compareTo(node.getValue()) < 0) {
            node.setLeftNode(deleteRecursive(node.getLeftNode(), value));
            return node;
        }
        node.setRightNode(deleteRecursive(node.getRightNode(), value));
        return node;
    }

    private T findSmallestValue(TreeNode<T> node) {
        return node.getLeftNode() == null ? node.getValue() : findSmallestValue(node.getLeftNode());
    }

    @Override
    public List<T> depthFirstSearchInOrder() {
        List<T> list = new ArrayList<>();
        depthFirstSearchInOrder(root, list);
        return list;
    }

    private void depthFirstSearchInOrder(TreeNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        depthFirstSearchInOrder(node.getLeftNode(), list);
        list.add(node.getValue());
        depthFirstSearchInOrder(node.getRightNode(), list);

    }

    @Override
    public List<T> depthFirstSearchPreOrder() {
        List<T> list = new ArrayList<>();
        depthFirstSearchPreOrder(root, list);
        return list;
    }

    private void depthFirstSearchPreOrder(TreeNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        list.add(node.getValue());
        depthFirstSearchPreOrder(node.getLeftNode(), list);
        depthFirstSearchPreOrder(node.getRightNode(), list);
    }

    @Override
    public List<T> depthFirstSearchPostOrder() {
        List<T> list = new ArrayList<>();
        depthFirstSearchPostOrder(root, list);
        return list;
    }

    private void depthFirstSearchPostOrder(TreeNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        depthFirstSearchPostOrder(node.getLeftNode(), list);
        depthFirstSearchPostOrder(node.getRightNode(), list);
        list.add(node.getValue());
    }

    @Override
    public List<T> breadthFirstSearch() {
        return breadthFirstSearch(root, new ArrayList<>());
    }

    private List<T> breadthFirstSearch(TreeNode<T> node, List<T> list) {
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode<T> tempNode = queue.poll();
            list.add(tempNode.getValue());
            /*add left child to the queue */
            if (tempNode.getLeftNode() != null) {
                queue.add(tempNode.getLeftNode());
            }
            /*add right right child to the queue */
            if (tempNode.getRightNode() != null) {
                queue.add(tempNode.getRightNode());
            }
        }
        return list;
    }
}
