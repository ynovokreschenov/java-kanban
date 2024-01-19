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

    public ArrayList<Subtask> getSubtasks(){
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks){
        this.subtasks = subtasks;
    }

    public TaskState calculateEpicState(){
        TaskState state;
        return TaskState.NEW;
    }
}
