package org.example.repositories.impl;

import org.example.City;
import org.example.exceptions.NoInfoAboutSuchCityException;
import org.example.repositories.WeatherRepository;

import java.util.HashMap;
import java.util.Map;

public class WeatherRepositoryImpl implements WeatherRepository {
    private static final Map<City, String> weatherInCityData = new HashMap<>();

    @Override
    public boolean containsCity(City city) {
        return weatherInCityData.containsKey(city);
    }

    @Override
    public void addCity(City city, String cityWeather) {
        weatherInCityData.put(city, cityWeather);
    }

    @Override
    public String getCityWeather(City city) {
        if (!containsCity(city)) throw new NoInfoAboutSuchCityException();
        return weatherInCityData.get(city);
    }
}