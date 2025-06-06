package org.example.services;

import org.example.domain.City;
import org.example.dto.WeatherDto;

public interface WeatherService {
    WeatherDto getWeather(City city, String dateValue);
}