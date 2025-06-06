package org.example.repositories.impl;

import org.example.domain.City;
import org.example.entity.WeatherEntity;
import org.example.exceptions.DatabaseSelectException;
import org.example.exceptions.NoInfoAboutSuchCityException;
import org.example.repositories.DBWeatherRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class DBWeatherRepositoryImpl implements DBWeatherRepository {
    //================================================================================================================//
    private JdbcTemplate jdbcTemplate;

    //================================================================================================================//
    private final RowMapper<WeatherEntity> weatherRowMapper= (rs, rowNum) ->
            new WeatherEntity(rs.getLong("id"),
                    new City(rs.getString("name"),
                            rs.getString("latitude"),
                            rs.getString("longitude")),
                    rs.getString("dateValue"),
                    rs.getString("weatherStatus")
            );
    private final RowMapper<WeatherEntity> weatherStatusRowMapper = (rs, rowNum) ->
            new WeatherEntity(null,
                    null,
                    rs.getString("weatherStatus"), null
            );

    //================================================================================================================//
    public DBWeatherRepositoryImpl(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; };

    @Override
    public void addCity(City city, String dateValue, String cityWeather) {
        String sqlRequest = "INSERT INTO city(name, latitude, longitude, dateValue, weatherStatus) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlRequest, city.getName(), city.getLatitude(), city.getLongitude(), cityWeather);
    }

    @Override
    public boolean containsCity(City city) {
        String sqlRequest = "SELECT EXISTS(SELECT 1 FROM city WHERE name = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlRequest, Boolean.class, city.getName()));
    }

    @Override
    public WeatherEntity getWeather(City city, String dateValue) {
        try {
            String sqlRequest = "SELECT weatherStatus FROM city WHERE name = ?";
            WeatherEntity result = jdbcTemplate.queryForObject(sqlRequest, weatherStatusRowMapper, city.getName());
            if (result == null) throw new NoInfoAboutSuchCityException();
            return result;
        } catch (DatabaseSelectException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseSelectException(e);
        }
    }

    @Override
    public void clear() {
        jdbcTemplate.update("TRUNCATE city");
    }

    @Override
    public int length() {
        return jdbcTemplate.query("SELECT COUNT(*) FROM city", weatherRowMapper).size();
    }
}
