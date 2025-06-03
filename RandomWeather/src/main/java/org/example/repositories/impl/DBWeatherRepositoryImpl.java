package org.example.repositories.impl;

import org.example.domain.City;
import org.example.Main;
import org.example.entity.WeatherEntity;
import org.example.exceptions.DatabaseSelectException;
import org.example.exceptions.NoInfoAboutSuchCityException;
import org.example.repositories.DBWeatherRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class DBWeatherRepositoryImpl implements DBWeatherRepository {
    private JdbcTemplate jdbcTemplate;

    public DBWeatherRepositoryImpl(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;};

    private final RowMapper<WeatherEntity> weatherRowMapper= (rs, rowNum) ->
            new WeatherEntity(rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("weatherStatus")
            );


    @Override
    public void connect() {
    }
  
    @Override
    public void addCity(City city, String cityWeather) {
        String sqlRequest = "INSERT INTO city(name, weatherStatus) VALUES (?, ?)";
        jdbcTemplate.update(sqlRequest, city.getName(), cityWeather);
    }

    @Override
    public boolean containsCity(City city) {
        String sqlRequest = "SELECT EXISTS(SELECT 1 FROM city WHERE name = ?)";
        //String sqlRequest = "SELECT EXISTS(SELECT 1 FROM CITY WHERE NAME = ? )";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlRequest, Boolean.class, city.getName()));
    }

    @Override
    public String getWeather(City city) {
        try {
            String sqlRequest = "SELECT weatherStatus FROM city WHERE name = ?";
            //List<WeatherEntity> listResult = jdbcTemplate.query(sqlRequest, weatherRowMapper, city.getName());
            WeatherEntity result = jdbcTemplate.queryForObject(sqlRequest, weatherRowMapper, city.getName());
            if (result != null) throw new NoInfoAboutSuchCityException();
            return result.getWeatherStatus();
            //if (listResult.isEmpty()) throw new NoInfoAboutSuchCityException();
            //return listResult.iterator().next().getWeatherStatus();

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
