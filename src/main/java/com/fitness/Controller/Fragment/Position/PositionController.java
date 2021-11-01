package com.fitness.Controller.Fragment.Position;

import com.fitness.Controller.Controller;
import com.fitness.Element.PositionButton;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Position;
import com.fitness.Service.Clear;
import com.fitness.Service.Grid;
import com.fitness.Service.Work.EmploymentService;
import com.fitness.Service.Work.PositionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionController extends GridPane implements Controller {
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private TextField positionNameTextField;
    @FXML
    private ComboBox<Employment> employmentComboBox;
    @FXML
    private ScrollPane positionScrollPane;

    private GridPane positionsGridPane = null;

    private int row = 0, col = 0;
    private final byte POSITIONS_PER_ROW = 3;
    private PositionService positionService = new PositionService();
    private EmploymentService employmentService = new EmploymentService();
    private List<PositionButton> positionButtons = new ArrayList<>();

    public PositionController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/position/position.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    private void initComboBox() throws SQLException {
        employmentComboBox.setItems(FXCollections.observableArrayList(employmentService.getActual()));
    }

    private void initGridPane() throws SQLException {
        row = 0; col = 0;
        positionsGridPane = new GridPane();
        positionsGridPane.setMaxWidth(800);
        positionsGridPane.setMinWidth(800);
        positionsGridPane.setPrefWidth(800);
        positionsGridPane.setVgap(20);
        positionsGridPane.setPadding(new Insets(20, 20, 20, 20));
        positionScrollPane.setContent(positionsGridPane);

        List<Position> positions = positionService.getPositions();
        Grid.addColumns(positionsGridPane, POSITIONS_PER_ROW);
        Grid.addRows(positionsGridPane, (int)Math.ceil(positions.size() / POSITIONS_PER_ROW));

        for(Position position: positions){
            addPositionToGrid(position);
        }
    }

    private void initListeners(){
        editButton.setOnAction(event -> {
            if(PositionButton.getSelected() == null) return;
            Position position = PositionButton.getSelected().getPosition();
            try {
                position = positionService.edit(position, positionNameTextField, employmentComboBox);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            PositionButton.editSelected(position);
        });
        deleteButton.setOnAction(event -> {
            positionService.remove(PositionButton.getSelected().getPosition());
            PositionButton.removeSelected();
            try {
                initGridPane();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        addButton.setOnAction(event -> {
            Position position = positionService.add(positionNameTextField, employmentComboBox);
            if(position != null) {
                addPositionToGrid(position);
            }
        });
    }

    private void addPositionToGrid(Position position) {
        PositionButton positionButton = new PositionButton(position);
        positionButton.setOnAction(positionNameTextField, employmentComboBox);
        positionButtons.add(positionButton);

        if(col == POSITIONS_PER_ROW){
            col = 0;
            row++;
        }
        positionsGridPane.add(positionButton.getButton(), col, row);
        col++;
    }

    @Override
    public void start() {
        makeActive();
        try {
            initGridPane();
            initComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initListeners();
    }

    @Override
    public void stop() {
        Clear.textField(positionNameTextField);
        Clear.comboBox(employmentComboBox);
        PositionButton.removeSelected();
    }
}
