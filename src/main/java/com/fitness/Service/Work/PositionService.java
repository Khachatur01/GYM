package com.fitness.Service.Work;

import com.fitness.DAO.Work.PositionDAO;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Position;
import com.fitness.Service.Verify;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PositionService {
    private EmploymentService employmentService = new EmploymentService();
    private PositionDAO positionDAO = new PositionDAO();

    private Position makePosition(TextField nameTextField, ComboBox<Employment> employmentComboBox){
        Position position = null;
        String name = nameTextField.getText();
        Employment employment = employmentComboBox.getSelectionModel().getSelectedItem();

        if(     Verify.positionName(name, nameTextField) &&
                Verify.employment(employment, employmentComboBox)
        ){
            position = new Position();
            position.setName(name);
            position.setEmployment(employment);
        }

        return position;
    }

    public Position add(TextField positionNameTextField, ComboBox<Employment> employmentComboBox){
        Position position = this.makePosition(positionNameTextField, employmentComboBox);
        try {
            positionDAO.add(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return position;
    }
    public Position edit(Position position, TextField nameTextField, ComboBox<Employment> employmentComboBox) throws SQLException {
        Position newPosition = this.makePosition(nameTextField, employmentComboBox);
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

    public List<Position> getActual() throws SQLException{
        return positionDAO.getActual();
    }
    public List<Position> getAll() throws SQLException {
        return positionDAO.getAll();
    }
}
