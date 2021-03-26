package org.mjjaen.designpatterns.creational.singleton.businessObject;

/*
 * Usando reflexión es muy sencillo modificar la visibilidad de los constructores de singleton, haciendo esa
 * visibilidad pública y pudiendo crear tantas instancias como se quieran.
 * Para resolver este problema con la reflexión, Joshua Bloch sugiere el uso del tipo enumerado para diseñar el
 * patrón Singleton ya que Java asegura que cualquier valor enumerado es instanciado únicamente una vez en un programa
 * Java. Ya que los enumerados de Java son accesibles globalmente, este es el patrón singleton. El problema es que el
 * tipo enumerado es en algunos aspectos inflexible. Por ejemplo, no permite la inicialización perezosa. 
 */
public enum SingletonEnum implements Motorbike {
	INSTANCE;
	
	private String brand;
	private String capacity;
	
	private SingletonEnum() {}
	
	public String getBrand() {
		return this.brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getCapacity() {
		return this.capacity;
	}
	
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	
	@Override
	public String toString() {
		String cadena = "";
		if(this.brand != null && !this.brand.equals(""))
			cadena += "Brand: " + this.brand + "\n";
		if(this.capacity != null && !this.capacity.equals(""))
			cadena += "Capacity: " + this.capacity + " cc\n";
		return cadena;
	}
}