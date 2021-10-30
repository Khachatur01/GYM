package com.fitness.Service.Work;

import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Service;
import com.fitness.Service.Verify;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.*;

public class PositionService {

    private Position makePosition(TextField nameTextField){
        Position position = null;
        String name = nameTextField.getText();

        if(     Verify.positionName(name, nameTextField)
        ){
            position = new Position();
            position.setName(name);
        }

        return position;
    }

    public Position add(){
        Position position = null;
        ButtonType add = new ButtonType("Ավելացնել", ButtonBar.ButtonData.OK_DONE);
        ButtonType close = new ButtonType("Փակել", ButtonBar.ButtonData.CANCEL_CLOSE);

        GridPane gridPane = new GridPane();
        TextField nameTextField = new TextField();

        gridPane.add(new Label("Անուն"), 0, 0);

        gridPane.setVgap(20);
        gridPane.setHgap(20);

        gridPane.add(nameTextField, 1, 0);

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Հաստիք");
        alert.setHeaderText("Ավելացնել հաստիք");
        alert.getDialogPane().setContent(gridPane);
        alert.getButtonTypes().setAll(add, close);

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == add){
            position = this.makePosition(nameTextField);
            //@TODO add service to database;
        }

        return position;
    }
    public Position edit(TextField nameTextField){
        Position position = this.makePosition(nameTextField);
        //@TODO edit service in database;
        return position;
    }
    public void remove(Position position){
        if(position == null) return;

        ButtonType yes = new ButtonType("Ջնջել", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Հետ", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType removeHistory = new ButtonType("Ջնջել պատմությունը", ButtonBar.ButtonData.OK_DONE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Հաստատում");
        alert.setHeaderText("Հաստատեք, որ ցանկանում եք ջնջել հաստիքը");
        alert.setContentText("Ցանկանու՞մ եք ջնջել հաստիքը");
        alert.getButtonTypes().setAll(yes, removeHistory, no);

        Optional<ButtonType> result =  alert.showAndWait();

        if(result.isPresent()){
            if(result.get() == yes)
                remove(position, true);
            else if (result.get() == removeHistory)
                remove(position, false);
        }
    }
    private void remove(Position position, boolean removeHistory){
        //TODO
        System.out.println("removed");
    }

    public Position getPositionByServiceId(long id){
        return new Position(0, "Բոքսի մարզիչ");
    }

    public List<Position> getPositions() {
        return new ArrayList<>(Arrays.asList(
                new Position(0, "Բոքսի մարզիչ"),
                new Position(1, "Մարզիչ"),
                new Position(2, "Մերսող")
        ));
    }
    //@TODO
}
