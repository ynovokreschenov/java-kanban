package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {
    private static Epic epic;

    @BeforeEach
    public void beforeEach() {
        epic = new Epic("Первый эпик", "Описание первого эпика", TaskState.NEW);
    }

    @Test
    public void setAndGetSubtaskTest() {
        Subtask subtask = new Subtask(
                "Подзадача",
                "Описание подзадачи",
                TaskState.NEW,
                LocalDateTime.of(2024, 3, 25, 10, 0),
                Duration.ofDays(7));
        ArrayList<Subtask> subtasks = new ArrayList<>();
        subtasks.add(subtask);
        epic.setSubtasks(subtasks);

        assertEquals(subtasks, epic.getSubtasks(), "Не совпадает объект списка подзадач эпика");
        assertEquals(subtask, epic.getSubtasks().get(0), "Не совпадает объект подзадачи списка подзадач эпика");
    }

    @Test
    public void calculateEpicStateTest() {
        assertEquals(TaskState.NEW, epic.getState(), "Не совпадает начальный статус эпика");
        Subtask subtask1 = new Subtask(
                "Подзадача1",
                "Описание подзадачи1",
                TaskState.NEW,
                LocalDateTime.of(2024, 3, 25, 10, 0),
                Duration.ofDays(1)
        );
        Subtask subtask2 = new Subtask(
                "Подзадача2",
                "Описание подзадачи2",
                TaskState.NEW,
                LocalDateTime.of(2024, 3, 27, 10, 0),
                Duration.ofDays(1)
        );
        ArrayList<Subtask> subtasks = new ArrayList<>();
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        epic.setSubtasks(subtasks);
        assertEquals(TaskState.NEW, epic.getState(), "Не совпадает статус эпика с подзадачами NEW");
        subtask1.setState(TaskState.IN_PROGRESS);
        assertEquals(TaskState.IN_PROGRESS, epic.getState(), "Не совпадает статус эпика подзадача IN_PROGRESS");
        subtask1.setState(TaskState.DONE);
        subtask2.setState(TaskState.DONE);
        assertEquals(TaskState.DONE, epic.getState(), "Не совпадает статус эпика все подзадачи DONE");
    }
}