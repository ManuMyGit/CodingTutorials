package org.mjjaen.designpatterns.creational.singleton.businessObject;

import java.io.Serializable;

/*
 * A veces, en sistemas distribuidos, se necesita que la clase Singleton implemente la interfaz Serializable para que
 * se pueda guardar su estado en un archivo de sistema y recuperarlo más tarde en algún punto en el tiempo. La primera
 * idea que se puede venir a la cabeza es implementar el patrón Singleton con cualquiera de los métodos proporcionados
 * anteriormente (en el ejemplo se utiliza Bill Pugh).
 * Pero esto tiene un problema, y es que cada vez que se deserialice un objeto que se haya serializado, se creará una
 * nueva instancia del mismo. Para resolver el problema basta con añadir el método protected Object readResolve().
 */
public class SingletonSerializable implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private SingletonSerializable() {}
	
	private static class SingletonHelper{
        private static final Motorbike INSTANCE = new MotorbikeImpl();
    }
    
    public static Motorbike getInstance(){
        return SingletonHelper.INSTANCE;
    }
    
    protected Object readResolve() {
        return getInstance();
    }
}