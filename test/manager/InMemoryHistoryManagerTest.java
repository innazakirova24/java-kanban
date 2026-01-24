package manager;

import org.junit.jupiter.api.Test;
import task.Status;
import task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryHistoryManagerTest {

    @Test
    void getDefaultHistoryReturnsInitializedHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "getDefaultHistory() вернул null");

        Task task = new Task("History", "History", Status.NEW);
        historyManager.add(task);

        assertNotNull(historyManager.getHistory(), "getHistory() не должен возвращать null");
        assertEquals(1, historyManager.getHistory().size(),
                "После добавления одной задачи история должна содержать 1 элемент");
        assertEquals(task, historyManager.getHistory().get(0),
                "Первая задача в истории должна совпадать с добавленной");
    }

    @Test
    void historyKeepsPreviousVersionOfTask() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Old title", "Old description", Status.NEW);
        task.setId(42);
        historyManager.add(task);

        task.setTitle("New title");
        task.setDescription("New descrption");
        task.setStatus(Status.DONE);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "В истории должна быть 1 задача");

        Task fromHistory = history.get(0);
        assertEquals(42, fromHistory.getId(), "Id в истории должен сохраниться");
        assertEquals("Old title", fromHistory.getTitle(), "Title в истории должен остаться старым");
        assertEquals("Old description", fromHistory.getDescription(), "Description в истории должен остаться старым");
        assertEquals(Status.NEW, fromHistory.getStatus(), "Status в истории должен остаться старым");

    }
}
