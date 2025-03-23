package com.das6.binarytree.controller;

import com.das6.binarytree.model.BSTree;
import com.das6.binarytree.model.FileDownloader;
import com.das6.binarytree.model.FileUploader;
import javafx.animation.PauseTransition;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import com.das6.binarytree.model.Node;
import javafx.scene.text.Text;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class TreeController implements Initializable{
    @FXML
    private Pane drawField;
    @FXML
    private ChoiceBox<String> cbDataType;
    @FXML
    private ChoiceBox<String> cbTraverseTree;
    @FXML
    private Button btnResetAnimation;
    @FXML
    private TextField txtOrder;


    private BSTree bst;
    private String[] dataTypes = {"Integer", "Double", "String", "Character"};
    private String[] traverseOrders = {"Visualize Pre-Order", "Visualize In-Order","Visualize Post-Order"};
    private Map<String, Circle> treeNodes;
    private int dataType = -1;
    private double radius = 30;
    private double vGap = 100;
    private FileChooser fileChooser;
    private FileUploader fp;
    private FileDownloader fd;

    @FXML
    public void resetTree(ActionEvent event) {
        txtOrder.setText("");
        btnResetAnimation.setOpacity(0);
        btnResetAnimation.setVisible(true);
        cbTraverseTree.setValue("Select Traverse Order");
        cbTraverseTree.setDisable(false);
        displayTree();
    }

    @FXML
    public void uploadFile(ActionEvent event) {
        Stage primaryStage = (Stage)drawField.getScene().getWindow();
        cbDataType.setDisable(true);
        File url = fileChooser.showOpenDialog(primaryStage);
        if(url != null) {
            fp.load(url);
            dataType = fp.getDataType();
            if(dataType > 0) {
                switch (dataType) {
                    case 1 -> cbDataType.setValue("Integer");
                    case 2 -> cbDataType.setValue("Double");
                    case 3 -> cbDataType.setValue("String");
                    case 4 -> cbDataType.setValue("Character");
                }
                cbDataType.setDisable(false);
                switchDataType();
                fp.getDataCollected().forEach(item -> {
                    switch(dataType) {
                        case 1 -> bst.insert(Integer.parseInt(item.toString()));
                        case 2 -> bst.insert(Double.parseDouble(item.toString()));
                        case 3 -> bst.insert(item.toString());
                        case 4 -> bst.insert(item.toString().charAt(0));
                    }
                });
                displayTree();
            }

        }
        cbDataType.setDisable(false);
    }

    @FXML
    public void downloadFile(ActionEvent event) {
        Stage primaryStage = (Stage)drawField.getScene().getWindow();
        Alert alert;
        if(bst != null && bst.getRoot() != null) {
            File url = saveFile(primaryStage);
            boolean isFileSaved = fd.save(bst,dataType,url);
            if(isFileSaved) {
                alert = new Alert(AlertType.INFORMATION);
                alert.initOwner(primaryStage);
                alert.setTitle("File Saved");
                alert.setHeaderText("File Saved");
                alert.setContentText("The Tree has been saved successfully.");
                alert.showAndWait();
            }else {
                alert = new Alert(AlertType.ERROR);
                alert.initOwner(primaryStage);
                alert.setTitle("File Not Saved");
                alert.setHeaderText("File Not Saved");
                alert.setContentText("It has occurred an error, please try again.");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        treeNodes = new HashMap<>();
        btnResetAnimation.setOpacity(0);
        btnResetAnimation.setVisible(false);
        txtOrder.setDisable(true);
        fileChooser = new FileChooser();
        fp = new FileUploader();
        fd = new FileDownloader();
        ContextMenu opForBst = new ContextMenu();
        MenuItem create = new MenuItem("Create");
        create.setOnAction(e -> {
            try {
                Stage primaryStage = (Stage)drawField.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/das6/binarytree/CreateNode.fxml"));
                DialogPane treeDialog = fxmlLoader.load();

                CreateController createController = fxmlLoader.getController();
                createController.setLblDataType(cbDataType.getValue());

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(primaryStage);
                dialog.setDialogPane(treeDialog);
                dialog.setTitle("Create a Node");

                Optional<ButtonType> clickedButton = dialog.showAndWait();
                if (clickedButton.get() == ButtonType.OK && dataType != -1) {
                    System.out.println("Inserting a node...");
                    createController.createNode(bst,dataType);
                    displayTree();
                }else {
                    displayInformationDialog(primaryStage,AlertType.ERROR,"The node could not be inserted");
                    System.out.println("Cannot insert a node...");
                }
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        });
        MenuItem search = new MenuItem("Search");
        search.setOnAction(e -> {
            try {
                Stage primaryStage = (Stage)drawField.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("/com/das6/binarytree/SearchNode.fxml")));
                DialogPane treeDialog = fxmlLoader.load();

                SearchController searchController = fxmlLoader.getController();
                searchController.setLblDataType(cbDataType.getValue());

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(primaryStage);
                dialog.setDialogPane(treeDialog);
                dialog.setTitle("Search a Node");

                Optional<ButtonType> clickedButton = dialog.showAndWait();
                if (clickedButton.get() == ButtonType.OK && dataType != -1) {
                    System.out.println("Searching for a node...");
                    Node target = searchController.searchNode(bst, dataType);
                    if(target != null) {
                        System.out.println("Node found...");
                        displayRecursiveValueSearch(bst.getRoot(),drawField.getWidth()/2,vGap,drawField.getWidth()/4, target);
                        int lvlNode = bst.nodeLevel(bst.getRoot(), target.getValue(),0);
                        displayInformationDialog(primaryStage,AlertType.INFORMATION,"Node found in level: " + lvlNode);
                    }else {
                        System.out.println("Node not found...");
                        displayTree();
                        displayInformationDialog(primaryStage,AlertType.ERROR,"Node not found");
                    }
                }else {
                    System.out.println("Cannot search for a node...");
                    displayInformationDialog(primaryStage,AlertType.ERROR,"The node could not be searched");
                }
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        });
        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(e -> {
            try {
                Stage primaryStage = (Stage)drawField.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/das6/binarytree/DeleteNode.fxml"));
                DialogPane treeDialog = fxmlLoader.load();

                DeleteController deleteController = fxmlLoader.getController();
                deleteController.setLblDataType(cbDataType.getValue());

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(primaryStage);
                dialog.setDialogPane(treeDialog);
                dialog.setTitle("Delete a Node");

                Optional<ButtonType> clickedButton = dialog.showAndWait();
                if(clickedButton.get() == ButtonType.OK && dataType != -1) {
                    System.out.println("Deleting a node...");
                    deleteController.deleteNode(bst,dataType);
                    displayTree();
                }else {
                    displayInformationDialog(primaryStage,AlertType.ERROR,"The node could not be deleted");
                    System.out.println("Cannot delete the node...");
                }
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        });
        opForBst.getItems().addAll(create, search, delete);

        cbDataType.getItems().addAll(dataTypes);
        cbDataType.setValue("Select Data Type");
        cbDataType.setOnAction(e -> switchDataType());

        cbTraverseTree.getItems().addAll(traverseOrders);
        cbTraverseTree.setValue("Select Traverse Order");
        cbTraverseTree.setOnAction(e -> traverseTree());

        drawField.setOnContextMenuRequested(e -> {
            opForBst.show(drawField, e.getScreenX(), e.getScreenY());
        });
    }

    public void switchDataType() {
        if(!cbDataType.isDisabled()){
            String op = cbDataType.getValue();
            switch (op) {
                case "Integer":
                    bst = new BSTree<Integer>();
                    dataType = 1;
                    System.out.println("Creating Integer BST...");
                    break;
                case "Double":
                    bst = new BSTree<Double>();
                    dataType = 2;
                    System.out.println("Creating Double BST...");
                    break;
                case "String":
                    bst = new BSTree<String>();
                    dataType = 3;
                    System.out.println("Creating String BST...");
                    break;
                case "Character":
                    bst = new BSTree<Character>();
                    dataType = 4;
                    System.out.println("Creating Character BST...");
                    break;
                default:
                    System.out.println("Cannot create a null BST...");
                    dataType = -1;
            }
            displayTree();
        }
    }

    public void traverseTree() {
        if(bst != null) {
            String order = cbTraverseTree.getValue();
            List<?> orderArray;
            switch (order) {
                case "Visualize Pre-Order":
                    orderArray = bst.iteratorPreOrder();
                    displayRecursiveOrder(orderArray,0);
                    displayResetButton();
                    showOrderArray(orderArray);
                    cbTraverseTree.setDisable(true);
                    break;
                case "Visualize In-Order":
                    orderArray = bst.iteratorInOrder();
                    displayRecursiveOrder(orderArray,0);
                    displayResetButton();
                    showOrderArray(orderArray);
                    cbTraverseTree.setDisable(true);
                    break;
                case "Visualize Post-Order":
                    orderArray = bst.iteratorPostOrder();
                    displayRecursiveOrder(orderArray,0);
                    displayResetButton();
                    showOrderArray(orderArray);
                    cbTraverseTree.setDisable(true);
                    break;
                default:
                    System.out.println("Option not recognized...");
                    break;
            }
        } else {
            System.out.println("Cannot traverse a null tree...");
        }
    }

    private void displayResetButton() {
        btnResetAnimation.setOpacity(100);
        btnResetAnimation.setVisible(true);
    }

    private void displayInformationDialog(Stage primaryStage, AlertType type, String info) {
        Alert alert;
        switch(type) {
            case INFORMATION:
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(primaryStage);
                alert.setTitle("Information");
                alert.setHeaderText("Atención");
                alert.setContentText(info);
                alert.showAndWait();
                break;
            case ERROR:
                alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(primaryStage);
                alert.setTitle("Error");
                alert.setHeaderText("Atención");
                alert.setContentText(info);
                alert.showAndWait();
                break;

        }
    }

    private void showOrderArray(List<?> order) {
        txtOrder.setText(order.toString());
    }

    public void displayTree() {
        drawField.getChildren().clear();
        if(bst.getRoot() != null) {
            displayRecursiveTree(bst.getRoot(),drawField.getWidth()/2,vGap,drawField.getWidth()/4);
        }
    }


    private void displayRecursiveTree(Node root, double x, double y, double hGap){
        if(root.getLeft() != null){
            drawField.getChildren().add(new Line(x-hGap, y+vGap, x, y));
            displayRecursiveTree(root.getLeft(), x-hGap, y+vGap, hGap/2);
        }
        if(root.getRight() != null){
            drawField.getChildren().add(new Line(x+hGap, y+vGap, x, y));
            displayRecursiveTree(root.getRight(), x+hGap, y+vGap, hGap/2);
        }
        Circle circle = new Circle(x, y, radius);
        Text txt = new Text(x-10, y+6, root.getValue().toString());
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        treeNodes.put(txt.getText(),circle);
        drawField.getChildren().addAll(circle, txt);
    }

    private void displayRecursiveValueSearch(Node root, double x, double y, double hGap, Node<?> target){
        if(root.getLeft() != null){
            drawField.getChildren().add(new Line(x-hGap, y+vGap, x, y));
            displayRecursiveValueSearch(root.getLeft(), x-hGap, y+vGap, hGap/2, target);
        }
        if(root.getRight() != null){
            drawField.getChildren().add(new Line(x+hGap, y+vGap, x, y));
            displayRecursiveValueSearch(root.getRight(), x+hGap, y+vGap, hGap/2, target);
        }
        Circle circle = new Circle(x, y, radius);
        Text txt = new Text(x-10, y+6, root.getValue().toString());
        if(target.equals(root)) {
            circle.setFill(Color.SKYBLUE);
            circle.setStroke(Color.SKYBLUE);
        }else {
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
        }
        treeNodes.put(txt.getText(),circle);
        drawField.getChildren().addAll(circle, txt);
    }

    private void displayRecursiveOrder(List<?> order, int index){
        if(index < order.size()) {
            Object value = order.get(index);
            if(treeNodes.containsKey(value.toString())){
                Circle circle = treeNodes.get(value.toString());
                PauseTransition animation = new PauseTransition(Duration.seconds(1));
                animation.setOnFinished(event -> {
                    circle.setFill(Color.SKYBLUE);
                    circle.setStroke(Color.SKYBLUE);
                    displayRecursiveOrder(order, index+1);
                });
                System.out.println("new animation created");
                animation.play();
            }else {
                displayRecursiveOrder(order, index+1);
            }
        }
    }

    private File saveFile(Stage primaryStage) {
        fileChooser.setTitle("Save Tree");
        fileChooser.setInitialFileName("newTree.txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File url = fileChooser.showSaveDialog(primaryStage);

        if(url != null) {
            try{
                if(url.createNewFile()) {
                    System.out.println("File created: " + url.getAbsolutePath());
                }else {
                    System.out.println("File already exists: " + url.getAbsolutePath());
                }
            }catch(IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("No file selected");
        }

        return url;
    }
}
