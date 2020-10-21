package com.flaringapp.data.storage;

import com.flaringapp.data.models.Car;

import java.io.FileNotFoundException;
import java.util.Collection;

public interface CarsSourceModel {

    Collection<Car> loadCars(String fileName) throws FileNotFoundException;

}
