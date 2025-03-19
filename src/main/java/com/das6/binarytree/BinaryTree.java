package com.das6.binarytree;

import com.das6.binarytree.model.FileUploader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;

public class BinaryTree extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TreeUi.fxml"));
        Pane root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setFullScreen(true);
        stage.setTitle("Binary Tree");
        stage.setScene(scene);
        stage.centerOnScreen();

        stage.widthProperty().addListener((obs, oldVal, newVal) -> root.setPrefWidth(newVal.doubleValue()));
        stage.heightProperty().addListener((obs, oldVal, newVal) -> root.setPrefHeight(newVal.doubleValue()));

        stage.show();
    }

    public static void main(String[] args) { launch(); }
}