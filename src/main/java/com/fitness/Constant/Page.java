package com.fitness.Constant;

public enum Page {
    PRELOADER("preload.fxml"),
    MENU("menu.fxml");

    private String value;
    public String getValue(){
        return this.value;
    }
    Page(String value){
        this.value = value;
    }
}
