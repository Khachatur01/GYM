package com.fitness.Service.Work;

import com.fitness.DAO.Work.EmploymentDAO;
import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Employment;
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
    public void remove(Employment employment){
        if(employment == null) return;

        ButtonType yes = new ButtonType("Ջնջել", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Հետ", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType removeHistory = new ButtonType("Ջնջել պատմությունը", ButtonBar.ButtonData.OK_DONE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Հաստատում");
        alert.setHeaderText("Հաստատեք, որ ցանկանում եք ջնջել ծառայությունը");
        alert.setContentText("Ցանկանու՞մ եք ջնջել ծառայությունը");
        alert.getButtonTypes().setAll(yes, removeHistory, no);

        Optional<ButtonType> result =  alert.showAndWait();

        if(result.isPresent()){
            if(result.get() == yes)
                remove(employment, true);
            else if (result.get() == removeHistory)
                remove(employment, false);
        }
    }
    private void remove(Employment employment, boolean removeHistory){
        //TODO
        System.out.println("removed");
    }

    public List<Employment> getEmployments() throws SQLException {
        return employmentDAO.getNonArchived();
    }
}
