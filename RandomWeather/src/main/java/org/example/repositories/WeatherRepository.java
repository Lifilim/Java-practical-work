package org.example.repositories;

import org.example.domain.City;

public interface WeatherRepository {
    boolean containsCity(City city);
    void addCity(City city, String cityWeather);
    String getWeather(City city);
}