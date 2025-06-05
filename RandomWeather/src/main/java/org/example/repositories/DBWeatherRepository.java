package org.example.repositories;

public interface DBWeatherRepository extends WeatherRepository{
    void clear();
    int length();
}
