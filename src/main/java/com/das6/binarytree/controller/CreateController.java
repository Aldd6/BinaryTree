package com.das6.binarytree.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import com.das6.binarytree.model.ITree;

public class CreateController {
    @FXML
    private TextField txtNodeValue;
    @FXML
    private Label lblDataType;

    public void createNode(ITree bst, int dataType) {
        String baseValue = txtNodeValue.getText();
        switch(dataType) {
            case 1 -> bst.insert(Integer.parseInt(baseValue));
            case 2 -> bst.insert(Double.parseDouble(baseValue));
            case 3 -> bst.insert(baseValue);
            case 4 -> bst.insert(baseValue.charAt(0));
        }
    }

    public void setLblDataType(String dataType) {
        lblDataType.setText(dataType);
    }
}
