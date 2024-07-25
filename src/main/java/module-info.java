module com.example.demo4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens com.example.demo4 to javafx.fxml;
    opens com.example.demo4.services.taskServices to javafx.base;
    exports com.example.demo4;
}