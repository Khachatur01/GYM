package com.fitness.Constant;

import java.io.Serializable;

public enum Status implements Serializable {
    NOT_CONNECTED("Միացում չկա"),
    LOCAL("Լոկալ միացում"),
    REMOTE("Հեռավար միացում"),
    FILE("Ֆայլային միացում");

    private final String name;
    Status(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
