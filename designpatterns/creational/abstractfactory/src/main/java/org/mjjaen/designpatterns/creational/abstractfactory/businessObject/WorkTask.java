package org.mjjaen.designpatterns.creational.abstractfactory.businessObject;

public class WorkTask extends Task{
    @Override
    public void prepareTask() {
        System.out.println("Preparing work task ...");
    }
}
