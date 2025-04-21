package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        try (Scanner terminalInput = new Scanner(System.in)) {
            WeatherRepositoryBase weatherRepository = new RandomWeatherRepository();
            while (true) {
                System.out.println("Введите название города:");
                try {
                    City city = new City();
                    city.inputName(terminalInput);
                    weatherRepository.addCity(city);
                    System.out.println(weatherRepository.getCityWeather(city));
                } catch (WrongCityNameException e) {
                    System.out.println("\u001B[33m" + "Некорректный запрос. \nНазвание города должно состоять только из символов кириллицы." + "\u001B[0m");
                } catch (NoInfoAboutSuchCityException e) {
                    System.out.println("\u001B[33m" + "Информации об этом городе нет в базе. Добавьте её." + "\u001B[0m");
                } finally {
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("\u001B[3mКакая-то ошибка ¯\\_(ツ)_/¯\u001B[0m");
        }
    }

}

interface TemperatureServiceBase {
    int getTemperature();
}
class RandomTemperatureService implements TemperatureServiceBase {
    private static final Random random = new Random();
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
    static final int delta = 5;

    @Override
    public int getTemperature() {  // тот же random, что и передаваемый
        int id = random.nextInt(temperaturesBounds.length);
        return temperaturesBounds[id][0] - delta +
                random.nextInt(temperaturesBounds[id][1] - temperaturesBounds[id][0] + 2 + 2 * delta);
    }
}

interface WeatherServiceBase {
    String getWeather();
}
class RandomWeatherService implements WeatherServiceBase {
    private static final Random random = new Random();
    private static final String[] weathers = {"sunny", "cloudy", "foggy", "chance of precipitation"};

    @Override
    public String getWeather() {
        TemperatureServiceBase temperatureService = new RandomTemperatureService();
        int temperature = temperatureService.getTemperature();
        return Integer.toString(temperature) + "°C, " + weathers[random.nextInt(weathers.length)];
    }
}



class City {        // TwT
    private String name;
    public void inputName(Scanner terminalInput) {
        name = terminalInput.nextLine();
        if (!this.checkName()) throw new WrongCityNameException();
    }
    public String getName() {
        return name;
    }
    public boolean checkName() {
        return name.matches("[а-я]+");
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return name.equals(city.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

interface GetWeatherRepositoryBase {
    String getCityWeather(City city);
}
interface AddWeatherRepositoryBase {
    void addCity(City city);
}
interface WeatherRepositoryBase extends GetWeatherRepositoryBase, AddWeatherRepositoryBase {
}
class RandomWeatherRepository implements WeatherRepositoryBase {
    private static final WeatherServiceBase weatherService = new RandomWeatherService();
    private static final Map<City, String> weatherInCityData = new HashMap<>();

    @Override
    public void addCity(City city) {
        if (!weatherInCityData.containsKey(city)) {
            String cityWeather = weatherService.getWeather();
            weatherInCityData.put(city, cityWeather);
        }
    }
    @Override
    public String getCityWeather(City city) {
        if (!weatherInCityData.containsKey(city)) throw new NoInfoAboutSuchCityException();
        return weatherInCityData.get(city);
    }
}