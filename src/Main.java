import model.Task;
import model.TaskState;
import service.*;

import java.io.File;
import java.io.IOException;



public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("FileBackedTaskManager:");
        File file = new File("./resources/task.csv");
        TaskManager taskManagerFileBacked = FileBackedTaskManager.loadFromFile(file);

        Task fbt = new Task("Первая задача123", "Описание первой задачи123", TaskState.NEW);
        taskManagerFileBacked.addTask(fbt);
        taskManagerFileBacked.getTask(fbt.getId()); // добавляем в историю

        System.out.println("Список задач " + taskManagerFileBacked.getAllTasks());
        System.out.println("Список эпиков " + taskManagerFileBacked.getAllEpics());
        System.out.println("Список подзадач " + taskManagerFileBacked.getAllSubtasks());

        System.out.println("InMemoryTaskManager:");
        TaskManager taskManager = Managers.getDefaultTaskManager();

        // создали две задачи
        Task task1 = taskManager.addTask(new Task("Первая задача", "Описание первой задачи", TaskState.NEW));
        Task task2 = taskManager.addTask(new Task("Вторая задача", "Описание второй задачи", TaskState.NEW));

        // получаем задачу по идентификатору
        Task taskFromManager = taskManager.getTask(task1.getId());
        System.out.println(String.format("Получаем задачу по идентификатору: %s", taskFromManager));
    }
}
