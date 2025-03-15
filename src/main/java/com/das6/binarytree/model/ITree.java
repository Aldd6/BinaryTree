package com.das6.binarytree.model;

import java.util.List;

public interface ITree<T> {
    void insert(T value);
    void delete(T value);
    boolean search(T value);
    int size();
    boolean isEmpty();
    String toString();
    List<T> iteratorInOrder();
    List<T> iteratorPreOrder();
    List<T> iteratorPostOrder();
}
