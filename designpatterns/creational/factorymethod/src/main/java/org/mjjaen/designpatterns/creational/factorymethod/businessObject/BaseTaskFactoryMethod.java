package org.mjjaen.designpatterns.creational.factorymethod.businessObject;

public abstract class BaseTaskFactoryMethod {
    public enum TASKTYPE {
        COMPUTER, WORK, HOME
    }

    public abstract Task createTask(TASKTYPE taskType, Integer executionTime);
}
