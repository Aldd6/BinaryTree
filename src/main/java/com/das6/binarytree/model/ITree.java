package com.das6.binarytree.model;

public interface ITree<T> {
    void insert(T value);
    void delete(T value);
    boolean contains(T value);
    void removeRightSubTree();
    void removeLeftSubTree();
    void removeAllElements();
    int size();
    boolean isEmpty();
    String toString();
    void iteratorInOrder();
    void iteratorPreOrder();
    void iteratorPostOrder();
    void iteratorLevelOrder();
}
