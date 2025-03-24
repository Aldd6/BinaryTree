package com.das6.binarytree.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FileDownloader {

    public boolean save(ITree<?> bst, int dataType, Tree treeType, File url) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(url));
            bw.write(dataTypeInFile(dataType) + "\n");
            bw.write(treeTypeInFile(treeType) + "\n");
            bw.write("DATASET " + constructDataSetOf(bst).toString().replaceAll("\\s+","") + "\n");
            bw.close();
            return true;
        }catch(IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<?> constructDataSetOf(ITree<?> bst) {
        return bst.iteratorLevelOrder().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private String dataTypeInFile(int dataType) {
        return switch(dataType) {
            case 1 -> "CREATE INTEGER";
            case 2 -> "CREATE DOUBLE";
            case 3 -> "CREATE STRING";
            case 4 -> "CREATE CHARACTER";
            default -> "";
        };
    }

    private String treeTypeInFile(Tree treeType) {
        return switch (treeType) {
            case BST -> "TYPE BST";
            case AVL -> "TYPE AVL";
        };
    }
}
