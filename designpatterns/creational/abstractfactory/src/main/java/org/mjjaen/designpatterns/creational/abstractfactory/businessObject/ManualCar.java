package org.mjjaen.designpatterns.creational.abstractfactory.businessObject;

public class ManualCar extends Car {
    @Override
    public String getTransmision() {
        return "manual";
    }
}
