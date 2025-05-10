package org.example.repositories;

import org.example.City;
import java.sql.SQLException;

public interface DBWeatherRepository extends WeatherRepository{
    void connect();
    void clear();
    int length();
}
