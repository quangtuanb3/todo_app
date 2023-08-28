package com.example.task_app.model.enumeration;

public enum Gender {
    MALE("Nam"),

    FEMALE("Nữ");

    private final String name;

    Gender(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
