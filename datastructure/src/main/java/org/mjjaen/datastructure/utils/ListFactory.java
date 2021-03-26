package org.mjjaen.datastructure.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ListFactory {
	public static final String ARRAYLIST = "ARRAYLIST";
	public static final String LINKEDLIST = "LINKEDLIST";
	
	public List<Integer> createList(final String type) {
		List<Integer> list;
		switch(type) {
			case "ARRAYLIST":
				list = new ArrayList<Integer>();
				break;
			case "LINKEDLIST":
				list = new LinkedList<Integer>();
				break;
			default:
				list = new ArrayList<Integer>();
				break;
		}
		return list;
	}
}