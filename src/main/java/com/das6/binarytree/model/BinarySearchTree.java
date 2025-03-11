package com.das6.binarytree.model;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> implements ITree<T>{

    private Node<T> root;
    private List<T> cachedData;
    private int count;


    public BinarySearchTree() {
        root = null;
        cachedData = new LinkedList<>();
        count = 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(T value) {
        return !cachedData.isEmpty() && cachedData.contains(value);
    }

    @Override
    public void insert(T value) {
        if(!contains(value)) {
            root = recursiveInsertValue(this.root, value);
        }
    }

    private Node<T> recursiveInsertValue(Node<T> node, T value) {
        if(isEmptyThis(node)) {
            return new Node<>(value);
        }
        if(isLessThan(node.getValue(), value)) {
            node.setLeft(recursiveInsertValue(node.getLeft(), value));
        }
        if(isMoreThan(node.getValue(), value)) {
            node.setRight(recursiveInsertValue(node.getRight(), value));
        }
        return node;
    }

    private boolean isEmptyThis(Node<T> node) {
        return node == null;
    }

    private boolean isLessThan(T node, T value) {
        return node.compareTo(value) < 0;
    }

    private boolean isMoreThan(T node, T value) {
        return node.compareTo(value) > 0;
    }

}
