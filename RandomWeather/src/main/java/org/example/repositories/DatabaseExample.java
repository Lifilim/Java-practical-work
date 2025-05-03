package org.example.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/*
CREATE TABLE IF NOT EXISTS cities (
    id            BIGSERIAL,
    city          TEXT NOT NULL,
    CONSTRAINT cities_pk PRIMARY KEY (id)
);
*/


public class DatabaseExample {
    public static void main(String[] args) throws ClassNotFoundException {

        String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "postgres";

        Class.forName("org.postgresql.Driver");

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            Statement statement = connection.createStatement();

            // Почему лучше не использовать * в селекте?
            ResultSet allCities = statement.executeQuery("SELECT id, city FROM cities");

            while (allCities.next()) {
                long id = allCities.getLong("id");
                String city = allCities.getString("city");
                System.out.printf("Город %s имеет id %s\n", city, id);
            }
            allCities.close();
            statement.close();

            System.out.println("***");

            String sql = "INSERT INTO cities(city) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Почему не можем использовать просто конкатенацию строк?
            preparedStatement.setString(1, "Самара");

            int rowsInserted = preparedStatement.executeUpdate();

            System.out.println(rowsInserted + " город вставили");

            // Какой индекс будем использовать, когда хотим найти город "Саратов"?
            Statement statementAfterInsert = connection.createStatement();

            ResultSet allCitiesAfterInsert = statementAfterInsert.executeQuery("SELECT id, city FROM cities");

            while (allCitiesAfterInsert.next()) {
                int id = allCitiesAfterInsert.getInt("id");
                String city = allCitiesAfterInsert.getString("city");
                System.out.printf("Город %s имеет id %s\n", city, id);
            }
            allCitiesAfterInsert.close();
            statementAfterInsert.close();

            System.out.println("***");

            try (PreparedStatement insertInTransaction = connection.prepareStatement(sql)) {
                connection.setAutoCommit(false);
                insertInTransaction.setString(1, "Саратов-в-транзакции");
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                if (connection != null) {
                    try {
                        System.err.print("Откатываем транзакцию");
                        connection.rollback();
                    } catch (SQLException rollbackException) {
                        rollbackException.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
