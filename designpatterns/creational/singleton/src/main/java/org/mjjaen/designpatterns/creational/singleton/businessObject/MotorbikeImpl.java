package org.mjjaen.designpatterns.creational.singleton.businessObject;

public class MotorbikeImpl implements Motorbike {
	private String brand;
	private String capacity;
	
	public MotorbikeImpl() {
		super();
	}
	
	public MotorbikeImpl(String brand, String model) {
		super();
		this.brand = brand;
		this.capacity = capacity;
	}
	
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