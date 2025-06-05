package org.example.domain;

import org.example.exceptions.WrongCityNameException;

import java.util.Objects;
import java.util.Scanner;

public class City {
    //================================================================================================================//
    private String name;    // city's name

    //================================================================================================================//
    public City() {}
    public City(String cityName) { inputName(cityName); }
    
    public void inputName(Scanner terminalInput) {
        name = terminalInput.nextLine();
        if (!this.checkName()) throw new WrongCityNameException();
    }

    public void inputName(String input) {
        name = input;
        if (!this.checkName()) throw new WrongCityNameException();
    }

    public String getName() {
        return name;
    }

    public boolean checkName() {
        return name.matches("[а-яА-Я]+");
    }

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
