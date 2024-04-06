import model.Task;
import model.TaskState;
import server.HttpTaskServer;
import service.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("FileBackedTaskManager:");
        File file = new File("./resources/task.csv");
        TaskManager taskManagerFileBacked = FileBackedTaskManager.loadFromFile(file);

        Task fbt = new Task(
                "Первая задача123",
                "Описание первой задачи123",
                TaskState.NEW,
                LocalDateTime.of(2024, 3, 24, 10, 0),
                Duration.ofDays(3)
        );
        taskManagerFileBacked.addTask(fbt);
        taskManagerFileBacked.getTask(fbt.getId()); // добавляем в историю

        Task task1 = taskManagerFileBacked.addTask(new Task(
                "Первая задача",
                "Описание первой задачи",
                TaskState.NEW,
                LocalDateTime.of(2024, 3, 25, 10, 0),
                Duration.ofDays(7)
                ));
        Task task2 = taskManagerFileBacked.addTask(new Task(
                "Вторая задача",
                "Описание второй задачи",
                TaskState.NEW,
                LocalDateTime.of(2024, 3, 20, 10, 0),
                Duration.ofDays(17)
        ));

        HttpTaskServer server = new HttpTaskServer(taskManagerFileBacked);
        server.start();

//        System.out.println("Список задач " + taskManagerFileBacked.getAllTasks());
//        System.out.println("Приоритизированный список задач: " + taskManagerFileBacked.getPrioritizedTasks());
//        System.out.println("Список эпиков " + taskManagerFileBacked.getAllEpics());
//        System.out.println("Список подзадач " + taskManagerFileBacked.getAllSubtasks());
    }
}
