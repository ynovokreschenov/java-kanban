package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefaultTaskManager();
    }

    @Test
    public void addAndGetTaskTest() {
        Task newTask = new Task(
                "Первая задача",
                "Описание первой задачи",
                TaskState.NEW,
                LocalDateTime.of(2024, 3, 25, 10, 0),
                Duration.ofDays(1)
        );
        int taskId = taskManager.addTask(newTask).getId();
        Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(newTask.getId(), savedTask.getId(), "ID не совпадают");
        assertEquals(newTask.getTitle(), savedTask.getTitle(), "Наименования не совпадают");
        assertEquals(newTask.getDescription(), savedTask.getDescription(), "Описания не совпадают");
        assertEquals(newTask.getState(), savedTask.getState(), "Статусы не совпадают");
    }

    @Test
    public void updateTaskTest() {
        Task task = new Task(
                "Первая задача",
                "Описание первой задачи",
                TaskState.NEW,
                LocalDateTime.of(2024, 3, 26, 10, 0),
                Duration.ofDays(1)
        );
        int taskId = taskManager.addTask(task).getId();
        Task updatedTask = new Task(
                "Первая задача изменена",
                "Описание первой задачи изменено",
                TaskState.IN_PROGRESS,
                LocalDateTime.of(2024, 3, 25, 10, 0),
                Duration.ofDays(1)
        );
        updatedTask.setId(taskId);
        taskManager.updateTask(updatedTask);

        assertEquals(updatedTask, taskManager.getTask(taskId), "Задачи не совпадают");
        assertEquals(updatedTask.getId(), taskManager.getTask(taskId).getId(), "ID не совпадают");
        assertEquals(updatedTask.getTitle(), taskManager.getTask(taskId).getTitle(), "Наименования не совпадают");
        assertEquals(updatedTask.getDescription(), taskManager.getTask(taskId).getDescription(), "Описания не совпадают");
        assertEquals(updatedTask.getState(), taskManager.getTask(taskId).getState(), "Статусы не совпадают");
    }

    @Test
    public void getAllDeleteAndDeleteAllTasksTest() {
        Task task1 = new Task(
                "Первая задача",
                "Описание первой задачи",
                TaskState.NEW,
                LocalDateTime.of(2024, 3, 25, 10, 0),
                Duration.ofDays(1)
        );
        Task task2 = new Task(
                "Вторая задача",
                "Описание второй задачи",
                TaskState.NEW,
                LocalDateTime.of(2024, 3, 23, 10, 0),
                Duration.ofDays(1)
        );
        Task task3 = new Task(
                "Третья задача",
                "Описание третьей задачи",
                TaskState.NEW,
                LocalDateTime.of(2024, 3, 21, 10, 0),
                Duration.ofDays(1)
        );
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(3, tasks.size(), "Неверное количество задач");
        String testResponse = "Список всех задач не содержит добавленную задачу";
        assertTrue(tasks.contains(task1), testResponse);
        assertTrue(tasks.contains(task2), testResponse);
        assertTrue(tasks.contains(task3), testResponse);
        taskManager.deleteTask(task1.getId());
        final List<Task> tasksAfterDelete = taskManager.getAllTasks();
        assertEquals(2, tasksAfterDelete.size(), "Неверное количество задач после удаления");
        assertFalse(tasksAfterDelete.contains(task1), "Список всех задач содержит удаленную задачу");
        taskManager.deleteAllTasks();
        final List<Task> tasksAfterDeleteAll = taskManager.getAllTasks();
        assertEquals(0, tasksAfterDeleteAll.size(), "Неверное количество задач после удаления всех");
    }

    @Test
    public void getEpicSubtasksTest() {
        Epic epic = new Epic("Эпик", "Описание эпика", TaskState.NEW);
        int epicId = taskManager.addEpic(epic).getId();
        Epic savedEpic = taskManager.getEpic(epicId);

        assertNotNull(savedEpic, "Эпик не найден");
        assertEquals(epicId, savedEpic.getId(), "ID не совпадают");
        assertEquals("Эпик", savedEpic.getTitle(), "Наименования не совпадают");
        assertEquals("Описание эпика", savedEpic.getDescription(), "Описания не совпадают");
        assertEquals(TaskState.NEW, savedEpic.getState(), "Статусы не совпадают");

        Subtask subtask = new Subtask(
                "Первая подзадача",
                "Описание первой подзадачи",
                TaskState.NEW,
                LocalDateTime.of(2024, 3, 25, 10, 0),
                Duration.ofDays(1)
        );
        taskManager.addSubtask(subtask, epic);
        assertTrue(taskManager.getEpicSubtasks(epicId).contains(subtask), "Список подзадач эпика не содержит подзадачи");
    }

    //getHistory()
    @Test
    public void getHistoryTest() {
        Epic epic = new Epic("Эпик", "Описание эпика", TaskState.NEW);
        int epicId = taskManager.addEpic(epic).getId();
        Epic savedEpic = taskManager.getEpic(epicId);
        assertTrue(taskManager.getHistory().contains(savedEpic));
    }
}