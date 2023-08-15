package org.mjjaen.functionalprogramming.businessObject.predicates;

import java.util.function.Predicate;

import org.mjjaen.functionalprogramming.businessObject.Apple;

public class HeavyApplePredicate implements Predicate<Apple> {
	public boolean test(Apple t) {
		return t.getWeight().compareTo(150) > 0;
	}
}