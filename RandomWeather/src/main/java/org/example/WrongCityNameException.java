package org.example;

public class WrongCityNameException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Wrong city name!";
    }
}
