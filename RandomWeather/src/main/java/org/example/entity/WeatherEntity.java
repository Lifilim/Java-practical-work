package org.example.entity;

import org.example.domain.City;

public class WeatherEntity {
    private Long id;
    private City city;
    private String weatherStatus;
    private String date;

    public WeatherEntity(City city, String weatherStatus) {
        this.city = city;
        this.weatherStatus = weatherStatus;
    }
    public WeatherEntity(Long id, City city, String weatherStatus, String date) {
        this.id = id;
        this.city = city;
        this.weatherStatus = weatherStatus;
        this.date = date;
    }

    public Long getId() { return id; }
    public City getCity() { return city; }
    public String getWeatherStatus() { return weatherStatus; }
    public String getDate() { return date; }

    public void setId(Long id) { this.id = id; }
    public void setName(City city) { this.city = city; }
    public void setWeatherStatus(String weatherStatus) { this.weatherStatus = weatherStatus; }
    public void setDate(String weatherStatus) { this.date = date; }
}
