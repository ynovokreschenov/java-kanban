import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskState;
import service.InMemoryHistoryManager;
import service.InMemoryTaskManager;
import service.Managers;
import service.TaskManager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали!");
        //TaskManager taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());
        TaskManager taskManager = Managers.getDefaultTaskManager();

        // создали две задачи
        Task task1 = taskManager.addTask(new Task("Первая задача", "Описание первой задачи", TaskState.NEW));
        Task task2 = taskManager.addTask(new Task("Вторая задача", "Описание второй задачи", TaskState.NEW));

        // получаем задачу по идентификатору
        Task taskFromManager = taskManager.getTask(task1.getId());
        System.out.println("Получаем задачу по идентификатору: " + taskFromManager);

        // обновляем параметры задачи и проверяем что они сохранились
        Task taskNew = new Task(task2.getTitle(), task2.getDescription()+"123", TaskState.IN_PROGRESS);
        taskNew.setId(task2.getId());
        taskManager.updateTask(taskNew);
        System.out.println("Проверяем измененную задачу" + taskManager.getTask(taskNew.getId()));

        // удаляем задачу
        taskManager.deleteTask(task1.getId());
        System.out.println("Проверяем список задач после удаления" + taskManager.getAllTasks());

        // создали эпик с двумя подзадачами
        Epic epic1 = taskManager.addEpic(new Epic("Первый эпик", "Описание первого эпика", TaskState.NEW));
        taskManager.addSubtask(
                new Subtask("Первая подзадача первого эпика", "Описание подзадачи первого эпика", TaskState.NEW),
                        epic1
        );
        taskManager.addSubtask(
                new Subtask("Вторая подзадача первого эпика", "Описание подзадачи первого эпика", TaskState.IN_PROGRESS),
                epic1
        );

        System.out.println("Печатаем эпик: " + epic1);
        System.out.println("Печатаем список задач эпика: " + epic1.getSubtasks());

        ArrayList<Task> taskList = taskManager.getAllTasks();
        //System.out.println("Печатаем список всех задач: " + taskManager.getAllTasks());
        ArrayList<Epic> epicList = taskManager.getAllEpics();
        ArrayList<Subtask> subtaskList = taskManager.getAllSubtasks();

        System.out.println("Список задач");
        for (Task task : taskList){
            System.out.println(task);
        }

        System.out.println("Список эпиков");
        for (Epic epic : epicList){
            System.out.println(epic);
            System.out.println("Список задач эпика " + epic.getId());
            for (Subtask subtask : epic.getSubtasks()){
                System.out.println(subtask);
            }
        }
    }
}
