package org.example;

import org.example.exceptions.NoInfoAboutSuchCityException;
import org.example.exceptions.WrongCityNameException;

import org.example.services.WeatherService;
import org.example.services.impl.WeatherServiceImpl;

//import org.example.repositories.DatabaseExample;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
            String url="jdbc:postgresql://localhost:5432/weather";
            String user="lim";
            String password="milmil";

            try(Connection connection = DriverManager.getConnection(url, user, password)) {
                Statement statement = connection.createStatement();
                InputStream sqlInitial = Main.class
                        .getClassLoader()
                        .getResourceAsStream("sql/schema.sql");
                assert sqlInitial != null;
                String sql = new String(sqlInitial.readAllBytes(), StandardCharsets.UTF_8);
                statement.execute(sql);
                System.out.println("подключились!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


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
        }
    }

}