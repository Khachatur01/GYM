package com.fitness.Service.Work;

import com.fitness.Model.Person.Customer;
import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Service;
import com.fitness.Service.Verify;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.*;
import java.util.List;

public class ServiceService {
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

    public boolean add(){
        ButtonType add = new ButtonType("Ավելացնել", ButtonBar.ButtonData.OK_DONE);
        ButtonType close = new ButtonType("Փակել", ButtonBar.ButtonData.CANCEL_CLOSE);

        TextField nameTextField = new TextField();
        ComboBox<Position> positionComboBox = new ComboBox<>();
        TextField priceTextField = new TextField();
        GridPane gridPane = new GridPane();
        gridPane.getChildren().addAll(nameTextField, positionComboBox, priceTextField);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Ավելացնել ծառայություն");
        alert.setHeaderText("Ավելացնել ծառայություն");
        alert.getDialogPane().setContent(gridPane);
        alert.getButtonTypes().setAll(add, close);

        Optional<ButtonType> result =  alert.showAndWait();

        if(result.isPresent()){
            if(result.get() == add)
                System.out.println("add");
        }

        Service service = this.makeService(nameTextField, positionComboBox, priceTextField);
        //@TODO add service to database;
        return service != null;
    }
    public boolean edit(TextField nameTextField, ComboBox<Position> positionComboBox, TextField priceTextField){
        Service service = this.makeService(nameTextField, positionComboBox, priceTextField);
        //@TODO edit service in database;
        return service != null;
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
        offers.put(new Service(1, "Բոքս", 1500, new Position("Բոքսի մարզիչ")), 5);
        offers.put(new Service(2, "Մարզում", 1200, new Position("Մարզիչ")), 5);
        offers.put(new Service(3, "Մերսում", 1000, new Position("Մերսող")), 3);
        return offers;
    }

    public List<Service> getServices() {
        return new ArrayList<>(Arrays.asList(
                new Service(1, "Բոքս", 1500, new Position("Բոքսի մարզիչ")),
                new Service(2, "Մարզում", 1200, new Position("Մարզիչ")),
                new Service(3, "Մերսում", 1000, new Position("Մերսող"))
        ));
    }
    //@TODO
}
