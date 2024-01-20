package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static int taskIdCounter;
    private static HashMap<Integer, Task> tasks;
    private static HashMap<Integer, Epic> epics;
    private static HashMap<Integer, Subtask> subtasks;

    public TaskManager(){
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }

    private int getNextTaskId(){
        return ++taskIdCounter;
    }

    // Методы Task
    public Task addTask(Task task){
        int nextId = getNextTaskId();
        task.setId(nextId);
        tasks.put(task.getId(), task);
        return task;
    }

    public Task getTask(int id){
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }
        else {
            return null;
        }
    }

    public void updateTask(Task task){
        tasks.put(task.getId(), task);
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        for (Task task : tasks.values()){
            taskList.add(task);
        }
        return taskList;
    }

    public void deleteTask(int id){
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        }
    }

    public void deleteAllTasks(){
        for (Task task : tasks.values()) {
            tasks.remove(task.getId());
        }
    }

    // Методы Epic
    public Epic addEpic(Epic epic){
        int nextId = getNextTaskId();
        epic.setId(nextId);
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Epic getEpic(int id){
        return epics.get(id);
    }

    public ArrayList<Subtask> getEpicSubtasks(int id){
        return getEpic(id).getSubtasks();
    }

    public void updateEpic(Epic epic){
        tasks.put(epic.getId(), epic);
    }

    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> epicList = new ArrayList<>();
        for (Epic epic : epics.values()){
            epicList.add(epic);
        }
        return epicList;
    }

    public void deleteEpic(int id){
        if (epics.containsKey(id)){
            for (Subtask subtask : epics.get(id).getSubtasks()){
                subtasks.remove(subtask.getId());
            }
            epics.remove(id);
        }
    }

    public void deleteAllEpics() {
        for (Epic epic : epics.values()) {
            epics.remove(epic.getId());
        }
    }

    // Методы Subtask
    public Subtask addSubtask(Subtask subtask, Epic epic){
        int nextId = getNextTaskId();
        subtask.setId(nextId);
        subtasks.put(subtask.getId(), subtask);
        subtask.setEpic(epic);
        ArrayList<Subtask> epicSubtasks = epic.getSubtasks();
        epicSubtasks.add(subtask);
        epic.setSubtasks(epicSubtasks);
        epic.calculateEpicState();
        return subtask;
    }

    public Subtask getSubtask(int id){
        return subtasks.get(id);
    }

    public void updateSubtask(Subtask subtask, Epic epic){
        subtasks.put(subtask.getId(), subtask);
        subtask.setEpic(epic);
        epic.calculateEpicState();
    }

    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> subtaskList = new ArrayList<>();
        for (Subtask subtask : subtasks.values()){
            subtaskList.add(subtask);
        }
        return subtaskList;
    }

    public void deleteSubtask(int id){
        if (subtasks.containsKey(id)) {
            Epic epic = subtasks.get(id).getEpic();
            epic.getSubtasks().remove(id);
            subtasks.remove(id);
            epic.calculateEpicState();
        }
    }

    public void deleteAllSubtasks(){
        if (subtasks.size() > 0) {
            Epic epic = null;
            for (Subtask subtask : subtasks.values()) {
                if (epic == null){
                    epic = subtask.getEpic();
                }
                subtasks.remove(subtask.getId());
                epic.calculateEpicState();
            }
            epic.calculateEpicState();
        }
    }
}
