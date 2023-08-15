package org.mjjaen.datastructure.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="list")
public class ListConfiguration {
	private Integer size;
	private Integer elementsAtTheMiddle;
	
	public ListConfiguration() {
		super();
	}
	
	public ListConfiguration(Integer size, Integer elementsAtTheMiddle) {
		super();
		this.size = size;
		this.elementsAtTheMiddle = elementsAtTheMiddle;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getElementsAtTheMiddle() {
		return elementsAtTheMiddle;
	}

	public void setElementsAtTheMiddle(Integer elementsAtTheMiddle) {
		this.elementsAtTheMiddle = elementsAtTheMiddle;
	}
}