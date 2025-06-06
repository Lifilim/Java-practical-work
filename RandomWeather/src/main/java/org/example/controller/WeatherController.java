package org.example.controller;

import org.example.domain.City;
import org.example.dto.WeatherDto;
import org.example.services.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    //================================================================================================================//
    private final WeatherService weatherService;

    //================================================================================================================//
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather/")
    public WeatherDto getWeatherByCityName(@RequestParam  String cityName, @RequestParam String date) {
        return weatherService.getWeather(new City(cityName), date);
    }
}