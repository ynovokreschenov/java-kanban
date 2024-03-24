package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;

    public Epic(String title, String description, TaskState taskState) {
        this.setTitle(title);
        this.setDescription(description);
        this.setState(taskState);
        //this.setStartTime(LocalDateTime.now());
        //this.setDuration(Duration.ZERO);
        subtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
        for (Subtask subtask : subtasks) {
            subtask.setEpic(this);
        }
        this.calculateEpicState();
    }

    public void calculateEpicState() {
        // Пересчитываем статус эпика
        TaskState epicState = TaskState.IN_PROGRESS;
        ArrayList<Subtask> epicSubtasks = getSubtasks();
        if (epicSubtasks.size() == 0) {
            epicState = TaskState.NEW;
        } else {
            int countNewSubtasks = 0;
            int countDoneSubtasks = 0;
            for (Subtask subtask : epicSubtasks) {
                if (subtask.getState() == TaskState.NEW) {
                    countNewSubtasks++;
                }
                if (subtask.getState() == TaskState.DONE) {
                    countDoneSubtasks++;
                }
            }
            if (countNewSubtasks == epicSubtasks.size()) {
                epicState = TaskState.NEW;
            }
            if (countDoneSubtasks == epicSubtasks.size()) {
                epicState = TaskState.DONE;
            }
        }
        super.setState(epicState);

        // Пересчитываем Временные параметры: дата начала, дата окончания, длительность
        Duration epicDuration = Duration.ZERO;
        LocalDateTime epicStartTime = null;
        LocalDateTime epicEndTime = null;
        if (subtasks.size() > 0){
            epicStartTime = subtasks.get(0).getStartTime();
            //epicEndTime = subtasks.get(0).getEndTime();
        }
        for (Subtask subtask : epicSubtasks) {
            if (subtask.getStartTime() != null && subtask.getEndTime() != null) {
                epicDuration.plusMinutes(subtask.getDuration());
                if (subtask.getStartTime().isBefore(epicStartTime)) {
                    epicStartTime = subtask.getStartTime();
                }
            }
        }
        if (epicStartTime != null) {
            this.setStartTime(epicStartTime);
            this.setDuration(epicDuration);
            this.setEndTime(epicStartTime.plus(epicDuration));
        }
    }

    @Override
    public String toString() {
        return "model.Epic{" +
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
        return TaskType.EPIC;
    }
}
