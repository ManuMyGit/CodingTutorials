package org.mjjaen.designpatterns.creational.singleton.businessObject;

/*
 * Esta implementación se utiliza si instance consume mucha memoria y no se tiene que crear hasta que sea necesario
 */
public class SingletonLazy {
	private static Motorbike instance;
	
	private SingletonLazy() {}
	
	public static Motorbike getInstance() {
		if(instance == null)
			instance = new MotorbikeImpl();
		return instance;
	}
}
