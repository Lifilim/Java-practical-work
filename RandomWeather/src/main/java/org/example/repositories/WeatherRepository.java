package org.example.repositories;

import org.example.City;

import java.sql.SQLException;

public interface WeatherRepository {
    void initialize();
    boolean containsCity(City city);
    void addCity(City city, String cityWeather);
    String getWeather(City city);
}