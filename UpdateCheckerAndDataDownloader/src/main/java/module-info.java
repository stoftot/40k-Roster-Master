module org.example.updatecheckeranddatadownloader {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires jdk.compiler;


    opens org.example.updatecheckeranddatadownloader to javafx.fxml;
    exports org.example.updatecheckeranddatadownloader;
}