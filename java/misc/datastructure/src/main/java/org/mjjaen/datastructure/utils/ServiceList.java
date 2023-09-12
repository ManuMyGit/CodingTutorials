package org.mjjaen.datastructure.utils;

import java.util.Iterator;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ServiceList {
	private final ListConfiguration listConfiguration;
	
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
		list.stream().forEach(element -> log.info(element + " "));
		log.info("\n");
	}
}
