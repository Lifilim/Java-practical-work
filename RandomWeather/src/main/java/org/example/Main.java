package org.example;

import org.example.domain.City;
import org.example.exceptions.NoInfoAboutSuchCityException;
import org.example.exceptions.WrongCityNameException;

import org.example.services.WeatherService;
import org.example.services.impl.WeatherServiceImpl;

import java.util.*;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        try (Scanner terminalInput = new Scanner(System.in)) {
            WeatherService weatherService = new WeatherServiceImpl();
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
            System.out.println("\u001B[33m" + e.getMessage() + "\u001B[0m");
        }
    }

}