package com.fitness.Service;


import javafx.scene.control.TextField;

public class Verifier {
    public static boolean correctCard(String card, TextField cardTextField){
        return !card.trim().equals("");
    }
    public static boolean correctName(String name, TextField nameTextField){
        if(name.trim().equals("")){
            nameTextField.getStyleClass().remove("textField");
            nameTextField.getStyleClass().add("textField_error");
            return false;
        }

        nameTextField.getStyleClass().remove("textField_error");
        nameTextField.getStyleClass().add("textField");
        return true;
    }
    public static boolean correctSurname(String surname, TextField surnameTextField){
        return !surname.trim().equals("");
    }
    public static boolean correctAddress(String address, TextField addressTextField){
        return !address.trim().equals("");
    }
    public static boolean correctPhone(String phone, TextField phoneTextField){
        return !phone.trim().equals("");
    }
}
