package com.fitness.Service.Work;

import com.fitness.Model.Person.Customer;
import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Service;
import com.fitness.Service.Verify;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.*;
import java.util.List;

public class ServiceService {
    private PositionService positionService = new PositionService();

    private Service makeService(TextField nameTextField, ComboBox<Position> positionComboBox, TextField priceTextField){
        Service service = null;
        String name = nameTextField.getText();
        Position position = positionComboBox.getValue();
        String price = priceTextField.getText();
        if(     Verify.serviceName(name, nameTextField) &&
                Verify.position(position, positionComboBox) &&
                Verify.servicePrice(price, priceTextField)
        ){
            service = new Service();
            service.setName(name);
            service.setPosition(position);
            service.setPrice(Integer.parseInt(price));
        }

        return service;
    }

    public Service add(){
        Service service = null;
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
            service = this.makeService(nameTextField, positionComboBox, priceTextField);
            //@TODO add service to database;
        }

        return service;
    }
    public Service edit(TextField nameTextField, ComboBox<Position> positionComboBox, TextField priceTextField){
        Service service = this.makeService(nameTextField, positionComboBox, priceTextField);
        //@TODO edit service in database;
        return service;
    }
    public void remove(Service service){
        if(service == null) return;

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
                remove(service, true);
            else if (result.get() == removeHistory)
                remove(service, false);
        }
    }
    private void remove(Service service, boolean removeHistory){
        //TODO
        System.out.println("removed");
    }

    public Map<Service, Integer> getServiceBySubscriptionId(long id){
        Map<Service, Integer> offers = new HashMap<>();
        offers.put(new Service(1, "Բոքս", 1500, new Position(0, "Բոքսի մարզիչ")), 5);
        offers.put(new Service(2, "Մարզում", 1200, new Position(1, "Մարզիչ")), 5);
        offers.put(new Service(3, "Մերսում", 1000, new Position(2, "Մերսող")), 3);
        return offers;
    }

    public List<Service> getServices() {
        return new ArrayList<>(Arrays.asList(
                new Service(1, "Բոքս", 1500, new Position(0, "Բոքսի մարզիչ")),
                new Service(2, "Մարզում", 1200, new Position(1, "Մարզիչ")),
                new Service(3, "Մերսում", 1000, new Position(2, "Մերսող"))
        ));
    }
    //@TODO
}
