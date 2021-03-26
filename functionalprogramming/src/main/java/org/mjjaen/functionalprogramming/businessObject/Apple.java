package org.mjjaen.functionalprogramming.businessObject;

public class Apple {
	private String color;
	private Integer weight;
	private Integer price;
	
	public Apple(String color, Integer weight) {
		this.color = color;
		this.weight = weight;
	}
	
	public Apple(String color, Integer weight, Integer price) {
		this.color = color;
		this.weight = weight;
		this.price = price;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public String toString() {
		String string = "";
		string += "Color: " + this.color + "\n";
		string += "Weight: " + this.weight + "\n";
		return string;
	}
}
