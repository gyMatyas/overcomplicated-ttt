package com.codecool.enterprise.overcomplicated.model;

public enum State {
    Empty("-"), Player("X"), AI("O");

    public String getValue() {
        return value;
    }

    String value;

    State(String s) {
        value = s;
    }
}
