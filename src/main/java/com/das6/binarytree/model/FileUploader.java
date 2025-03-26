package com.das6.binarytree.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileUploader {
    private int dataType;
    private Tree treeType;
    private List<?> dataCollected;

    public void load(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String[] instructions;
            while((line = br.readLine()) != null) {
                if(line.charAt(0) != '#' && line.length() > 0) {
                    instructions = line.split("\\s+");
                    try {
                        Keyword key = Keyword.valueOf(instructions[0].toUpperCase());
                        switch (key) {
                            case CREATE:
                                Data data = Data.valueOf(instructions[1].toUpperCase());
                                setDataTypeInternal(data);
                                break;
                            case TYPE:
                                Tree tree = Tree.valueOf(instructions[1].toUpperCase());
                                setTreeTypeInternal(tree);
                            case DATASET:
                                String dataDepured = instructions[1].replaceAll("[\\[\\](){}]","");
                                dataCollected = Arrays.asList(dataDepured.split(","));
                                break;
                            default:
                                dataType = -1;
                                dataCollected = null;
                        }
                    }catch(IllegalArgumentException e){
                        e.printStackTrace();
                    }
                }
            }
            br.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public int getDataType() {
        return dataType;
    }

    public List<?> getDataCollected() {
        return dataCollected;
    }

    public Tree getTreeType() {
        return treeType;
    }

    private void setDataTypeInternal(Data data) {
        switch (data) {
            case INTEGER:
                dataType = 1;
                break;
            case DOUBLE:
                dataType = 2;
                break;
            case STRING:
                dataType = 3;
                break;
            case CHARACTER:
                dataType = 4;
                break;
            default:
                dataType = -1;
        }
    }

    private void setTreeTypeInternal(Tree tree) {
        treeType = tree;
    }
}
