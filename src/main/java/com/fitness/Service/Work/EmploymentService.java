package com.fitness.Service.Work;

import com.fitness.DAO.Work.EmploymentDAO;
import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Verify;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.*;

public class EmploymentService {
    private EmploymentDAO employmentDAO = new EmploymentDAO();

    private Employment makeEmployment(TextField nameTextField, TextField priceTextField){
        Employment employment = null;
        String name = nameTextField.getText();
        String price = priceTextField.getText();
        if(     Verify.employmentName(name, nameTextField) &&
                Verify.price(price, priceTextField)
        ){
            employment = new Employment();
            employment.setName(name);
            employment.setPrice(Integer.parseInt(price));
        }

        return employment;
    }

    public Employment add(TextField employmentNameTextField, TextField priceTextField){
        Employment employment = this.makeEmployment(employmentNameTextField, priceTextField);
        try {
            employmentDAO.add(employment);
        } catch (SQLException e) {
            e.printStackTrace();
            employment = null;
        }

        return employment;
    }
    public Employment edit(Employment employment, TextField nameTextField, TextField priceTextField) throws SQLException {
        Employment newEmployment = this.makeEmployment(nameTextField, priceTextField);
        newEmployment.setId(employment.getId());
        employmentDAO.edit(newEmployment);
        return newEmployment;
    }
    public void remove(Employment employment) throws SQLException {
        if(employment == null) return;

        ButtonType archive = new ButtonType("Արխիվացնել", ButtonBar.ButtonData.OK_DONE);
        ButtonType back = new ButtonType("Հետ", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType removeHistory = new ButtonType("Ջնջել պատմությունը", ButtonBar.ButtonData.OK_DONE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Հաստատում");
        alert.setHeaderText("Հաստատեք, որ ցանկանում եք ջնջել ծառայությունը");
        alert.setContentText("Ցանկանու՞մ եք ջնջել ծառայությունը");
        alert.getButtonTypes().setAll(archive, removeHistory, back);

        Optional<ButtonType> result =  alert.showAndWait();

        if(result.isPresent()){
            if(result.get() == archive)
                employmentDAO.remove(employment, false);
            else if (result.get() == removeHistory)
                employmentDAO.remove(employment, true);
        }
    }

    public List<Employment> getActual() throws SQLException {
        return employmentDAO.getActual();
    }
    public List<Employment> getAll() throws SQLException {
        return employmentDAO.getAll();
    }
}
