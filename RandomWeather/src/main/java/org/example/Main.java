package org.example;

import org.example.exceptions.NoInfoAboutSuchCityException;
import org.example.exceptions.WrongCityNameException;

import org.example.services.SomeWeatherService;
import org.example.services.impl.SomeWeatherServiceImpl;

import org.example.City;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        try (Scanner terminalInput = new Scanner(System.in)) {
            SomeWeatherService weatherService = new SomeWeatherServiceImpl();
            while (true) {
                System.out.println("Введите название города:");
                try {
                    City city = new City();
                    city.inputName(terminalInput);
                    System.out.println(weatherService.getWeather(city));
                } catch (WrongCityNameException e) {
                    System.out.println("\u001B[33m" + "Некорректный запрос. \nНазвание города должно состоять только из символов кириллицы." + "\u001B[0m");
                } catch (NoInfoAboutSuchCityException e) {
                    System.out.println("\u001B[33m" + "Информации об этом городе нет в базе. Добавьте её." + "\u001B[0m");
                } finally {
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("\u001B[3mКакая-то ошибка ¯\\_(ツ)_/¯\u001B[0m");
        }
    }

}