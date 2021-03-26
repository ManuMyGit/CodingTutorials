package org.mjjaen.designpatterns.creational.factorymethod.businessObject;

public class HomeTask extends Task {
    @Override
    public void prepareTask() {
        System.out.println("Preparing home task ...");
    }
}
