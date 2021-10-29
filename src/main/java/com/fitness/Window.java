package com.fitness;

import com.fitness.Controller.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.Controller.Fragment.Customer.AddCustomerController;
import com.fitness.Controller.Fragment.Customer.CustomerController;
import com.fitness.Controller.Fragment.Customer.EditCustomerController;
import com.fitness.Controller.Fragment.Employee.AddEmployeeController;
import com.fitness.Controller.Fragment.Employee.EditEmployeeController;
import com.fitness.Controller.Fragment.Employee.EmployeeController;
import com.fitness.Controller.Fragment.Enter.WithCardController;
import com.fitness.Controller.Fragment.Enter.WithoutCardController;
import com.fitness.Controller.Fragment.Report.ReportController;
import com.fitness.Controller.Fragment.Service.ServiceController;
import com.fitness.Controller.Fragment.SettingsController;
import com.fitness.Controller.Fragment.Subscription.SubscriptionController;
import com.fitness.Style.ButtonStyle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Window {
    private static Button activeButton;
    private static Pane activePane;
    private static Map<Fragment, Controller> controllers = new HashMap<>();

    public void close(Stage stage){
        stage.close();
    }

    public Stage open(String url, Stage stage) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(parent));
        stage.show();
        return stage;
    }

    private static void changeActiveButton(Button selected){
        ButtonStyle.unselect(Window.activeButton);
        ButtonStyle.select(selected);
        Window.activeButton = selected;
    }

    public void initMenuPanes() throws IOException {
        controllers.put(Fragment.WITH_CARD, new WithCardController());
        controllers.put(Fragment.WITHOUT_CARD, new WithoutCardController());
        controllers.put(Fragment.CUSTOMER, new CustomerController());
        controllers.put(Fragment.EDIT_CUSTOMER, new EditCustomerController());
        controllers.put(Fragment.ADD_CUSTOMER, new AddCustomerController());
        controllers.put(Fragment.EMPLOYEE, new EmployeeController());
        controllers.put(Fragment.EDIT_EMPLOYEE, new EditEmployeeController());
        controllers.put(Fragment.ADD_EMPLOYEE, new AddEmployeeController());
        controllers.put(Fragment.SERVICE, new ServiceController());
        controllers.put(Fragment.SUBSCRIPTION, new SubscriptionController());
        controllers.put(Fragment.REPORT, new ReportController());
        controllers.put(Fragment.SETTINGS, new SettingsController());
    }

    public static void setActivePane(Pane activePane){
        Window.activePane = activePane;
    }

    private static Controller onlyGetFragment(Fragment fragment){
        Controller pane = controllers.get(fragment);
        activePane.getChildren().setAll((Pane)pane);
        return pane;
    }
    public static Controller getFragment(Fragment fragment, Button selected){
        changeActiveButton(selected);
        return onlyGetFragment(fragment);
    }
    public static Controller getFragment(Fragment fragment){
        return onlyGetFragment(fragment);
    }
}
