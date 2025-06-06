package org.example.services.impl;

import org.example.component.WeatherMapper;
import org.example.dto.WeatherDto;
import org.example.exceptions.NoInfoAboutSuchCityException;
import org.example.services.WeatherService;

import org.example.repositories.WeatherRepository;

import org.example.domain.City;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.time.Clock;

@Service
public class WebWeatherServiceImpl implements WeatherService {
    //================================================================================================================//
    private WebClient webClientYandexMaps;
    private WebClient webClientProjecteol;
    private WeatherMapper weatherMapper;
    private WeatherRepository weatherRepository;
    private Clock clock;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Value("${app.cred.token}")
    private String token;

    //================================================================================================================//
    public WebWeatherServiceImpl(
            @Qualifier("webClientYandexMaps") WebClient webClientYandexMaps,
            @Qualifier("webClientProjecteol") WebClient webClientProjecteol,
            WeatherRepository weatherRepository,
            WeatherMapper weatherMapper,
            Clock clock) {
        this.webClientYandexMaps = webClientYandexMaps;
        this.webClientProjecteol = webClientProjecteol;
        this.weatherMapper = weatherMapper;
        this.weatherRepository = weatherRepository;
        this.clock = clock;
    }

    @Override
    public WeatherDto getWeather(City city, String date) {
        final String dateValue = (date == null) ? sdf.format(clock) : date;

        WeatherDto result;
        if (!weatherRepository.containsCity(city)) {
            /*
            result = webClientProjecteol.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/weather/")
                        .queryParam("lat", city.getLatitude())
                        .queryParam("lon", city.getLongitude())
                        .queryParam("date", dateValue)
                        .queryParam("token", token)
                        .build()
                )
                .retrieve()
                .bodyToMono(WeatherDto.class)
                .block();
             */

        }
        return weatherMapper.mapToDto(weatherRepository.getWeather(city, dateValue));
    }
}
