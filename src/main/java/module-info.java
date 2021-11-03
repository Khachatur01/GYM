module com.fitness {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.apache.poi.poi;

    opens com.fitness.Model.Person to javafx.base;
    opens com.fitness.Model.Work to javafx.base;
    opens com.fitness.Model.Configuration to javafx.base;

    opens com.fitness.Controller to javafx.fxml;
    opens com.fitness.Controller.Fragment to javafx.fxml;
    opens com.fitness.Controller.Fragment.Customer to javafx.fxml;
    opens com.fitness.Controller.Fragment.Employee to javafx.fxml;
    opens com.fitness.Controller.Fragment.Enter to javafx.fxml;
    opens com.fitness.Controller.Fragment.Archive to javafx.fxml;
    opens com.fitness.Controller.Fragment.Employment to javafx.fxml;
    opens com.fitness.Controller.Fragment.Position to javafx.fxml;
    opens com.fitness.Controller.Fragment.Subscription to javafx.fxml;
    exports com.fitness;
}