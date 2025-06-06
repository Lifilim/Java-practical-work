package org.example.domain;

import org.example.exceptions.WrongCityNameException;

import java.util.Objects;
import java.util.Scanner;

public class City {
    //================================================================================================================//
    private String name;        // city's name
    private String latitude;    // широта
    private String longitude;   // долгота

    //================================================================================================================//
    public City() {}
    public City(String cityName) { inputName(cityName); }
    public City(String cityName, String latitude, String longitude) {
        inputName(cityName);
        inputСoordinates(latitude, longitude);
    }

    public void inputName(Scanner terminalInput) {
        name = terminalInput.nextLine();
        if (!this.checkName()) throw new WrongCityNameException();
    }

    public void inputName(String input) {
        name = input;
        if (!this.checkName()) throw new WrongCityNameException();
    }

    public void inputСoordinates(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public boolean checkName() {
        return name.matches("[а-яА-Я]+");
    }

    public String getName() { return name; }
    public String getLatitude() { return latitude; }
    public String getLongitude() { return longitude; }

    //================================================================================================================//
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return name.equals(city.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
