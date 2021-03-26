package org.mjjaen.designpatterns.creational.factorymethod.businessObject;

public class TaskFactoryMethod extends BaseTaskFactoryMethod {
    @Override
    public Task createTask(TASKTYPE taskType, Integer executionTime) {
        Task task;
        switch (taskType) {
            case COMPUTER:
                task = new ComputerTask();
                break;
            case HOME:
                task = new HomeTask();
                break;
            case WORK:
                task = new WorkTask();
                break;
            default:
                throw new IllegalArgumentException("No such task.");
        }
        task.setExecuitonTimeMs(executionTime);
        task.prepareTask();
        task.executeTask();
        return task;
    }
}
