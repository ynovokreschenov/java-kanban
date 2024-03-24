package service;

import model.Epic;
import model.Subtask;
import model.Task;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    static int taskIdCounter;
    final HashMap<Integer, Task> tasks;
    final HashMap<Integer, Epic> epics;
    final HashMap<Integer, Subtask> subtasks;
    TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    final HistoryManager historyStorage;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.tasks = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.historyStorage = historyManager;
    }

    private int getNextTaskId() {
        return ++taskIdCounter;
    }

    @Override
    public List<Task> getHistory() {
        return historyStorage.getTasks();
    }

    @Override
    public Task addTask(Task task) {
        if (validateTaskPeriodCrossing(task)) {
            int nextId = getNextTaskId();
            task.setId(nextId);
            tasks.put(nextId, task);
            if (task.getStartTime() != null) {
                prioritizedTasks.add(task);
            }
            return task;
        } else {
            return null;
        }
    }

    private boolean validateTaskPeriodCrossing(Task task) {
        long crossTasks = prioritizedTasks.stream()
                .filter((t) -> task.getStartTime().isAfter(t.getStartTime()) && task.getStartTime().isBefore(t.getEndTime()))
                .filter((t) -> task.getEndTime().isAfter(t.getStartTime()) && task.getEndTime().isBefore(t.getEndTime()))
                .count();
        return crossTasks == 0;
    }

    @Override
    public Task getTask(int id) {
        if (tasks.containsKey(id)) {
            historyStorage.add(tasks.get(id));
            return tasks.get(id);
        } else {
            return null;
        }
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        for (Task task : tasks.values()) {
            taskList.add(task);
        }
        return taskList;
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    @Override
    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        }
        Task task = tasks.get(id);
        if (task != null) {
            prioritizedTasks.remove(task);
        }
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    // Методы Epic
    @Override
    public Epic addEpic(Epic epic) {
        int nextId = getNextTaskId();
        epic.setId(nextId);
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public Epic getEpic(int id) {
        if (epics.containsKey(id)) {
            historyStorage.add(epics.get(id));
            return epics.get(id);
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(int id) {
        return getEpic(id).getSubtasks();
    }

    @Override
    public void updateEpic(Epic epic) {
        tasks.put(epic.getId(), epic);
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> epicList = new ArrayList<>();
        for (Epic epic : epics.values()) {
            epicList.add(epic);
        }
        return epicList;
    }

    @Override
    public void deleteEpic(int id) {
        if (epics.containsKey(id)) {
            for (Subtask subtask : epics.get(id).getSubtasks()) {
                subtasks.remove(subtask.getId());
            }
            epics.remove(id);
        }
    }

    @Override
    public void deleteAllEpics() {
        for (Epic epic : epics.values()) {
            epics.remove(epic.getId());
        }
    }

    // Методы Subtask
    @Override
    public Subtask addSubtask(Subtask subtask, Epic epic) {
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

    @Override
    public Subtask getSubtask(int id) {
        if (subtasks.containsKey(id)) {
            historyStorage.add(subtasks.get(id));
            return subtasks.get(id);
        } else {
            return null;
        }
    }

    @Override
    public void updateSubtask(Subtask subtask, Epic epic) {
        subtasks.put(subtask.getId(), subtask);
        subtask.setEpic(epic);
        epic.calculateEpicState();
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> subtaskList = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            subtaskList.add(subtask);
        }
        return subtaskList;
    }

    @Override
    public void deleteSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Epic epic = subtasks.get(id).getEpic();
            epic.getSubtasks().remove(id);
            subtasks.remove(id);
            epic.calculateEpicState();
        }
    }

    @Override
    public void deleteAllSubtasks() {
        if (subtasks.size() > 0) {
            Epic epic = null;
            for (Subtask subtask : subtasks.values()) {
                if (epic == null) {
                    epic = subtask.getEpic();
                }
                subtasks.remove(subtask.getId());
            }
            if (epic != null) {
                epic.calculateEpicState();
            }
        }
    }
}
