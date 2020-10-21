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
        Collection<Car> carsList = loadCarsList(Constants.INPUT_FILE_CARS_NAME);

        Collection<Car> carsToDelete = loadCarsList(Constants.INPUT_FILE_CARS_TO_DELETE_NAME);

        Collection<Car> firstList = loadCarsList(Constants.INPUT_FILE_1_NAME);
        Collection<Car> secondList = loadCarsList(Constants.INPUT_FILE_2_NAME);

        printLine("Cars list:");
        printCars(carsList);

        Map<Integer, List<Car>> groupedCars = runGrouping(carsList);

        printLine("Cars to delete:");
        printCars(carsToDelete);

        runSubtract(groupedCars, carsToDelete);

        printLine("Cars from file 1:");
        printCars(firstList);

        printLine("Cars from file 2:");
        printCars(secondList);

        Collection<Car> mergedCars = runMergeSortByName(firstList, secondList);

        runCountInBoundOfPrice(mergedCars, Constants.PRICE_MIN, Constants.PRICE_MAX);
    }

    /**
     * @return map of max speed - list of cars with this ,ax speed
     */
    private Map<Integer, List<Car>> runGrouping(Collection<Car> carsList) {
        printLine("Grouping cars by max speed and printing max " + Constants.GROUPING_DEMO_MAX_COUNT +
                "elements of each group\n");

        Map<Integer, List<Car>> carsByMaxSpeed = carsList.stream()
                .collect(Collectors.groupingBy(Car::getMaxSpeed));

        carsByMaxSpeed.forEach((maxSpeed, cars) -> {
            printLine("- Max speed: " + maxSpeed);
            cars.stream()
                    .limit(Constants.GROUPING_DEMO_MAX_COUNT)
                    .forEach(this::printCar);
        });

        newLine();

        return carsByMaxSpeed;
    }

    private void runSubtract(
            Map<Integer, List<Car>> groupedCars,
            Collection<Car> carsToDelete
    ) {
        printLine("Subtracting cars to delete from grouped cars map by max speed\n");

        groupedCars.forEach((maxSpeed, cars) -> {
                    cars.removeIf(car ->
                            carsToDelete.stream()
                                    .anyMatch(carToDelete ->
                                            car.getName().equals(carToDelete.getName())
                                    )
                    );
                    printLine("- Max speed: " + maxSpeed);
                    cars.forEach(this::printCar);
                }
        );

        newLine();
    }

    private Collection<Car> runMergeSortByName(Collection<Car> firstList, Collection<Car> secondList) {
        printLine("Sorting cars by name in reversed order:\n");

        List<Car> mergedCars = Stream.concat(firstList.stream(), secondList.stream())
                .sorted(carNameComparator.reversed())
                .collect(Collectors.toList());

        mergedCars.forEach(this::printCar);

        newLine();

        return mergedCars;
    }

    private void runCountInBoundOfPrice(Collection<Car> cars, int priceMin, int priceMax) {
        printLine("Searching for cars with price more that " + priceMin + " and less than " + priceMax);

        cars.stream()
                .filter(car -> car.getPrice() >= priceMin && car.getPrice() <= priceMax)
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
