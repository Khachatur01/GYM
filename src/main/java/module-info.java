module com.fitness {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.apache.poi.poi;

    opens com.fitness.Controller to javafx.fxml;
    exports com.fitness;
}