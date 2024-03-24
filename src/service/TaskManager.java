package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public interface TaskManager {
    // Методы Task
    Task addTask(Task task);

    Task getTask(int id);

    void updateTask(Task task);

    ArrayList<Task> getAllTasks();

    TreeSet<Task> getPrioritizedTasks();

    void deleteTask(int id);

    void deleteAllTasks();

    // Методы Epic
    Epic addEpic(Epic epic);

    Epic getEpic(int id);

    ArrayList<Subtask> getEpicSubtasks(int id);

    void updateEpic(Epic epic);

    ArrayList<Epic> getAllEpics();

    void deleteEpic(int id);

    void deleteAllEpics();

    // Методы Subtask
    Subtask addSubtask(Subtask subtask, Epic epic);

    Subtask getSubtask(int id);

    void updateSubtask(Subtask subtask, Epic epic);

    ArrayList<Subtask> getAllSubtasks();

    void deleteSubtask(int id);

    void deleteAllSubtasks();

    List<Task> getHistory();
}
