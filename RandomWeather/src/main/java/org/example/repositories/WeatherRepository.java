package org.example.repositories;

import org.example.City;

import java.sql.SQLException;

public interface WeatherRepository {
    boolean containsCity(City city) throws SQLException;
    void addCity(City city, String cityWeather) throws SQLException;
    String getCityWeather(City city) throws SQLException;
}