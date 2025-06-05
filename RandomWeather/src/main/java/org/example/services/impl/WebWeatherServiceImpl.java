package org.example.services.impl;

import org.example.component.WeatherMapper;
import org.example.dto.WeatherDto;
import org.example.exceptions.NoInfoAboutSuchCityException;
import org.example.services.WeatherService;

import org.example.repositories.WeatherRepository;

import org.example.domain.City;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebWeatherServiceImpl implements WeatherService {
    //================================================================================================================//
    private WebClient webClientYandexMaps;
    private WebClient webClientProjecteol;
    private WeatherMapper weatherMapper;
    private WeatherRepository weatherRepository;

    //================================================================================================================//
    public WebWeatherServiceImpl(
            @Qualifier("webClientYandexMaps") WebClient webClientYandexMaps,
            @Qualifier("webClientProjecteol") WebClient webClientProjecteol,
            WeatherRepository weatherRepository,
            WeatherMapper weatherMapper) {
        this.webClientYandexMaps = webClientYandexMaps;
        this.webClientProjecteol = webClientProjecteol;
        this.weatherMapper = weatherMapper;
        this.weatherRepository = weatherRepository;
    }

    @Override
    public WeatherDto getWeather(City city) {
        if (!weatherRepository.containsCity(city)) {
            throw new NoInfoAboutSuchCityException();
        }
        return weatherMapper.mapToDto(weatherRepository.getWeather(city));
    }
    public WeatherDto getWeather(City city, String date) {
        if (!weatherRepository.containsCity(city)) {
            /*
            webClientProjecteol.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/weather/")
                            .query())
            */
        }
        return weatherMapper.mapToDto(weatherRepository.getWeather(city));
    }
}
