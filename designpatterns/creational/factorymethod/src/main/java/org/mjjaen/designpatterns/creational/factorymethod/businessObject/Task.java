package org.mjjaen.designpatterns.creational.factorymethod.businessObject;

public abstract class Task {
    private Integer execuitonTimeMs;

    public Integer getExecuitonTimeMs() {
        return execuitonTimeMs;
    }

    public void setExecuitonTimeMs(Integer execuitonTimeMs) {
        this.execuitonTimeMs = execuitonTimeMs;
    }

    public abstract void prepareTask();

    public void executeTask() {
        System.out.println("The task took " + this.execuitonTimeMs + " to be executed.");
    }
}
