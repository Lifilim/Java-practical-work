package org.example.repositories;

public interface DBWeatherRepository extends WeatherRepository{
    void connect();
    void clear();
    int length();
}
