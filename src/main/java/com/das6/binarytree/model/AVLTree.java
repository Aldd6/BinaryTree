package com.das6.binarytree.model;

public class AVLTree<T extends Comparable<T>> extends BSTree<T>{

    public AVLTree() {
        super();
    }

    @Override
    public void insert(T value) {
        if(search(value) == null) {
            root = recursiveInsertValue(this.root, value);
        }
    }

    @Override
    public void delete(T value) { root = recursiveDeleteValue(this.root, value); }

    private Node<T> recursiveInsertValue(Node<T> node, T value) {
        if(isEmptyThe(node)) {
            count++;
            return new Node<>(value);
        }
        if(isMoreThan(node.getValue(), value)) {
            node.setLeft(recursiveInsertValue(node.getLeft(), value));
        }else if(isLessThan(node.getValue(), value)) {
            node.setRight(recursiveInsertValue(node.getRight(), value));
        }else {
            return node;
        }
        updateHeight(node);
        return applyRotation(node);
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
        updateHeight(node);
        return applyRotation(node);
    }

    private Node<T> applyRotation(Node<T> node) {
        int ef = balance(node);
        if(ef > 1) { //left-heavy
            if(balance(node.getLeft()) < 0) {
                node.setLeft(leftRotation(node.getLeft()));
            }
            return rightRotation(node);
        }
        if(ef < -1) {//right-heavy
            if(balance(node.getRight()) > 0) {
                node.setRight(rightRotation(node.getRight()));
            }
            return leftRotation(node);
        }
        return node;
    }

    private Node<T> rightRotation(Node<T> node) {
        Node<T> leftNode = node.getLeft();
        Node<T> subRightNode = leftNode.getRight();
        leftNode.setRight(node);
        node.setLeft(subRightNode);
        updateHeight(node);
        updateHeight(subRightNode);
        return leftNode;
    }

    private Node<T> leftRotation(Node<T> node) {
        Node<T> rightNode = node.getRight();
        Node<T> subLeftNode = rightNode.getLeft();
        rightNode.setLeft(node);
        node.setRight(subLeftNode);
        updateHeight(node);
        updateHeight(subLeftNode);
        return rightNode;
    }

    private int balance(Node<T> node) {
        return node != null ? height(node.getLeft()) - height(node.getRight()) : 0;
    }

    private void updateHeight(Node<T> node) {
        if(node != null) {
            int maxHeight = Math.max(
                    height(node.getLeft()),
                    height(node.getRight())
            );
            node.setHeight(maxHeight + 1);
        }
    }

    private int height(Node<T> node) {
        return node != null ? node.getHeight():0;
    }

}
