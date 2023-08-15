package org.mjjaen.datastructure.utils;

public class MyInteger2 {
	private Integer number;

	public MyInteger2(Integer number) {
		super();
		this.number = number;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	@Override
	public String toString() {
		return this.number.toString();
	}
}
