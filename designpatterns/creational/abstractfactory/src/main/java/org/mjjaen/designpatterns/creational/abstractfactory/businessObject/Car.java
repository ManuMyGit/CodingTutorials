package org.mjjaen.designpatterns.creational.abstractfactory.businessObject;

public abstract class Car {
    protected String transmision;

    public void turnOnEngine() {
        System.out.println("The car with transmision " + getTransmision() + " has been turned on");
    }

    public abstract String getTransmision();
}
