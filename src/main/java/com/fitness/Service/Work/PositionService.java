package com.fitness.Service.Work;

import com.fitness.DAO.Work.PositionDAO;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Position;
import com.fitness.Service.Verify;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PositionService {
    private final PositionDAO positionDAO = new PositionDAO();

    private Position makePosition(TextField nameTextField, ObservableList<Employment> employments) {
        Position position = null;
        String name = nameTextField.getText();

        if(     Verify.positionName(name, nameTextField) &&
                !employments.isEmpty()
        ) {
            position = new Position();
            position.setName(name);
            position.setEmployments(employments);
        }

        return position;
    }

    public Position add(TextField positionNameTextField, ObservableList<Employment> employments) {
        Position position = this.makePosition(positionNameTextField, employments);
        try {
            positionDAO.add(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return position;
    }
    public Position edit(Position position, TextField nameTextField, ObservableList<Employment> employments) throws SQLException {
        Position newPosition = this.makePosition(nameTextField, employments);
        newPosition.setId(position.getId());
        positionDAO.edit(newPosition);
        return newPosition;
    }
    public void remove(Position position) throws SQLException {
        if(position == null) return;

        ButtonType archive = new ButtonType("Արխիվացնել", ButtonBar.ButtonData.OK_DONE);
        ButtonType back = new ButtonType("Հետ", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType removeHistory = new ButtonType("Ջնջել պատմությունը", ButtonBar.ButtonData.OK_DONE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Հաստատում");
        alert.setHeaderText("Հաստատեք, որ ցանկանում եք ջնջել հաստիքը");
        alert.setContentText("Ցանկանու՞մ եք ջնջել հաստիքը");
        alert.getButtonTypes().setAll(archive, removeHistory, back);

        Optional<ButtonType> result =  alert.showAndWait();

        if(result.isPresent()){
            if(result.get() == archive)
                positionDAO.remove(position, false);
            else if (result.get() == removeHistory)
                positionDAO.remove(position, true);
        }
    }

    public List<Position> getActual() throws SQLException {
        return positionDAO.getActual();
    }
    public List<Position> getAll() throws SQLException {
        return positionDAO.getAll();
    }
}
