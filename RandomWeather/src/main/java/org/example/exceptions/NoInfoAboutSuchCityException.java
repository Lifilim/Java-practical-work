package org.example.exceptions;

public class NoInfoAboutSuchCityException extends RuntimeException {
    @Override
    public String getMessage() {
        return "No information about such city!";
    }
}
