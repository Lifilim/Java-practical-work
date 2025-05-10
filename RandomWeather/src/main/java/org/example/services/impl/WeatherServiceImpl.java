package org.example.services.impl;

import org.example.services.WeatherService;

import org.example.repositories.TemperatureRepository;
import org.example.repositories.WeatherRepository;
import org.example.repositories.impl.DBWeatherRepositoryImpl;
import org.example.repositories.impl.TemperatureRepositoryImpl;

import java.util.Random;
import org.example.domain.City;

public class WeatherServiceImpl implements WeatherService {
    private static final Random random = new Random();
    private static final String[] weathers = {"sunny", "cloudy", "foggy", "chance of precipitation"};
    private static final WeatherRepository weatherRepository = new DBWeatherRepositoryImpl();
    private static final TemperatureRepository temperatureService = new TemperatureRepositoryImpl();

    @Override
    public String getWeather(City city) {
        if (!weatherRepository.containsCity(city)) {
            int temperature = temperatureService.getTemperature();
            weatherRepository.addCity(city, Integer.toString(temperature) + "°C, " + weathers[random.nextInt(weathers.length)]);
        }
        return weatherRepository.getWeather(city);
    }
}
