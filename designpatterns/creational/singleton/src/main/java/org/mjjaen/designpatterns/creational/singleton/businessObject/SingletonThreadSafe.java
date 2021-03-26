package org.mjjaen.designpatterns.creational.singleton.businessObject;

/*
 * Esta implementación es como la implementación perezosa (SingletonLazy) pero ofrece seguridad entre hilos,
 * asegurando que en una aplicación multihilo se respetará la sincronización
 */
public class SingletonThreadSafe {
	private static Motorbike instance;
	
	private SingletonThreadSafe() {}
	
	/*
	 * Esta implementación reduce el rendimiento por el coste de la sincronización, que afecta a todo el método
	 * y afecta al rendimiento independientemente de si la instancia ya está creada o no.
	 */
	public static synchronized Motorbike getInstance() {
		if(instance == null)
			instance = new MotorbikeImpl();
		return instance;
	}
	
	/*
	 * Este segundo método aumenta el rendimiento ya que se produce un doble check. El coste de la sincronización 
	 * sólo aplica si la instancia no está creada.
	 */
	public static synchronized Motorbike getInstanceUsingDoubleChecking() {
		if(instance == null) {
			synchronized (Motorbike.class) {
				if(instance == null)
					instance = new MotorbikeImpl();
			}
		}
		return instance;
	}
}
