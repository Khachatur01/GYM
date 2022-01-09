package com.fitness.Constant;

public enum Week {
    MONDAY("monday", "Երկ․"),
    TUESDAY("tuesday", "Երեք․"),
    WEDNESDAY("wednesday", "Չոր․"),
    THURSDAY("thursday", "Հինգ․"),
    FRIDAY("friday", "Ուրբ․"),
    SATURDAY("saturday", "Շաբ․"),
    SUNDAY("sunday", "Կիր․");

    private final String name;
    private final String armName;
    Week(String name, String armName) {
        this.name = name;
        this.armName = armName;
    }
    public String toString() {
        return this.name;
    }
    public String getArmName() {return  this.armName;}
}
