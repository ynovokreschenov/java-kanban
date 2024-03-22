package model;

import java.util.Objects;

public class Task {
    private int id;
    private TaskState state;
    private String title;
    private String description;

    public Task() {
    }

    public Task(String title, String description, TaskState taskState) {
        this.title = title;
        this.description = description;
        this.state = taskState;
    }

    public Task(Task task) {
        this.id = task.id;
        this.title = task.title;
        this.state = task.state;
        this.description = task.description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "model.Task{" +
                "id=" + id +
                ", state=" + state +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state);
    }

    public Integer getEpicId() {
        return null;
    }

    public TaskType getType() {
        return TaskType.TASK;
    }
}
