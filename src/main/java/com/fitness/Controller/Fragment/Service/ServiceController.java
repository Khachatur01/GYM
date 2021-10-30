package com.fitness.Controller.Fragment.Service;

import com.fitness.Controller.Controller;
import com.fitness.Element.ServiceButton;
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
import java.util.ArrayList;
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

    private int row = 0, col = 0;
    private final byte SERVICE_PER_ROW = 3;
    private ServiceService serviceService = new ServiceService();
    private List<ServiceButton> serviceButtons = new ArrayList<>();

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

        List<Service> services = serviceService.getServices();
        Grid.addColumns(servicesGridPane, SERVICE_PER_ROW);
        Grid.addRows(servicesGridPane, (int)Math.ceil(services.size() / SERVICE_PER_ROW));

        for(Service service: services){
            addServiceToGrid(service);
        }
    }

    private void initListeners(){
        editButton.setOnAction(event -> {
            Service service = serviceService.edit(serviceNameTextField, positionComboBox, priceTextField);
            ServiceButton.editSelected(service);
        });
        deleteButton.setOnAction(event -> {
            serviceService.remove(ServiceButton.getSelected().getService());
            ServiceButton.removeSelected();
            initGridPane();
        });
        addButton.setOnAction(event -> {
            Service service = serviceService.add();
            if(service != null)
                addServiceToGrid(service);
        });
    }

    private void addServiceToGrid(Service service){
        positionComboBox.getItems().add(service.getPosition());

        ServiceButton serviceButton = new ServiceButton(service);
        serviceButton.setOnAction(serviceNameTextField, positionComboBox, priceTextField);
        serviceButtons.add(serviceButton);

        if(col == SERVICE_PER_ROW){
            col = 0;
            row++;
        }
        servicesGridPane.add(serviceButton.getButton(), col, row);
        col++;
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