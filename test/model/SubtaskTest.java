package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    private static Subtask subtask;
    private static Epic epic;

    @BeforeEach
    public void beforeEach(){
        subtask = new Subtask("Первая подзадача", "Описание первой подзадачи", TaskState.NEW);
        epic = new Epic("Первый эпик", "Описание первого эпика", TaskState.NEW);
    }
    @Test
    public void setAndGetEpicTest(){
        assertNull(subtask.getEpic(),"У новой подзадачи должен быть пустой эпик");
        subtask.setEpic(epic);
        assertEquals(epic, subtask.getEpic(),"Не совпадает объект эпика подзадачи");
    }
}