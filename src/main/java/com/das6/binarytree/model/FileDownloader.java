package com.das6.binarytree.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FileDownloader {

    public boolean save(BSTree<?> bst, int dataType, File url) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(url));
            bw.write(dataTypeInFile(dataType) + "\n");
            bw.write("DATASET " + constructDataSetOf(bst).toString().replaceAll("\\s+","") + "\n");
            bw.close();
            return true;
        }catch(IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<?> constructDataSetOf(BSTree<?> bst) {
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
}
