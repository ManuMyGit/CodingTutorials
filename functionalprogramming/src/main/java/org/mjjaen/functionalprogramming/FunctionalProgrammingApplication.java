package org.mjjaen.functionalprogramming;

import org.mjjaen.functionalprogramming.businessObject.Apple;
import org.mjjaen.functionalprogramming.businessObject.Person;
import org.mjjaen.functionalprogramming.businessObject.functionalinterface.SquareInterface;
import org.mjjaen.functionalprogramming.businessObject.predicates.GreenApplePredicate;
import org.mjjaen.functionalprogramming.businessObject.predicates.HeavyApplePredicate;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionalProgrammingApplication {
	private static final String STRATEGY_COLOR = "STRATEGY_COLOR";
	private static final String STRATEGY_PESO = "STRATEGY_PESO";

    public static void main( String[] args ) {
    	exampleFunctionalInterface();
    	exampleHighOrderFunction();
    	exampleMethodReferenceClass();
    	exampleMethodReferenceInstance();
    	exampleMethodReferenceType();
    	exampleMethodReferenceEmptyConstructor();
    	exampleMethodReferenceNotEmptyConstructor();
    	exampleLambda();
    	exampleStream();
    	exampleOptional();
    	examplePredicate();
    	exampleFunctions();
    	exampleComparators();
    	exampleRecursion();
    }

	private static Person[] initializeArrayPerson() {
		Person[] personArray = new Person[5];
		Person p1 = new Person("Person 1", LocalDate.of(1981, 7, 24));
		Person p2 = new Person("Person 2", LocalDate.of(1982, 1, 18));
		Person p3 = new Person("Person 3", LocalDate.of(1979, 7, 5));
		Person p4 = new Person("Person 4", LocalDate.of(1984, 11, 1));
		Person p5 = new Person("Person 5", LocalDate.of(1982, 1, 2));
		personArray[0] = p1;
		personArray[1] = p2;
		personArray[2] = p3;
		personArray[3] = p4;
		personArray[4] = p5;
		return personArray;
	}

	private static void printArray(Object[] array) {
		Stream.of(array).forEach(element -> System.out.println(element));
	}

	private static void exampleFunctionalInterface() {
		System.out.println("\nMethod exampleFunctionalInterface:\n");
		int a = 5;
		// lambda expression to define the calculate method
		SquareInterface s = (int x) -> x * x;
		// parameter passed and return type must be same as defined in the prototype
		int ans = s.calculate(a);
		System.out.println(ans);
	}

	private static void exampleHighOrderFunction() {
		System.out.println("\nMethod exampleHighOrderFunction:\n");
    	List<Integer> numbers = Arrays.asList(1, 2, 3, 9, 5, 3, 7, 5);
		Collections.sort(numbers, new Comparator<Integer>() {
			@Override
			public int compare(Integer n1, Integer n2) {
				return n1.compareTo(n2);
			}
		});
		//Using lambdas to build the same functionality
		Collections.sort(numbers, (n1, n2) -> n1.compareTo(n2));
		printArray(numbers.toArray());
	}
    
    private static void exampleMethodReferenceClass() {
    	System.out.println("\nMethod exampleMetodoReferenceClass:\n");
    	Person[] personArray = initializeArrayPerson();
    	System.out.println("Array before ordering:");
    	printArray(personArray);
        //Order an array using lambdas
        Arrays.sort(personArray, (a, b) -> Person.compareByAge(a, b));
        System.out.println("Array after ordering:");
        printArray(personArray);
        personArray = initializeArrayPerson();
        System.out.println("Array before ordering:");
    	printArray(personArray);
        //Order an array using reference methods
        Arrays.sort(personArray, Person::compareByAge);
        System.out.println("Array after ordering:");
        printArray(personArray);
    }
    
    private static void exampleMethodReferenceInstance() {
    	System.out.println("\nMethod exampleMetodoReferenceInstance:\n");
    	Comparator<Person> comparator = Person.comparator;
    	Person[] personArray = initializeArrayPerson();
    	System.out.println("Array before ordering:");
    	printArray(personArray);
        //Order an array using lambdas
        Arrays.sort(personArray, (a, b) -> comparator.compare(a, b));
        System.out.println("Array after ordering:");
        printArray(personArray);
        personArray = initializeArrayPerson();
        System.out.println("Array before ordering:");
    	printArray(personArray);
        //Order an array using reference methods
        Arrays.sort(personArray, comparator::compare);
        System.out.println("Array after ordering:");
        printArray(personArray);
    }
    
    private static void exampleMethodReferenceType() {
    	System.out.println("\nMethod exampleMetodoReferenceType:\n");
    	String[] strings = {"asdf", "asdfas2", "adsfh23", "xvzcsd", "234adsf", "jsdf34"};
    	System.out.println("Array before ordering:");
    	printArray(strings);
        //Order an array using lambdas
        Arrays.sort(strings, (a, b) -> a.compareToIgnoreCase(b));
        System.out.println("Array after ordering:");
    	printArray(strings);
        String[] anotherStrings = {"asdf", "asdfas2", "adsfh23", "xvzcsd", "234adsf", "jsdf34"};
        System.out.println("Array before ordering:");
        printArray(anotherStrings);
        //Order an array using reference methods
        Arrays.sort(anotherStrings, String::compareToIgnoreCase);
        System.out.println("Array after ordering:");
    	printArray(anotherStrings);
    }
    
    private static void exampleMethodReferenceEmptyConstructor() {
    	System.out.println("\nMethod exampleMetodoReferenceEmptyConstructor:\n");
    	//Create a person using lambdas
        Supplier<Person> s1 = () -> new Person();
        Person p1 = s1.get();
        System.out.println(p1);
        //Create a persons using reference methods
        Supplier<Person> s2 = Person::new;
        Person p2 = s2.get();
        System.out.println(p2);
    }
    
    private static void exampleMethodReferenceNotEmptyConstructor() {
    	System.out.println("\nMethod exampleMetodoReferenceNotEmptyConstructor:\n");
    	//Create a person using lambdas
        BiFunction<String, LocalDate, Person> f1 = (a, b) -> new Person(a, b);
        Person p1 = f1.apply("Person 1", LocalDate.of(1981, 7, 24));
        System.out.println(p1);
        //Create a persons using reference methods
        BiFunction<String, LocalDate, Person> f2 = Person::new;
        Person p2 = f2.apply("Person 2", LocalDate.of(1982, 11, 12));
        System.out.println(p2);
    }
    
    private static void exampleLambda() {
    	System.out.println("\nMethod exampleLambda:\n");
    	System.out.println("Initial array:");
    	List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 1, 5);
    	for(Integer number : list) {
    		System.out.print(number + " ");
    	}
    	System.out.println("");
    	System.out.println("First element");
    	list.stream().findFirst().ifPresent(System.out::println);
    	System.out.println("Filter an element");
    	list.stream().filter(element -> element == 5).forEach(System.out::println);
    	System.out.println("Distinct elements");
    	list.stream().distinct().forEach(a -> System.out.print(a + " "));
    	System.out.println("Duplicate value of the elements");
    	list.stream().map(s -> s * 2).forEach(a -> System.out.print(a + " "));
    	System.out.println("\nRemove duplicate elements and then duplicate values");
    	list.stream().distinct().map(s -> s * 2).forEach(a -> System.out.print(a + " "));
    }
    
    private static void exampleStream() {
    	System.out.println("\nMethod exampleStream:\n");
    	List<String> list = new Vector<>();
    	list.add("Workshop");
    	list.add("Workshop Lambdas and Stream API");
    	long cont = list.stream()               //List<String> --> Stream<String>
    			.map(s -> s.split(""))	//Stream<String> --> Stream<String[]> -> 2 elements
				.distinct()                     //Stream<String[]> --> Stream<String[]>
    			.count();                       //Stream<String[]> --> long
    	System.out.println("Counter");
    	System.out.println(cont);
    	cont =
				list.stream()                    //List<String> --> Stream<String>
    			.map(s -> s.split(""))    //Stream<String> --> Stream<String[]> -> 2 elements
    			.flatMap(Arrays::stream)        //Stream<String[]> --> Stream<String> -> 39 elements (flattered)
    			.distinct()                     //Stream<String> --> Stream<String> -> 20 elements
    			.count();                       //Stream<String> --> long
    	System.out.println("Counter again");
    	System.out.println(cont);
    }
    
    private static void exampleOptional() {
    	Optional<Person> optional = Optional.ofNullable(null);
    	System.out.println("optional is not null but the object inside the optional is null. You can use optional without throwing NullPointerException");
    	System.out.println(optional);
    	System.out.println(optional.orElse(null));
    	System.out.println("You can check if the object inside the optional is null");
    	if(optional.isPresent())
    		System.out.println("The object inside the optional is not null");
    	else
    		System.out.println("The object inside the optional is null");
    	optional = Optional.of(new Person());
    	if(optional.isPresent())
    		System.out.println("The object inside the optional is not null");
    	else
    		System.out.println("The object inside the optional is null");
    }
    
    //Functions include apply, andThen and compose methods
    private static void exampleFunctions() {
    	System.out.println("\nMethod exampleFunctions:\n");
    	
    	//Unique functions
    	Function<String, Integer> f1 = String::length;
    	Function<Integer, Integer> f2 = i -> i * 2;
    	
    	//Functions composition
    	Function<String, Integer> f3 = f1.andThen(f2);
    	System.out.println(f3.apply("example"));
    	
    	//Simple bifunctions
    	BiFunction<String, String, Integer> bf1 = (a, b) -> a.length() + b.length();
    	
    	//Composition of bifunction with function
    	BiFunction<String, String, Integer> bf2 = bf1.andThen(f2);
    	System.out.println(bf2.apply("example1", "example2"));

    	//Currying: technique of converting a function that takes multiple arguments into a sequence of functions that take a single argument
		Function<Double, Function<Double, Double>> weight = mass -> gravity -> mass * gravity;

		Function<Double, Double> weightOnEarth = weight.apply(9.81);
		System.out.println("My weight on Earth: " + weightOnEarth.apply(60.0));

		Function<Double, Double> weightOnMars = weight.apply(3.75);
		System.out.println("My weight on Mars: " + weightOnMars.apply(60.0));
    }
    
    //Comparatos include compare, reserved, thenComparing (overloaded), thenComparingDouble, thenComparingInt, thenComparingLong methods
    private static void exampleComparators() {
    	System.out.println("\nMethod exampleComparators:\n");
    	
    	//Unique comparators
    	Comparator<String> c1 = (a, b) -> a.length() - b.length();
    	Comparator<String> c2 = String::compareTo;
    	
    	//Comparator composition. First c1 is used and then, if equals, c2 is used.
    	Comparator<String> c3 = c1.thenComparing(c2);
    	System.out.println(c3.compare("string2", "string1"));
    }

	private static void exampleRecursion() {
		System.out.println("\nMethod exampleRecursion:\n");
		Integer factorial1 = factorial(5);
		Integer factorial2 = factorial(5, 1);
		System.out.println("5 factorial: " + factorial1);
		System.out.println("5 factorial: " + factorial2);
	}

	private static Integer factorial(Integer number) {
		return (number == 1) ? 1 : number * factorial(number - 1);
	}

	private static Integer factorial(Integer number, Integer result) {
		return (number == 1) ? result : factorial(number - 1, result * number);
	}

	private static void examplePredicate() {
		System.out.println("\nMethod examplePredicate:\n");

		List<String> list = new Vector<>();
		list.add("Subject");
		list.add("Anduril");
		list.add("Ar");
		list.add("Beard");
		list.add("Bea");
		list.add("Goodbye");

		//Unique predicates
		Predicate<String> p1 = s -> s.length() > 3;
		Predicate<String> p2 = s -> s.charAt(0) == 'A';
		Predicate<String> p3 = s -> s.compareToIgnoreCase("Subject") == 0;

		//Predicates composition, fromt left to right
		Predicate<String> p4 = p1.and(p2).or(p3).negate();

		//Use of predicates
		System.out.println("Apply p1 predicate");
		List<String> res = list.stream().filter(p1).collect(Collectors.toList());
		res.stream().forEach(System.out::println);
		System.out.println("Apply p2 predicate");
		res = list.stream().filter(p2).collect(Collectors.toList());
		res.stream().forEach(System.out::println);
		System.out.println("Apply p3 predicate");
		res = list.stream().filter(p3).collect(Collectors.toList());
		res.stream().forEach(System.out::println);
		System.out.println("Apply p4 predicate");
		res = list.stream().filter(p4).collect(Collectors.toList());
		res.stream().forEach(System.out::println);

		//Comparators
		Apple apple1 = new Apple("red", 60);
		Apple apple2 = new Apple("green", 80);
		Apple apple3 = new Apple("blue", 100);
		Apple apple4 = new Apple("white", 120);
		Apple apple5 = new Apple("black", 140);
		Apple apple6 = new Apple("yellow", 160);
		Apple apple7 = new Apple("grey", 180);
		Apple apple8 = new Apple("pink", 200);
		Apple apple9 = new Apple("purple", 210);

		List<Apple> lista = new Vector<>();
		lista.add(apple1);
		lista.add(apple2);
		lista.add(apple3);
		lista.add(apple4);
		lista.add(apple5);
		lista.add(apple6);
		lista.add(apple7);
		lista.add(apple8);
		lista.add(apple9);

		List<Apple> result = filterApples(lista, getFilterStrategy(STRATEGY_COLOR));
		System.out.println("Color filtering");
		result.stream().forEach(p -> System.out.println(p));
		result = filterApples(lista, getFilterStrategy(STRATEGY_PESO));
		System.out.println("Weight filtering");
		result.stream().forEach(p -> System.out.println(p));

		//Lambdas
		result = filterApplesLambda(lista, getFilterStrategyLambda(STRATEGY_COLOR));
		System.out.println("Color filtering");
		result.stream().forEach(p -> System.out.println(p));
		result = filterApplesLambda(lista, getFilterStrategyLambda(STRATEGY_PESO));
		System.out.println("Weight filtering");
		result.stream().forEach(p -> System.out.println(p));
	}

	private static List<Apple> filterApples(List<Apple> list, Predicate<Apple> predicate) {
		List<Apple> result = new Vector<>();
		for(Apple apple : list) {
			if(predicate.test(apple))
				result.add(apple);
		}
		return result;
	}

	/*
	 * And example with functional programming
	 */
	private static List<Apple> filterApplesLambda(List<Apple> list, Predicate<Apple> predicate) {
		List<Apple> result = list.stream().filter(predicate).collect(Collectors.toList());
		return result;
	}

	private static Predicate<Apple> getFilterStrategy(String strategy) {
		if(strategy.equals(STRATEGY_COLOR))
			return new GreenApplePredicate();
		else if(strategy.equals(STRATEGY_PESO))
			return new HeavyApplePredicate();
		else
			throw new RuntimeException("No such strategy");
	}

	/*
	 * With lambdas we have a clean code and there is no need of AppleGreenColorPredicate and AppleHeavyPesoPredicate classes
	 */
	private static Predicate<Apple> getFilterStrategyLambda(String strategy) {
		if(strategy.equals(STRATEGY_COLOR))
			return p -> p.getColor().equals("green");
		else if(strategy.equals(STRATEGY_PESO))
			return p -> p.getWeight().compareTo(150) > 0;
		else
			throw new RuntimeException("No such strategy");
	}
}