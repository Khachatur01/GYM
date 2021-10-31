package com.fitness.Service.Work;

import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Employment;
import com.fitness.Service.Verify;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.*;

public class EmploymentService {
    private PositionService positionService = new PositionService();

    private Employment makeEmployment(TextField nameTextField, ComboBox<Position> positionComboBox, TextField priceTextField){
        Employment employment = null;
        String name = nameTextField.getText();
        Position position = positionComboBox.getValue();
        String price = priceTextField.getText();
        if(     Verify.employmentName(name, nameTextField) &&
                Verify.position(position, positionComboBox) &&
                Verify.price(price, priceTextField)
        ){
            employment = new Employment();
            employment.setName(name);
            employment.setPosition(position);
            employment.setPrice(Integer.parseInt(price));
        }

        return employment;
    }

    public Employment add(){
        Employment employment = null;
        ButtonType add = new ButtonType("Ավելացնել", ButtonBar.ButtonData.OK_DONE);
        ButtonType close = new ButtonType("Փակել", ButtonBar.ButtonData.CANCEL_CLOSE);

        GridPane gridPane = new GridPane();
        TextField nameTextField = new TextField();
        ComboBox<Position> positionComboBox = new ComboBox<>();
        TextField priceTextField = new TextField();

        gridPane.add(new Label("Անուն"), 0, 0);
        gridPane.add(new Label("Հաստիք"), 0, 1);
        gridPane.add(new Label("Մեկ ծառայության գին"), 0, 2);

        gridPane.setVgap(20);
        gridPane.setHgap(20);
        positionComboBox.setItems(FXCollections.observableArrayList(positionService.getPositions()));

        gridPane.add(nameTextField, 1, 0);
        gridPane.add(positionComboBox, 1, 1);
        gridPane.add(priceTextField, 1, 2);

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Ծառայություն");
        alert.setHeaderText("Ավելացնել ծառայություն");
        alert.getDialogPane().setContent(gridPane);
        alert.getButtonTypes().setAll(add, close);

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == add){
            employment = this.makeEmployment(nameTextField, positionComboBox, priceTextField);
            //@TODO add employment to database;
        }

        return employment;
    }
    public Employment edit(TextField nameTextField, ComboBox<Position> positionComboBox, TextField priceTextField){
        Employment employment = this.makeEmployment(nameTextField, positionComboBox, priceTextField);
        //@TODO edit employment in database;
        return employment;
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

    public List<Employment> getEmployments() {
        //@TODO
        return new ArrayList<>(Arrays.asList(
                new Employment(1, "Բոքս", 1500, new Position(0, "Բոքսի մարզիչ")),
                new Employment(2, "Մարզում", 1200, new Position(1, "Մարզիչ")),
                new Employment(3, "Մերսում", 1000, new Position(2, "Մերսող"))
        ));
    }
}
