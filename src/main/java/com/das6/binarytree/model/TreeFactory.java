package com.das6.binarytree.model;

public class TreeFactory {
    public ITree<?> createTree(Tree treeType, Data dataType) {
        if (treeType == Tree.BST) {
            return switch(dataType) {
                case INTEGER -> new BSTree<Integer>();
                case DOUBLE -> new BSTree<Double>();
                case STRING -> new BSTree<String>();
                case CHARACTER -> new BSTree<Character>();
                case null -> null;
            };
        }
        if (treeType == Tree.AVL) {
            return switch (dataType) {
                case INTEGER -> new AVLTree<Integer>();
                case DOUBLE -> new AVLTree<Double>();
                case STRING -> new AVLTree<String>();
                case CHARACTER -> new AVLTree<Character>();
                case null -> null;
            };
        }
        return null;
    }
}
