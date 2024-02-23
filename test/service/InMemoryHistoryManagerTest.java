package service;

import model.Task;
import model.TaskState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private static HistoryManager historyManager;

    @BeforeEach
    public void beforeEach(){
        historyManager = Managers.getDefaultHistory();
    }
    @Test
    void add() {
        Task task = new Task("Первая задача", "Описание первой задачи", TaskState.NEW);
        task.setId(1);
        historyManager.add(task);
        final List<Task> history = historyManager.getTasks();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "Размер истории не равен 1");
    }

    @Test
    void remove() {
        Task task1 = new Task("Первая задача", "Описание первой задачи", TaskState.NEW);
        task1.setId(1);
        historyManager.add(task1);
        Task task2 = new Task("Вторая задача", "Описание второй задачи", TaskState.NEW);
        task2.setId(2);
        historyManager.add(task2);
        List<Task> history = historyManager.getTasks();
        assertNotNull(history, "История не пустая.");
        assertEquals(2, history.size(), "Размер истории не равен 2");
        historyManager.remove(task1.getId());
        history = historyManager.getTasks();
        assertEquals(1, history.size(), "Размер истории не равен 1");
        historyManager.remove(task2.getId());
        history = historyManager.getTasks();
        assertEquals(0, history.size(), "Размер истории не равен 0");
    }

    @Test
    void getTasks() {
        List<Task> history = historyManager.getTasks();
        assertNotNull(history, "История не пустая.");
        assertEquals(0, history.size(), "Размер истории не равен 0");
        Task task1 = new Task("Первая задача", "Описание первой задачи", TaskState.NEW);
        task1.setId(1);
        historyManager.add(task1);
        Task task2 = new Task("Первая задача", "Описание первой задачи", TaskState.NEW);
        task1.setId(2);
        historyManager.add(task2);
        history = historyManager.getTasks();
        assertEquals(2, history.size(), "Размер истории не равен 2");
    }
}