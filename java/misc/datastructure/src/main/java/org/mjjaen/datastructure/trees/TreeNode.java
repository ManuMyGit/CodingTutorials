package org.mjjaen.datastructure.trees;

import java.util.Comparator;

public interface TreeNode<T extends Comparable<T>> {
    T getValue();
    void setValue(T value);
    TreeNode<T> getLeftNode();
    void setLeftNode(TreeNode<T> treeNode);
    TreeNode<T> getRightNode();
    void setRightNode(TreeNode<T> treeNode);
}
