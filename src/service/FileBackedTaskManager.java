package service;

import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;
    public static final String TASK_CSV = "./resources/task.csv";

    public FileBackedTaskManager() {
        this(Managers.getDefaultHistory());
    }

    public FileBackedTaskManager(HistoryManager historyManager) {
        this(historyManager, new File(TASK_CSV));
    }

    public FileBackedTaskManager(File file) {
        this(Managers.getDefaultHistory(), file);
    }

    public FileBackedTaskManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
    }

    public void init() {
        loadFromFile();
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        manager.init();
        return manager;
    }

    @Override
    public Task addTask(Task task) {
        super.addTask(task);
        save();
        return task;
    }

    @Override
    public Task getTask(int id){
        Task task = super.getTask(id);
        save();
        return task;
    }

    private String toString(Task task) {
        //id,type,name,status,description,epic
        String line = task.getId() + "," + task.getType() + "," + task.getTitle() + "," +
                task.getState().toString() + "," + task.getDescription() + ",";
        if (task.getType() == TaskType.SUBTASK){
            line += task.getEpicId();
        }
        return line;
    }

    private Task fromString(String value) {
        final String[] columns = value.split(",");
        if (columns.length >= 5) {
            Integer id = Integer.valueOf(columns[0]);
            String name = columns[2];
            String stateString = columns[3];
            String description = columns[4];
            TaskState state = null;
            switch (stateString) {
                case "NEW":
                    state = TaskState.NEW;
                    break;
                case "IN_PROGRESS":
                    state = TaskState.IN_PROGRESS;
                    break;
                case "DONE":
                    state = TaskState.DONE;
                    break;
            }

            TaskType type = TaskType.valueOf(columns[1]);
            Task task = null;
            switch (type) {
                case TASK:
                    task = new Task(name, description, state);
                    break;
                case SUBTASK:
                    Subtask subtask = new Subtask(name, description, state);
                    Integer epicId = Integer.valueOf(columns[5]);
                    Epic epicSubtask = epics.get(epicId);
                    subtask.setEpic(epicSubtask);
                    task = subtask;
                    break;
                case EPIC:
                    task = new Epic(name, description, state);
                    break;
            }
            task.setId(id);
            return task;
        } else {
            return null;
        }
    }

    static String toString(HistoryManager manager) {
        StringBuilder sb = new StringBuilder();
        for (Task task : manager.getTasks()) {
            sb.append(task.getId()).append(",");
        }
        return sb.toString();
    }

    static List<Integer> historyFromString(String value) {
        final String[] ids = value.split(",");
        if (ids.length > 0) {
            List<Integer> history = new ArrayList<>();
            for (String id : ids) {
                history.add(Integer.valueOf(id));
            }
            return history;
        } else {
            return null;
        }
    }

    private void save() {
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Заголовок id,type,name,status,description,epic
            writer.append("id,type,name,status,description,epic");
            writer.newLine();
            // Сохраняем задачи
            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                writer.append(toString(entry.getValue()));
                writer.newLine();
            }
            // Сохраняем эпики
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                writer.append(toString(entry.getValue()));
                writer.newLine();
            }
            // Сохраняем подзадачи
            for (Map.Entry<Integer, Subtask> entry : subtasks.entrySet()) {
                writer.append(toString(entry.getValue()));
                writer.newLine();
            }
            // история
            writer.append(toString(historyStorage));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка в файле: " + file.getAbsolutePath(), e); // TODO ManagerSaveException
        }
    }

    private void loadFromFile() {
        int maxId = 0;
        try (final BufferedReader reader = new BufferedReader(new FileReader(file, UTF_8))) {
            reader.readLine(); // Пропускаем заголовок
            while (true) {
                String line = reader.readLine();
                //System.out.println(line);
                if (line == null) {
                    break;
                }
                if (line.isEmpty()) {
                    break;
                }
                // Задачи
                final Task task = fromString(line);
                if (task != null) {
                    //System.out.println(task);
                    final int id = task.getId();
                    if (task.getType() == TaskType.TASK) {
                        tasks.put(id, task);
                    } else if (task.getType() == TaskType.EPIC) {
                        epics.put(id, (Epic) task);
                    } else if (task.getType() == TaskType.SUBTASK) {
                        subtasks.put(id, (Subtask) task);
                    }
                    // Сохраняем значение счетчика для taskIdCounter
                    if (maxId < id) {
                        maxId = id;
                    }
                } else {
                    // История
                    List<Integer> historyList = historyFromString(line);
                    for (int histId: historyList){
                        super.getTask(histId);
                        super.getSubtask(histId);
                        super.getEpic(histId);
                    }
                }

            }



        } catch (IOException e) {
            throw new RuntimeException(e); // TODO ManagerSaveException
        }
        // генератор
        taskIdCounter = maxId;

    }

}
