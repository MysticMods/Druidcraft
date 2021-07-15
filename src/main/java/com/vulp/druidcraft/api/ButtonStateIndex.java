package com.vulp.druidcraft.api;

public enum ButtonStateIndex {

    DEFAULT("default"),
    HOVERED("hovered"),
    PRESSED("pressed"),
    DISABLED("disabled"),
    AUTO("auto");

    private final String name;

    ButtonStateIndex(String name) {
        this.name = name;
    }

}