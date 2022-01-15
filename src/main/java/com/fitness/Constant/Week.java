package com.fitness.Constant;

public enum Week {
    MONDAY("monday", "Երկ․", "Երկուշաբթի"),
    TUESDAY("tuesday", "Երեք․", "Երեքշաբթի"),
    WEDNESDAY("wednesday", "Չոր․", "Չորեքշաբթի"),
    THURSDAY("thursday", "Հինգ․", "Հինգշաբթի"),
    FRIDAY("friday", "Ուրբ․", "Ուրբաթ"),
    SATURDAY("saturday", "Շաբ․", "Շաբաթ"),
    SUNDAY("sunday", "Կիր․", "Կիրակի");

    private final String name;
    private final String armName;
    private final String armFullName;
    Week(String name, String armName, String armFullName) {
        this.name = name;
        this.armName = armName;
        this.armFullName = armFullName;
    }
    public String toString() {
        return this.name;
    }
    public String getArmName() {return this.armName;}

    public String getArmFullName() {
        return armFullName;
    }
}
