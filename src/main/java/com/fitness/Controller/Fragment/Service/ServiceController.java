package com.fitness.Controller.Fragment.Service;

import com.fitness.Controller.Controller;
import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Service;
import com.fitness.Service.Grid;
import com.fitness.Service.Work.PositionService;
import com.fitness.Service.Work.ServiceService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.util.List;

public class ServiceController extends GridPane implements Controller {
    @FXML
    private ScrollPane servicesScrollPane;
    @FXML
    private TextField serviceNameTextField;
    @FXML
    private ComboBox<Position> positionComboBox;
    @FXML
    private TextField priceTextField;
    @FXML
    private Button editButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;

    private GridPane servicesGridPane = null;

    private final byte SERVICE_PER_ROW = 3;
    private ServiceService serviceService = new ServiceService();
    private Button selectedServiceButton;
    private Service selectedService;
    private List<Service> services = serviceService.getServices();

    public ServiceController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/service/service.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    private void initGridPane(){
        servicesGridPane = new GridPane();
        servicesGridPane.setMaxWidth(800);
        servicesGridPane.setMinWidth(800);
        servicesGridPane.setPrefWidth(800);
        servicesGridPane.setVgap(20);
        servicesGridPane.setPadding(new Insets(20, 20, 20, 20));
        servicesScrollPane.setContent(servicesGridPane);

        int row = 0, col = 0;

        Grid.addColumns(servicesGridPane, SERVICE_PER_ROW);
        Grid.addRows(servicesGridPane, (int)Math.ceil(services.size() / SERVICE_PER_ROW));

        for(Service service: services){
            positionComboBox.getItems().add(service.getPosition());

            String serviceName = service.getName();

            Button serviceButton = new Button(serviceName);
            serviceButton.getStyleClass().add("service_box");
            serviceButton.setOnAction(event -> {
                if(selectedServiceButton != null)
                    selectedServiceButton.getStyleClass().remove("service_box_selected");

                selectedServiceButton = serviceButton;
                selectedService = service;

                selectedServiceButton.getStyleClass().add("service_box_selected");
                serviceNameTextField.setText(serviceName);
                positionComboBox.getSelectionModel().select(service.getPosition());
                priceTextField.setText(service.getPrice() + "");
            });

            if(col == SERVICE_PER_ROW){
                col = 0;
                row++;
            }
            servicesGridPane.add(serviceButton, col, row);
            col++;
        }
    }

    private void initListeners(){
        editButton.setOnAction(event -> {
            serviceService.edit(serviceNameTextField, positionComboBox, priceTextField);
        });
        deleteButton.setOnAction(event -> {
            serviceService.remove(selectedService);
            selectedService = null;
            selectedServiceButton = null;
            initGridPane();
        });
        addButton.setOnAction(event -> {
            serviceService.add();
        });
    }

    @Override
    public void start() {
        makeActive();
        initGridPane();
        initListeners();
    }

    @Override
    public void stop() {
        serviceNameTextField.setText("");
        positionComboBox.getItems().clear();
        priceTextField.setText("");
    }
}