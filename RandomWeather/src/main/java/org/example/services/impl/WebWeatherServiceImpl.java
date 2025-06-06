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
import java.util.List;
import java.util.Map;

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
    @Value("${app.cred.key}")
    private String key;

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

        WeatherDto result = new WeatherDto(city, dateValue, null);
        if (!weatherRepository.containsCity(city)) {
            Map<String, Object> response = webClientYandexMaps.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/")
                            .queryParam("apikey", key)
                            .queryParam("geocode", city.getName())
                            .queryParam("lang", "ru_RU")
                            .queryParam("format", "json")
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null && response.containsKey("response")) {
                Map<String, Object> responseMap = (Map<String, Object>) response.get("response");
                Map<String, Object> geoObjectCollection = (Map<String, Object>) responseMap.get("GeoObjectCollection");
                List<Map<String, Object>> featureMember = (List<Map<String, Object>>) geoObjectCollection.get("featureMember");

                if (!featureMember.isEmpty()) {
                    Map<String, Object> firstFeature = featureMember.get(0);
                    Map<String, Object> geoObject = (Map<String, Object>) firstFeature.get("GeoObject");
                    Map<String, Object> point = (Map<String, Object>) geoObject.get("Point");
                    String pos = (String) point.get("pos");

                    String[] coordinates = pos.split(" ");
                    String latitude = coordinates[0]; // долгота
                    String longitude = coordinates[1];  // широта

                    city.inputСoordinates(latitude, longitude);
                }
            }

            result.setCity(city);
            result.setWeatherStatus(webClientProjecteol.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/forecast_energy/")
                        .queryParam("lat", city.getLatitude())
                        .queryParam("lon", city.getLongitude())
                        .queryParam("date", dateValue)
                        .queryParam("kind", "ses")
                        .queryParam("token", token)
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block()
            );

            weatherRepository.addCity(city, dateValue, result.getWeatherStatus());
        }
        result.setWeatherStatus(weatherRepository.getWeather(city, dateValue).getWeatherStatus());
        return result;
    }
}
