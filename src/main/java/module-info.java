module com.example.truthTable {
    requires javafx.controls;
    requires javafx.fxml;



    exports com.truthTable;
    opens com.truthTable to javafx.fxml;
}