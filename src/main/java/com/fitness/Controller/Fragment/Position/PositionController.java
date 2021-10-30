package com.fitness.Controller.Fragment.Position;

import com.fitness.Controller.Controller;
import com.fitness.Element.PositionButton;
import com.fitness.Model.Work.Position;
import com.fitness.Service.Grid;
import com.fitness.Service.Work.PositionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
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
    private ScrollPane positionScrollPane;

    private GridPane positionsGridPane = null;

    private int row = 0, col = 0;
    private final byte POSITIONS_PER_ROW = 3;
    private PositionService positionService = new PositionService();
    private List<PositionButton> positionButtons = new ArrayList<>();

    public PositionController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/position/position.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    private void initGridPane(){
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
            Position position = positionService.edit(positionNameTextField);
            PositionButton.editSelected(position);
        });
        deleteButton.setOnAction(event -> {
            positionService.remove(PositionButton.getSelected().getPosition());
            PositionButton.removeSelected();
            initGridPane();
        });
        addButton.setOnAction(event -> {
            Position position = positionService.add();
            if(position != null)
                addPositionToGrid(position);
        });
    }

    private void addPositionToGrid(Position position){
        PositionButton positionButton = new PositionButton(position);
        positionButton.setOnAction(positionNameTextField);
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
        initGridPane();
        initListeners();
    }

    @Override
    public void stop() {
        positionNameTextField.setText("");
    }
}
