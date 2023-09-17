package org.mjjaen.datastructure.trees.impl;

import lombok.*;
import org.mjjaen.datastructure.trees.TreeNode;

@Data
@RequiredArgsConstructor
class TreeNodeImpl<T extends Comparable<T>> implements TreeNode<T> {
    @NonNull
    private T value;
    private TreeNode<T> leftNode;
    private TreeNode<T> rightNode;
}
