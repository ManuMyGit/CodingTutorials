package org.mjjaen.datastructure.utils;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceList {
	@Autowired
	private ListConfiguration listConfiguration;
	
	@TimerAnnotation
	public void initializeList(final List<Integer> list) {
		for(int i = 0; i < listConfiguration.getSize(); i ++) {
			list.add(i);
		}
	}
	
	@TimerAnnotation
	public Integer getElement(final List<Integer> list, final Integer position) {
		return list.get(position);
	}
	
	@TimerAnnotation
	public void removeElementsFromListWithIterator(final List<Integer> list) {
		Iterator<Integer> iterator = list.iterator();
		while(iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
	}
	
	@TimerAnnotation
	public void removeElementsFromList(final List<Integer> list) {
		for(int i = 0; i < list.size(); i ++) {
			list.remove(i);
		}
	}
	
	@TimerAnnotation
	public void addElementsToTheList(final List<Integer> list) {
		for(int i = 0; i < listConfiguration.getElementsAtTheMiddle(); i ++) {
			list.add(list.size() / 2, i);
		}
	}
	
	
	public void printList(final List<Integer> list) {
		list.stream().forEach(element -> System.out.print(element + " "));
		System.out.println();
	}

	public ListConfiguration getListConfiguration() {
		return listConfiguration;
	}

	public void setListConfiguration(ListConfiguration listConfiguration) {
		this.listConfiguration = listConfiguration;
	}
}
