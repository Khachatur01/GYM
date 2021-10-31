package com.fitness.Controller.Fragment.Employment;

import com.fitness.Controller.Controller;
import com.fitness.Element.EmploymentButton;
import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Employment;
import com.fitness.Service.Grid;
import com.fitness.Service.Work.EmploymentService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmploymentController extends GridPane implements Controller {
    @FXML
    private ScrollPane employmentScrollPane;
    @FXML
    private TextField employmentNameTextField;
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

    private GridPane employmentsGridPane = null;

    private int row = 0, col = 0;
    private final byte EMPLOYMENT_PER_ROW = 3;
    private EmploymentService employmentService = new EmploymentService();
    private List<EmploymentButton> employmentButtons = new ArrayList<>();

    public EmploymentController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/employment/employment.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    private void initGridPane(){
        row = 0; col = 0;
        employmentsGridPane = new GridPane();
        employmentsGridPane.setMaxWidth(800);
        employmentsGridPane.setMinWidth(800);
        employmentsGridPane.setPrefWidth(800);
        employmentsGridPane.setVgap(20);
        employmentsGridPane.setPadding(new Insets(20, 20, 20, 20));
        employmentScrollPane.setContent(employmentsGridPane);

        List<Employment> employments = employmentService.getEmployments();
        Grid.addColumns(employmentsGridPane, EMPLOYMENT_PER_ROW);
        Grid.addRows(employmentsGridPane, (int)Math.ceil(employments.size() / EMPLOYMENT_PER_ROW));

        for(Employment employment: employments){
            addEmploymentToGrid(employment);
        }
    }

    private void initListeners(){
        editButton.setOnAction(event -> {
            Employment employment = employmentService.edit(employmentNameTextField, positionComboBox, priceTextField);
            EmploymentButton.editSelected(employment);
        });
        deleteButton.setOnAction(event -> {
            EmploymentButton employment = EmploymentButton.getSelected();
            if(employment == null) return;
            employmentService.remove(employment.getEmployment());
            EmploymentButton.removeSelected();
            initGridPane();
        });
        addButton.setOnAction(event -> {
            Employment employment = employmentService.add();
            if(employment != null)
                addEmploymentToGrid(employment);
        });
    }

    private void addEmploymentToGrid(Employment employment){
        positionComboBox.getItems().add(employment.getPosition());

        EmploymentButton employmentButton = new EmploymentButton(employment);
        employmentButton.setOnAction(employmentNameTextField, positionComboBox, priceTextField);
        employmentButtons.add(employmentButton);

        if(col == EMPLOYMENT_PER_ROW){
            col = 0;
            row++;
        }
        employmentsGridPane.add(employmentButton.getButton(), col, row);
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
        employmentNameTextField.setText("");
        positionComboBox.getItems().clear();
        priceTextField.setText("");
    }
}