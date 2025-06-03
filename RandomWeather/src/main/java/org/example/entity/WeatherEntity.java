package org.example.entity;

public class WeatherEntity {
    private Long id;
    private String name;
    private String weatherStatus;

    public WeatherEntity(Long id, String name, String weatherStatus) {
        this.id = id;
        this.name = name;
        this.weatherStatus = weatherStatus;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getWeatherStatus() { return weatherStatus; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setWeatherStatus(String weatherStatus) { this.weatherStatus = weatherStatus; }
}
