package org.mjjaen.designpatterns.creational.abstractfactory.businessObject;

public interface AbstractFactory<T> {
    T create(String type) ;
}
