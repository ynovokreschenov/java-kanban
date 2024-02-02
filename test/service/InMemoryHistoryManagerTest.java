package service;

import model.Task;
import model.TaskState;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private static HistoryManager historyManager = Managers.getDefaultHistory();
    @Test
    void add() {
        Task task = new Task("Первая задача", "Описание первой задачи", TaskState.NEW);
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }
}