package com.fitness.Controller.Fragment.Employment;

import com.fitness.Controller.Controller;
import com.fitness.Element.EmploymentButton;
import com.fitness.Model.Work.Employment;
import com.fitness.Service.Clear;
import com.fitness.Service.Grid;
import com.fitness.Service.Work.EmploymentService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmploymentController extends GridPane implements Controller {
    @FXML
    private ScrollPane employmentsScrollPane;
    @FXML
    private TextField employmentNameTextField;
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

    private void initGridPane() throws SQLException {
        row = 0; col = 0;
        employmentsGridPane = new GridPane();
        employmentsGridPane.setMaxWidth(800);
        employmentsGridPane.setMinWidth(800);
        employmentsGridPane.setPrefWidth(800);
        employmentsGridPane.setVgap(20);
        employmentsGridPane.setPadding(new Insets(20, 20, 20, 20));
        employmentsScrollPane.setContent(employmentsGridPane);

        List<Employment> employments = employmentService.getAll();
        Grid.addColumns(employmentsGridPane, EMPLOYMENT_PER_ROW);
        Grid.addRows(employmentsGridPane, (int)Math.ceil(employments.size() / EMPLOYMENT_PER_ROW));

        for(Employment employment: employments){
            addEmploymentToGrid(employment);
        }
    }

    private void initListeners(){
        editButton.setOnAction(event -> {
            if(EmploymentButton.getSelected() == null) return;
            Employment employment = EmploymentButton.getSelected().getEmployment();
            try {
                employment = employmentService.edit(employment, employmentNameTextField, priceTextField);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            EmploymentButton.editSelected(employment);
        });
        deleteButton.setOnAction(event -> {
            EmploymentButton employmentButton = EmploymentButton.getSelected();
            if(employmentButton == null) return;
            try {
                employmentService.remove(employmentButton.getEmployment());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            EmploymentButton.removeSelected();
            try {
                initGridPane();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        addButton.setOnAction(event -> {
            Employment employment = null;
            try {
                employment = employmentService.add(employmentNameTextField, priceTextField);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(employment != null)
                addEmploymentToGrid(employment);
        });
    }

    private void addEmploymentToGrid(Employment employment){
        EmploymentButton employmentButton = new EmploymentButton(employment);
        employmentButton.setOnAction(employmentNameTextField, priceTextField);
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
        try {
            initGridPane();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initListeners();
    }

    @Override
    public void stop() {
        Clear.textField(
                employmentNameTextField,
                priceTextField
        );
        EmploymentButton.removeSelected();
    }
}