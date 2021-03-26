package org.mjjaen.designpatterns.creational.singleton.businessObject;

/*
 * Antes de Java 5, el modelo de memoria de java tenía un montón de problemas y las aproximaciones restantes de
 * Singleton solían fallar en ciertos escenarios donde muchos hilos intentaban obtener la instancia de la clase 
 * Singleton de forma simulténea. Bill Pugh creó una aproximación diferente de crear una clase Singleton usando 
 * una clase inner static helper.
 */
public class SingletonBillPugh {
	private SingletonBillPugh() {}
	
	private static class SingletonHelper{
        private static final Motorbike INSTANCE = new MotorbikeImpl();
    }
    
    public static Motorbike getInstance(){
        return SingletonHelper.INSTANCE;
    }
}
