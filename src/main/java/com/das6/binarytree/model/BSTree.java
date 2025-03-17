package com.das6.binarytree.model;
import java.util.LinkedList;
import java.util.List;

public class BSTree<T extends Comparable<T>> implements ITree<T>{

    private Node<T> root;
    private int count;


    public BSTree() {
        root = null;
        count = 0;
    }

    public Node<T> getRoot() {
        return this.root;
    }

    public int getMaxDepth(Node<T> node) {
        if(node == null) {
            return 0;
        }
        return 1 + Math.max(getMaxDepth(node.getLeft()), getMaxDepth(node.getRight()));
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
    public Node<T> search(T value) {
        return recursiveSearchValue(root, value);
    }

    @Override
    public void insert(T value) {
        if(search(value) == null) {
            root = recursiveInsertValue(this.root, value);
        }
    }

    @Override
    public void delete(T value) {
        root = recursiveDeleteValue(this.root, value);
    }

    @Override
    public List<T> iteratorInOrder() {
        List<T> path = new LinkedList<>();
        inOrderTraversal(this.root, path);
        return path;
    }

    @Override
    public List<T> iteratorPreOrder() {
        List<T> path = new LinkedList<>();
        preOrderTraversal(this.root, path);
        return path;
    }

    @Override
    public List<T> iteratorPostOrder() {
        List<T> path = new LinkedList<>();
        postOrderTraversal(this.root, path);
        return path;
    }

    @Override
    public String toString() {
        return iteratorPostOrder().toString();
    }

    private void inOrderTraversal(Node<T> root, List<T> path) {
        if(!isEmptyThe(root)) {
            inOrderTraversal(root.getLeft(), path);
            path.add(root.getValue());
            inOrderTraversal(root.getRight(), path);
        }
    }

    private void preOrderTraversal(Node<T> root, List<T> path) {
        if(!isEmptyThe(root)) {
            path.add(root.getValue());
            preOrderTraversal(root.getLeft(), path);
            preOrderTraversal(root.getRight(), path);
        }
    }

    private void postOrderTraversal(Node<T> root, List<T> path) {
        if(!isEmptyThe(root)) {
            postOrderTraversal(root.getLeft(), path);
            postOrderTraversal(root.getRight(), path);
            path.add(root.getValue());
        }
    }

    private Node<T> recursiveSearchValue(Node<T> node, T value) {
        if(isEmptyThe(node)) {
            return null;
        }
        if(isLessThan(node.getValue(), value)) {
            return recursiveSearchValue(node.getRight(), value);
        }else if(isMoreThan(node.getValue(), value)) {
            return recursiveSearchValue(node.getLeft(), value);
        }
        return node;
    }

    private Node<T> recursiveInsertValue(Node<T> node, T value) {
        if(isEmptyThe(node)) {
            count++;
            return new Node<>(value);
        }
        if(isMoreThan(node.getValue(), value)) {
            node.setLeft(recursiveInsertValue(node.getLeft(), value));
        }
        if(isLessThan(node.getValue(), value)) {
            node.setRight(recursiveInsertValue(node.getRight(), value));
        }
        return node;
    }

    private Node<T> recursiveDeleteValue(Node<T> node, T value) {
        if(isEmptyThe(node)) {
            return null;
        }
        if(isMoreThan(node.getValue(), value)) {
            node.setLeft(recursiveDeleteValue(node.getLeft(), value));
        }else if(isLessThan(node.getValue(), value)) {
            node.setRight(recursiveDeleteValue(node.getRight(), value));
        } else {
            if(isEmptyThe(node.getLeft()) && isEmptyThe(node.getRight())) {
                return null;
            }
            if(isEmptyThe(node.getLeft())) {
                return node.getRight();
            } else if(isEmptyThe(node.getRight())) {
                return node.getLeft();
            }
            node.setValue(obtainMinimun(node.getRight()));
            node.setRight(recursiveDeleteValue(node.getRight(), node.getValue()));
            count--;
        }
        return node;
    }

    private T obtainMinimun(Node<T> node) {
        T minimun = node.getValue();
        while(node.getLeft() != null) {
            minimun = node.getLeft().getValue();
            node = node.getLeft();
        }
        return minimun;
    }

    private boolean isEmptyThe(Node<T> node) {
        return node == null;
    }

    //Es menor el valor del nodo que el valor dado
    private boolean isLessThan(T node, T value) {
        return node.compareTo(value) < 0;
    }

    //Es mayor el valor del nodo que el valor dado
    private boolean isMoreThan(T node, T value) {
        return node.compareTo(value) > 0;
    }

}
