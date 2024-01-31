package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    List<Task> taskHistory;

    public InMemoryHistoryManager() {
        this.taskHistory = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        taskHistory.add(task);
        if (taskHistory.size() == 11){
            taskHistory.remove(0);
        }
    }

    @Override
    public List<Task> getHistory() {
        return taskHistory;
    }
}
