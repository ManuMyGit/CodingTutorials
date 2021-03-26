package org.mjjaen.designpatterns.creational.builder.businessObject;

import java.util.Date;

public class TaskInternalBuilder {
    private final long id;
    private String summary = "";
    private String description = "";
    private boolean done = false;
    private Date dueDate;

    private TaskInternalBuilder(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Date getDueDate() {
        return new Date(dueDate.getTime());
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = new Date(dueDate.getTime());
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", done=" + done +
                ", dueDate=" + dueDate +
                '}';
    }

    public static class Builder {
        private final long id;
        private String summary = "";
        private String description = "";
        private boolean done = false;
        private Date dueDate;

        public Builder(long id) {
            this.id = id;
        }

        public Builder withSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder witDone(boolean done) {
            this.done = done;
            return this;
        }

        public Builder withDueDate(Date dueDate) {
            this.dueDate = new Date(dueDate.getTime());
            return this;
        }
        public TaskInternalBuilder build() {
            TaskInternalBuilder taskInternalBuilder = new TaskInternalBuilder(this.id);
            taskInternalBuilder.setDescription(this.description);
            taskInternalBuilder.setDone(this.done);
            taskInternalBuilder.setDueDate(this.dueDate);
            taskInternalBuilder.setSummary(this.summary);
            return taskInternalBuilder;
        }
    }
}
