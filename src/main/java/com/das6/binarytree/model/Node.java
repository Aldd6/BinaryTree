package com.das6.binarytree.model;

public class Node<T extends Comparable<T>> {
    private T value;
    private int height;
    private Node<T> left;
    private Node<T> right;

    public Node(T value) {
        this.value = value;
        this.height = 1;
        this.left = null;
        this.right = null;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getLeft() { return left; }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public int getHeight() { return height; }

    public void setHeight(int height) { this.height = height; }

    @Override
    public String toString() {
        return this.value + " ";
    }
}
