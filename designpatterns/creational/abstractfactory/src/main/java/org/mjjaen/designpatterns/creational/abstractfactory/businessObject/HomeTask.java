package org.mjjaen.designpatterns.creational.abstractfactory.businessObject;

public class HomeTask extends Task {
    @Override
    public void prepareTask() {
        System.out.println("Preparing home task ...");
    }
}
