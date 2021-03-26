package org.mjjaen.designpatterns.creational.singleton.businessObject;

/*
 * Esta implementación de Singleton se puede usar si instance no consume muchos recursos y no se esperan excepciones
 */
public class SingletonEager {
	/*
	 * Se instancia la clase de forma pronta, en el momento que se crea SingletonEager
	 */
	private static final Motorbike instance = new MotorbikeImpl();
	
	private SingletonEager() {}
	
	public static Motorbike getInstance() {
		return instance;
	}
}
