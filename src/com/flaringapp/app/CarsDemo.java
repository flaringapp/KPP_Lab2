package com.flaringapp.app;

import com.flaringapp.data.models.Car;
import com.flaringapp.data.storage.CarsSourceModel;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarsDemo {

    private final CarsSourceModel sourceModel = InstanceResolver.getCarsSourceModel();

    private final Comparator<Car> carNameComparator = (first, second) ->
            String.CASE_INSENSITIVE_ORDER.compare(first.getName(), second.getName());

    public void run() {
        Collection<Car> firstList = loadCarsList(Constants.INPUT_FILE_1_NAME);
        Collection<Car> secondList = loadCarsList(Constants.INPUT_FILE_2_NAME);

        printLine("First collection:");
        printCars(firstList);

        printLine("Second collection:");
        printCars(secondList);

        runGrouping(firstList, secondList);

        runSubtract(firstList, secondList);

        runSortByName(firstList, secondList);
    }

    private void runGrouping(Collection<Car> firstList, Collection<Car> secondList) {
        printLine("Grouping cars by max speed and printing max " + Constants.GROUPING_DEMO_MAX_COUNT +
                "elements of each group\n");

        Map<Integer, List<Car>> carsByMaxSpeed = Stream.concat(firstList.stream(), secondList.stream())
                .collect(Collectors.groupingBy(Car::getMaxSpeed));

        carsByMaxSpeed.forEach((maxSpeed, cars) -> {
            printLine("- Max speed: " + maxSpeed);
            cars.stream()
                    .limit(Constants.GROUPING_DEMO_MAX_COUNT)
                    .forEach(car -> printLine(car.toString()));
        });

        newLine();
    }

    private void runSubtract(Collection<Car> firstList, Collection<Car> secondList) {
        printLine("Subtracting 2 cars lists\n");

        firstList.stream()
                .filter(car ->
                        secondList.stream().noneMatch(otherCar ->
                                car.getName().equals(otherCar.getName())
                        )
                )
                .forEach(this::printCar);

        newLine();
    }

    private void runSortByName(Collection<Car> firstList, Collection<Car> secondList) {
        printLine("Sorting cars by name in reversed order:\n");

        Stream.concat(firstList.stream(), secondList.stream())
                .sorted(carNameComparator.reversed())
                .forEach(this::printCar);

        newLine();
    }

    private Collection<Car> loadCarsList(String fileName) {
        Collection<Car> cars = Collections.emptyList();
        try {
            cars = sourceModel.loadCars(fileName);
        } catch (Exception e) {
            printLine(e.getMessage());
            exit();
        }
        return cars;
    }

    private void printCars(Collection<Car> cars) {
        cars.forEach(this::printCar);
        newLine();
    }

    private void printCar(Car car) {
        System.out.println(car.toString());
    }

    private void printLine(String line) {
        System.out.println(line);
    }

    private void newLine() {
        System.out.println();
    }

    private void exit() {
        System.exit(0);
    }

}
