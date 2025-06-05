package org.example.repositories.impl;

import org.example.domain.City;
import org.example.dto.WeatherDto;
import org.example.entity.WeatherEntity;
import org.example.exceptions.NoInfoAboutSuchCityException;
import org.example.repositories.WeatherRepository;

import java.util.HashMap;
import java.util.Map;

public class RandomWeatherRepositoryImpl implements WeatherRepository {
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
    public WeatherEntity getWeather(City city) {
        if (!containsCity(city)) throw new NoInfoAboutSuchCityException();
        return new WeatherEntity(city, weatherInCityData.get(city));
    }
}