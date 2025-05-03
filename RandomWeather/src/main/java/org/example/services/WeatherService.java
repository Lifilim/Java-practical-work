package org.example.services;

import org.example.City;
import java.sql.SQLException;

public interface WeatherService {
    String getWeather(City city) throws SQLException;
}