package org.mjjaen.designpatterns.creational.abstractfactory.businessObject;

public class ComputerTask extends Task {
    @Override
    public void prepareTask() {
        System.out.println("Preparing computer task ...");
    }
}
