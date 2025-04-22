package org.example.repositories.impl;

import java.util.Random;
import org.example.repositories.SomeTemperatureRepository;

public class SomeTemperatureRepositoryImpl implements SomeTemperatureRepository {
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
