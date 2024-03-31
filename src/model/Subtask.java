package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private Epic epic;

    public Subtask(String title, String description, TaskState taskState, LocalDateTime startTime, Duration duration) {
        this.setTitle(title);
        this.setDescription(description);
        this.setState(taskState);
        this.setStartTime(startTime);
        this.setDuration(duration);
        //this.endTime = startTime.plus(duration);
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public void setState(TaskState state) {
        super.setState(state);
        if (epic != null) {
            epic.calculateEpicState();
        }
    }

    @Override
    public String toString() {
        return "model.Subtask{" +
                "epicId=" + epic.getId() + ", " +
                "id=" + super.getId() +
                ", state=" + super.getState() +
                ", title='" + super.getTitle() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                ", duration='" + super.getDuration() + '\'' +
                ", startTime='" + super.getStartTimeFormatted() + '\'' +
                '}';
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public Integer getEpicId() {
        return epic.getId();
    }
}
