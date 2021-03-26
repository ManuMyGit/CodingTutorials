package org.mjjaen.designpatterns.creational.abstractfactory.businessObject;

public class FactoryProvider {
    public static AbstractFactory getFactory(String type){
        if("car".equalsIgnoreCase(type)){
            return new CarFactory();
        } else if("task".equalsIgnoreCase(type)){
            return new TaskFactory();
        } else
            return null;
    }
}
