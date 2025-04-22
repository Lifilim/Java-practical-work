package org.example.repositories;

import org.example.City;

public interface SomeWeatherRepository {
    boolean containsCity(City city);
    void addCity(City city, String cityWeather);
    String getCityWeather(City city);
}