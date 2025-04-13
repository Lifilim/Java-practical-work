package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        final Random random = new Random();
        try (Scanner terminalInput = new Scanner(System.in)) {
            Map<String, String> weatherInCity = new HashMap<String, String>();
            while (true) {
                System.out.println("Введите название города:");
                try {
                    String city = terminalInput.nextLine();
                    if (!checkCityName(city)) throw new WrongCityNameException();
                    if (!weatherInCity.containsKey(city)) {
                        String cityWeather = getRandomWeather(random);
                        weatherInCity.put(city, cityWeather);
                    }
                    System.out.println(weatherInCity.get(city));
                } catch (WrongCityNameException e) {
                    System.out.println("\u001B[33m" + "Некорректный запрос. \nНазвание города должно состоять только из символов кириллицы." + "\u001B[0m");
                } finally {
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("\u001B[3mКакая-то ошибка ¯\\_(ツ)_/¯\u001B[0m");
        }
    }

    private static boolean checkCityName(String cityName) {
        return cityName.matches("[а-я]+");
    }

    private static final int[][] temperaturesBounds = {              // https://climate-box.com/ru/textbooks/the-problem-of-climate-change-ru/1-2-%D1%82%D0%B8%D0%BF%D1%8B-%D0%BA%D0%BB%D0%B8%D0%BC%D0%B0%D1%82%D0%BE%D0%B2-%D0%B8-%D0%BA%D0%BB%D0%B8%D0%BC%D0%B0%D1%82%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B8%D0%B5-%D0%BF%D0%BE%D1%8F%D1%81%D0%B0-2/
                {26, 26},
                {20, 30},
                {12, 35},
                {7, 22},
                {0, 40},
                {2, 17},
                {-15, 20},
                {-20, 23},
                {-25, 8},
                {-20, 0},
                {-40, 0}
    };
    private static int getRandomTemperature(Random random) {  // тот же random, что и передаваемый
        int delta = 5;
        int id = random.nextInt(temperaturesBounds.length);
        return temperaturesBounds[id][0] - delta +
                random.nextInt(temperaturesBounds[id][1] - temperaturesBounds[id][0] + 2 + 2 * delta);
    }
    private static final String[] weathers = {"sunny", "cloudy", "foggy", "chance of precipitation"};
    private static String getRandomWeather(Random random) {
        int temperature = getRandomTemperature(random);
        return Integer.toString(temperature) + "°C, " + weathers[random.nextInt(weathers.length)];
    }
}