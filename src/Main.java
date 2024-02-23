import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskState;
import service.HistoryManager;
import service.Managers;
import service.TaskManager;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = Managers.getDefaultTaskManager();

        // создали две задачи
        Task task1 = taskManager.addTask(new Task("Первая задача", "Описание первой задачи", TaskState.NEW));
        Task task2 = taskManager.addTask(new Task("Вторая задача", "Описание второй задачи", TaskState.NEW));

        // получаем задачу по идентификатору
        Task taskFromManager = taskManager.getTask(task1.getId());
        System.out.println(String.format("Получаем задачу по идентификатору: %s", taskFromManager));

        // проверяем историю через taskManager
        List<Task> historyFromManager = taskManager.getHistory();
        System.out.println(String.format("historyFromManager: %s", historyFromManager));

        // проверяем историю через historyManager
        HistoryManager historyManager = Managers.getDefaultHistory();
        historyManager.add(task1);
        historyManager.add(task2);
        List<Task> history = historyManager.getTasks();
        System.out.println(String.format("history: %s", history));
        historyManager.remove(task1.getId());
        history = historyManager.getTasks();
        System.out.println(String.format("history after remove: %s", history));

        // обновляем параметры задачи и проверяем что они сохранились
        Task taskNew = new Task(task2.getTitle(), task2.getDescription() + "123", TaskState.IN_PROGRESS);
        taskNew.setId(task2.getId());
        taskManager.updateTask(taskNew);
        System.out.println(String.format("Проверяем измененную задачу: %s", taskManager.getTask(taskNew.getId())));

        // удаляем задачу
        taskManager.deleteTask(task1.getId());
        System.out.println(String.format("Проверяем список задач после удаления: %s", taskManager.getAllTasks()));

        // создали эпик с двумя подзадачами
        Epic epic1 = taskManager.addEpic(new Epic("Первый эпик", "Описание первого эпика", TaskState.NEW));
        System.out.println(String.format("Печатаем эпик нет задач: %s", epic1));
        Subtask subtask1 = new Subtask("Первая подзадача первого эпика", "Описание подзадачи первого эпика", TaskState.NEW);
        taskManager.addSubtask(subtask1, epic1);
        System.out.println(String.format("Печатаем эпик одна задача NEW: %s", epic1));
        subtask1.setState(TaskState.IN_PROGRESS);
        System.out.println(String.format("Печатаем эпик одна задача IN_PROGRESS: %s", epic1));
        Subtask subtask2 = new Subtask("Вторая подзадача первого эпика", "Описание подзадачи первого эпика", TaskState.NEW);
        taskManager.addSubtask(subtask2, epic1);
        //System.out.println(String.format("Печатаем эпик все задачи NEW: %s", epic1));
        subtask2.setState(TaskState.DONE);
        System.out.println(String.format("Печатаем эпик одна задача DONE: %s", epic1));
        subtask1.setState(TaskState.DONE);
        System.out.println(String.format("Печатаем эпик все задачи DONE: %s", epic1));
        System.out.println(String.format("Печатаем список задач эпика: %s", epic1.getSubtasks()));

        ArrayList<Task> taskList = taskManager.getAllTasks();
        //System.out.println("Печатаем список всех задач: %s" + taskManager.getAllTasks());
        ArrayList<Epic> epicList = taskManager.getAllEpics();
        ArrayList<Subtask> subtaskList = taskManager.getAllSubtasks();

        System.out.println("Список задач");
        for (Task task : taskList) {
            System.out.println(task);
        }

        System.out.println("Список эпиков");
        for (Epic epic : epicList) {
            System.out.println(epic);
            System.out.println(String.format("Список задач эпика: %s", epic.getId()));
            for (Subtask subtask : epic.getSubtasks()) {
                System.out.println(subtask);
            }
        }
    }
}
