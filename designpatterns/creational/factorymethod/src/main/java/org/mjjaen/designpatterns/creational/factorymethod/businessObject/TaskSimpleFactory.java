package org.mjjaen.designpatterns.creational.factorymethod.businessObject;

public class TaskSimpleFactory {
	public static Task createTask(String type){
        Task task;
        switch (type.toLowerCase()) {
            case "computer":
                task = new ComputerTask();
                break;
            case "work":
                task = new WorkTask();
                break;
            case "home":
                task = new HomeTask();
                break;
            default:
            	throw new IllegalArgumentException("No such task.");
        }
        return task;
	}
}
