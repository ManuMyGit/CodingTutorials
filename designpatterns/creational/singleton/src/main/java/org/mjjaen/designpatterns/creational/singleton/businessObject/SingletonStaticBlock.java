package org.mjjaen.designpatterns.creational.singleton.businessObject;

/*
 * Similar a SingletonEager, con la diferencia de que el objeto instance se crea en un bloque estático que permite
 * gestionar excepciones
 */
public class SingletonStaticBlock {
	private static final Motorbike instance;
	
	static {
		try {
			instance = new MotorbikeImpl();
		} catch (Exception e) {
			throw new RuntimeException("Error during creation");
		}
	}
	
	private SingletonStaticBlock() {}
	
	public static Motorbike getInstance() {
		return instance;
	}
}
