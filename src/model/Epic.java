package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;

    public Epic(String title, String description, TaskState taskState) {
        this.setTitle(title);
        this.setDescription(description);
        this.setState(taskState);
        subtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
        this.calculateEpicState();
    }

    public void calculateEpicState() {
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
    }
}
