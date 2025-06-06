package org.example.component;

import org.example.dto.WeatherDto;
import org.example.entity.WeatherEntity;
import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {
    public WeatherDto mapToDto(WeatherEntity weatherEntity) {
        return new WeatherDto(weatherEntity.getId(), weatherEntity.getCity(), weatherEntity.getWeatherStatus(), weatherEntity.getDateValue());
    }
    public WeatherEntity mapToEntity(WeatherDto weatherDto) {
        return new WeatherEntity(weatherDto.getId(), weatherDto.getCity(), weatherDto.getWeatherStatus(), weatherDto.getDateValue());
    }
}
