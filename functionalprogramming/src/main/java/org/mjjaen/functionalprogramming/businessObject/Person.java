package org.mjjaen.functionalprogramming.businessObject;

import java.time.LocalDate;
import java.util.Comparator;

public class Person {
	private String name;
    private LocalDate birthday;
    
	public Person() {
		super();
	}
	
	public Person(String name, LocalDate birthday) {
		super();
		this.name = name;
		this.birthday = birthday;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}
	
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String string = "";
		string += "Person:\n";
		string += "     Name: " + this.name + "\n";
		string += "     Birthday: " + this.birthday;
		return string;
	}
	
	public static int compareByAge(Person a, Person b) {
		return a.getBirthday().compareTo(b.getBirthday());
	}
	
	public static Comparator<Person> comparator = new Comparator<Person>() {
		public int compare(Person person1, Person person2) {
			return person1.getBirthday().compareTo(person2.getBirthday());
		}
	};
}
