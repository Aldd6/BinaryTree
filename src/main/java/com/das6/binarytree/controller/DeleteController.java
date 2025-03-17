package com.das6.binarytree.controller;

import com.das6.binarytree.model.BSTree;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DeleteController {
    @FXML
    private TextField txtNodeValue;
    @FXML
    private Label lblDataType;

    public void deleteNode(BSTree bst, int dataType) {
        String baseValue = txtNodeValue.getText();
        switch (dataType) {
            case 1 -> bst.delete(Integer.parseInt(baseValue));
            case 2 -> bst.delete(Double.parseDouble(baseValue));
            case 3 -> bst.delete(baseValue);
            case 4 -> bst.delete(baseValue.charAt(0));
        }
    }

    public void setLblDataType(String dataType) {
        lblDataType.setText(dataType);
    }
}
