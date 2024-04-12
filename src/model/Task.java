package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    private Integer id;
    private TaskState state;
    private String title;
    private String description;
    private LocalDateTime startTime = LocalDateTime.now();
    private LocalDateTime endTime;
    private Duration duration = Duration.ZERO;

    public Task() {
    }

    public Task(String title, String description, TaskState taskState, LocalDateTime startTime, Duration duration) {
        this.title = title;
        this.description = description;
        this.state = taskState;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime.plus(duration);
    }

    public Task(Task task) {
        this.id = task.id;
        this.title = task.title;
        this.state = task.state;
        this.description = task.description;
        this.startTime = task.startTime;
        this.duration = task.duration;
        this.endTime = startTime.plus(duration);
    }

    public Integer getId() {
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

    public Integer getEpicId() {
        return null;
    }

    public TaskType getType() {
        return TaskType.TASK;
    }

    public int getDuration() {
        return (int) duration.toMinutes();
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
        //this.endTime = startTime.plusMinutes(duration.toMinutes());
        this.endTime = startTime.plus(duration);
    }

    public String getStartTimeFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return startTime.format(formatter);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        //this.endTime = startTime.plus(duration);
    }

    protected void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    @Override
    public String toString() {
        return "model.Task{" +
                "id=" + id +
                ", state=" + state +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                ", startTime='" + startTime + '\'' +
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

}
