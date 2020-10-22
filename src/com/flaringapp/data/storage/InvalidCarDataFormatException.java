package com.flaringapp.data.storage;

public class InvalidCarDataFormatException extends IllegalStateException {

    public InvalidCarDataFormatException(String line) {
        super("Invalid car data input at line: \"" + line + "\"");
    }
}
