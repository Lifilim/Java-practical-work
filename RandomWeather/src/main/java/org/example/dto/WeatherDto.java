package org.example.dto;

import org.example.domain.City;

public class WeatherDto {
    private Long id;
    private City city;
    private String weatherStatus;
    private String dateValue;

    public WeatherDto(Long id, City city, String date, String weatherStatus) {
        this.id = id;
        this.city = city;
        this.dateValue = date;
        this.weatherStatus = weatherStatus;
    }

    public Long getId() { return id; }
    public City getCity() { return city; }
    public String getDateValue() { return dateValue; }
    public String getWeatherStatus() { return weatherStatus; }

    public void setId(Long id) { this.id = id; }
    public void setName(City city) { this.city = city; }
    public void setDateValue(String weatherStatus) { this.dateValue = dateValue; }
    public void setWeatherStatus(String weatherStatus) { this.weatherStatus = weatherStatus; }
}
