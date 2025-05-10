package org.example.repositories.impl;

import org.example.domain.City;
import org.example.Main;
import org.example.exceptions.DatabaseSelectException;
import org.example.exceptions.NoInfoAboutSuchCityException;
import org.example.repositories.DBWeatherRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class DBWeatherRepositoryImpl implements DBWeatherRepository {
    private String url = "jdbc:postgresql://localhost:5432/weather";
    private String user = "lim";
    private String password = "milmil";

    @Override
    public void connect() {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            InputStream sqlInitial = Main.class
                    .getClassLoader()
                    .getResourceAsStream("sql/schema.sql");
            assert sqlInitial != null;
            String sql = new String(sqlInitial.readAllBytes(), StandardCharsets.UTF_8);
            statement.execute(sql);
            System.out.println("База данный подключена!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DBWeatherRepositoryImpl() {
            this.connect();
    }

    @Override
    public void addCity(City city, String cityWeather) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO city(name, weatherStatus) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, city.getName()); // Значение для name (из переменной city.name)
                preparedStatement.setString(2, cityWeather);    // Значение для weatherStatus

                // запрос
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean containsCity(City city) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT EXISTS(SELECT 1 FROM city WHERE name = ?)")) {
            preparedStatement.setString(1, city.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getBoolean(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getWeather(City city) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT weatherStatus FROM city WHERE name = ?")) {
            preparedStatement.setString(1, city.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) throw new NoInfoAboutSuchCityException();
            return resultSet.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "TRUNCATE city")) {
                preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int length() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM city")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) throw new DatabaseSelectException();
                return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
