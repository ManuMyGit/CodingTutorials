package org.mjjaen.designpatterns.creational.abstractfactory.businessObject;

public class CarFactory implements AbstractFactory<Car> {
    @Override
    public Car create(String type) {
        Car car;
        switch (type) {
            case "manual":
                car = new ManualCar();
                break;
            case "automatic":
                car = new AutomaticCar();
                break;
            default:
                throw new IllegalArgumentException("No such car.");
        }
        return car;
    }
}
