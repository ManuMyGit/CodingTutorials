package org.mjjaen.functionalprogramming.businessObject.predicates;

import java.util.function.Predicate;

import org.mjjaen.functionalprogramming.businessObject.Apple;

public class GreenApplePredicate implements Predicate<Apple> {
	public boolean test(Apple t) {
		return t.getColor().equals("green");
	}
}