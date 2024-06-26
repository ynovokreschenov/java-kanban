package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskTest {
    static Task task;

    @BeforeEach
    public void beforeEach() {
        task = new Task(
                "Первая задача",
                "Описание первой задачи",
                TaskState.NEW,
                LocalDateTime.of(2024, 3, 25, 10, 0),
                Duration.ofDays(7));
    }

    @Test
    public void addNewTaskTest() {
        assertNotNull(task, "Задача не найдена.");
        assertEquals("Первая задача", task.getTitle(), "Нименование не совпадает");
        assertEquals("Описание первой задачи", task.getDescription(), "Описание не совпадает");
        assertEquals(TaskState.NEW, task.getState(), "Статус не совпадают");
    }

    @Test
    public void getAndSetTaskIdTest() {
        int taskId = 1;
        task.setId(taskId);
        assertEquals(1, task.getId(), "Не совпадает ID задачи");
    }

    @Test
    public void getAndSetTaskTitleTest() {
        assertEquals("Первая задача", task.getTitle(), "Не совпадает наименование задачи");
        task.setTitle("Новое наименование");
        assertEquals("Новое наименование", task.getTitle(), "Не совпадает новое наименование задачи");
    }

    @Test
    public void getAndSetTaskDescriptionTest() {
        assertEquals("Описание первой задачи", task.getDescription(), "Не совпадает описание задачи");
        task.setDescription("Новое описание");
        assertEquals("Новое описание", task.getDescription(), "Не совпадает новое описание задачи");
    }

    @Test
    public void getAndSetTaskStateTest() {
        assertEquals(TaskState.NEW, task.getState(), "Не совпадает начальный статус задачи");
        task.setState(TaskState.IN_PROGRESS);
        assertEquals(TaskState.IN_PROGRESS, task.getState(), "Не совпадает новый статус задачи");
    }
}