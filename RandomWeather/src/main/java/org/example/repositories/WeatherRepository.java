package org.example.repositories;

import org.example.domain.City;
import org.example.entity.WeatherEntity;

public interface WeatherRepository {
    boolean containsCity(City city);
    void addCity(City city, String cityWeather);
    WeatherEntity getWeather(City city);
}