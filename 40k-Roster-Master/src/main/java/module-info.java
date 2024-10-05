module org.example.w40krostermaster {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.w40krostermaster to javafx.fxml;
    exports org.example.w40krostermaster;
}