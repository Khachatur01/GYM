package com.fitness.Controller.Constant;

public enum Default {
    ID(-1);

    private long value;
    Default(long value){
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
