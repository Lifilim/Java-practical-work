package org.example.services.impl;

import org.example.services.SomeWeatherService;

import org.example.repositories.SomeWeatherRepository;
import org.example.repositories.SomeTemperatureRepository;
import org.example.repositories.impl.SomeWeatherRepositoryImpl;
import org.example.repositories.impl.SomeTemperatureRepositoryImpl;

import java.util.Random;
import org.example.City;

public class SomeWeatherServiceImpl implements SomeWeatherService {
    private static final Random random = new Random();
    private static final String[] weathers = {"sunny", "cloudy", "foggy", "chance of precipitation"};
    private static final SomeWeatherRepository weatherRepository = new SomeWeatherRepositoryImpl();
    private static final SomeTemperatureRepository temperatureService = new SomeTemperatureRepositoryImpl();

    @Override
    public String getWeather(City city) {
        if (!weatherRepository.containsCity(city)) {
            int temperature = temperatureService.getTemperature();
            weatherRepository.addCity(city, Integer.toString(temperature) + "°C, " + weathers[random.nextInt(weathers.length)]);
        }
        return weatherRepository.getCityWeather(city);
    }
}
