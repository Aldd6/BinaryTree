package com.das6.binarytree.controller;

import com.das6.binarytree.model.ITree;
import com.das6.binarytree.model.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SearchController {
    @FXML
    private TextField txtNodeValue;
    @FXML
    private Label lblDataType;

    public Node<?> searchNode(ITree bst, int dataType) {
        String baseValue = txtNodeValue.getText();
        return switch (dataType) {
            case 1 -> bst.search(Integer.parseInt(baseValue));
            case 2 -> bst.search(Double.parseDouble(baseValue));
            case 3 -> bst.search(baseValue);
            case 4 -> bst.search(baseValue.charAt(0));
            default -> null;
        };
    }

    public Object valueSearched(int dataType) {
        String baseValue = txtNodeValue.getText();
        Object found = false;
        switch(dataType) {
            case 1 -> found = Integer.parseInt(baseValue);
            case 2 -> found = Double.parseDouble(baseValue);
            case 3 -> found = baseValue;
            case 4 -> found = baseValue.charAt(0);
        }
        return found;
    }

    public void setLblDataType(String dataType) {
        lblDataType.setText(dataType);
    }

}
