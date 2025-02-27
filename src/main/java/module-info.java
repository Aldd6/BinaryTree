module com.das6.binarytree {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.das6.binarytree to javafx.fxml;
    exports com.das6.binarytree;
}