package com.flaringapp.data.storage;

public class InvalidCarDataFormat extends IllegalStateException {

    public InvalidCarDataFormat(String line) {
        super("Invalid car data input at line: \"" + line + "\"");
    }
}
