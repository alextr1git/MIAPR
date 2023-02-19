module com.example.miaprl2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.miaprl2 to javafx.fxml;
    exports com.example.miaprl2;
}