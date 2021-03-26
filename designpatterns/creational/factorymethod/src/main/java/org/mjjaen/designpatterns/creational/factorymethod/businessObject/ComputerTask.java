package org.mjjaen.designpatterns.creational.factorymethod.businessObject;

public class ComputerTask extends Task {
    @Override
    public void prepareTask() {
        System.out.println("Preparing computer task ...");
    }
}
