package org.mjjaen.designpatterns.creational.builder.businessObject;

import java.util.Date;

public class TaskBuilder {
    private final long id;
    private String summary = "";
    private String description = "";
    private boolean done = false;
    private Date dueDate;

    public TaskBuilder(long id) {
        this.id = id;
    }

    public TaskBuilder withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public TaskBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public TaskBuilder withDone(boolean done) {
        this.done = done;
        return this;
    }

    public TaskBuilder withDueDate(Date dueDate) {
        this.dueDate = new Date(dueDate.getTime());
        return this;
    }

    public Task build() {
        return new Task(id,summary, description,done, dueDate);
    }
}
