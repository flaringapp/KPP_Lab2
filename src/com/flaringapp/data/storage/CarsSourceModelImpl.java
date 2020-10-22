package com.flaringapp.data.storage;

import com.flaringapp.app.Constants;
import com.flaringapp.app.Utils;
import com.flaringapp.data.models.Car;
import com.sun.istack.internal.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class CarsSourceModelImpl implements CarsSourceModel {

    public static final String SPACE = " ";

    @Override
    public Collection<Car> loadCars(String fileName) throws FileNotFoundException {
        File file = new File(Constants.INPUT_FILES_DIR, fileName);

        Collection<Car> cars = new ArrayList<>();

        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            Car car = parseLine(line);
            if (car == null) throw new InvalidCarDataFormatException(line);

            cars.add(car);
        }

        scanner.close();

        return cars;
    }

    @Nullable
    private Car parseLine(String line) throws InvalidCarDataFormatException {
        String[] params = line.split(SPACE);
        int size = params.length;

        if (size < 3) return null;

        String maxSpeed = params[size - 1];
        String price = params[size - 2];

        if (!Utils.isOnlyNumbers(price)) return null;
        if (!Utils.isOnlyNumbers(maxSpeed)) return null;

        StringBuilder nameBuilder = new StringBuilder();
        nameBuilder.append(params[0]);
        for (int i = 1; i < size - 2; i++) {
            nameBuilder
                    .append(" ")
                    .append(params[i]);
        }

        return new Car(
                nameBuilder.toString(),
                Integer.parseInt(price),
                Integer.parseInt(maxSpeed)
        );
    }

}
