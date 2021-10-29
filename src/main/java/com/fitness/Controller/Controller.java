package com.fitness.Controller;

import com.fitness.Window;

public interface Controller {
    void start();
    void stop();

    default void makeActive(){
        System.out.println("activate");
        Window.setActiveController(this);
    }
}
