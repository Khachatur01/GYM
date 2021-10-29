package com.fitness.Controller.Fragment.Service;

import com.fitness.Controller.Controller;
import com.fitness.Model.Work.Position;
import com.fitness.Service.Work.PositionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.util.List;

public class ServiceController extends GridPane implements Controller {
    @FXML
    private GridPane servicesGridPane;
    @FXML
    private TextField serviceNameTextField;
    @FXML
    private ComboBox<Position> positionComboBox;
    @FXML
    private TextField priceTextField;
    @FXML
    private Button editButton;
    @FXML
    private Button addServiceButton;
    @FXML
    private Button addPositionButton;
    @FXML
    private Button deleteButton;

    private final byte SERVICE_PER_ROW = 3;
    private PositionService positionService = new PositionService();

    public ServiceController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/service/service.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    private void initGridPane(){
        List<Position> positions = positionService.getPositions();
        int row = 0, col = 0;

        for(int i = 0; i < SERVICE_PER_ROW; ++i){
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMinWidth(USE_COMPUTED_SIZE);
            columnConstraints.setMaxWidth(USE_COMPUTED_SIZE);
            columnConstraints.setPrefWidth(USE_COMPUTED_SIZE);
            columnConstraints.setHalignment(HPos.CENTER);
            servicesGridPane.getColumnConstraints().add(columnConstraints);
        }
        for(Position position: positions){
            String serviceName = position.getService().getName();
            String positionName = position.getName();

            Button serviceButton = new Button(serviceName);
            serviceButton.getStyleClass().add("service_box");

            if(col == SERVICE_PER_ROW){
                col = 0;
                row++;
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setMinHeight(USE_COMPUTED_SIZE);
                rowConstraints.setMaxHeight(USE_COMPUTED_SIZE);
                rowConstraints.setPrefHeight(USE_COMPUTED_SIZE);
                rowConstraints.setValignment(VPos.CENTER);
                servicesGridPane.getRowConstraints().add(rowConstraints);
            }

            servicesGridPane.add(serviceButton, col, row);
            col++;

        }
    }

    @Override
    public void start() {
        makeActive();
        initGridPane();
    }

    @Override
    public void stop() {

    }
}