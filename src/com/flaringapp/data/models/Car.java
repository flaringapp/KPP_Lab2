package com.flaringapp.data.models;

public class Car {

    private final String name;
    private final int price;
    private final int maxSpeed;

    public Car(String name, int price, int maxSpeed) {
        this.name = name;
        this.price = price;
        this.maxSpeed = maxSpeed;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public String toString() {
        return "" +
                String.format("%-30s", this.name) + "$" +
                String.format("%-10s", this.price) +
                String.format("%-4s", this.maxSpeed) + "km/h";
    }

}
