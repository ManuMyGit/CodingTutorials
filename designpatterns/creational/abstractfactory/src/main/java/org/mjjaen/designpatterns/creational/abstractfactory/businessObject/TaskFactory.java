package org.mjjaen.designpatterns.creational.abstractfactory.businessObject;

public class TaskFactory implements AbstractFactory<Task> {
    @Override
    public Task create(String type) {
        Task task;
        switch (type) {
            case "computer":
                task = new ComputerTask();
                break;
            case "home":
                task = new HomeTask();
                break;
            case "work":
                task = new WorkTask();
                break;
            default:
                throw new IllegalArgumentException("No such task.");
        }
        return task;
    }
}