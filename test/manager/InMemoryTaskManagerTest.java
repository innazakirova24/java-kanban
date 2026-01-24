package manager;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    @Test
    void getDefaultReturnsInitializedTaskManager() {
        TaskManager manager = Managers.getDefault();
        assertNotNull(manager, "Managers.getDefault() вернул null");

        Task created = manager.createTask(new Task("Task", "Description", Status.NEW));
        assertNotNull(created, "createTask вернул null");
        assertTrue(created.getId() > 0, "После создания у задачи должен быть установлен id > 0");

        Task found = manager.getTaskById(created.getId());
        assertNotNull(found, "getTaskById не нашёл созданную задачу");
        assertEquals(created, found, "Найденная задача должна совпадать с созданной");
    }

    @Test
    void addsDifferentTaskTypesAndFindsById() {
        TaskManager manager = new InMemoryTaskManager();

        Task createdTask = manager.createTask(new Task("Task", "Task description", Status.NEW));
        assertNotNull(createdTask, "createTask вернул null");
        int taskId = createdTask.getId();

        Epic createdEpic = manager.createEpic(new Epic("Epic", "Epic description"));
        assertNotNull(createdEpic, "createEpic вернул null");
        int epicId = createdEpic.getId();

        Subtask createdSubtask = manager.createSubtask(new Subtask("Subtask", "Subtask description", Status.NEW, epicId));
        assertNotNull(createdSubtask, "createSubtask вернул null (эпик должен существовать)");
        int subtaskId = createdSubtask.getId();

        Task foundTask = manager.getTaskById(taskId);
        Epic foundEpic = manager.getEpicById(epicId);
        Subtask foundSubtask = manager.getSubtaskById(subtaskId);

        assertNotNull(foundTask, "getTaskById не нашёл задачу");
        assertNotNull(foundEpic, "getEpicById не нашёл эпик");
        assertNotNull(foundSubtask, "getSubtaskById не нашёл сабтаск");
        assertEquals(createdTask, foundTask, "Найденная Task должна совпадать с созданной");
        assertEquals(createdEpic, foundEpic, "Найденный Epic должен совпадать с созданным");
        assertEquals(createdSubtask, foundSubtask, "Найденный Subtask должен совпадать");
        assertEquals(epicId, foundSubtask.getEpicId(), "Subtask должен ссылаться на правильный epicId");
    }

    @Test
    void setIdDoesNotConflictWithGeneratedId() {
        TaskManager manager = new InMemoryTaskManager();
        Task task1 = manager.createTask(new Task("Task_1", "Task_1 description", Status.NEW));
        int id1 = task1.getId();
        assertTrue(id1 > 0, "Менеджер должен присвоить id > 0");

        Task task2 = new Task("Task_2", "Task_2 description", Status.NEW);
        task2.setId(id1);

        Task created2 = manager.createTask(task2);
        int id2 = created2.getId();
        assertNotEquals(id1, id2, "Сгенерированный id второй задачи не должен конфликтовать с уже занятым id");
        assertEquals(task1, manager.getTaskById(id1), "Задача с первым id должна остаться прежней");
        assertEquals(created2, manager.getTaskById(id2), "Вторая задача должна находиться по новому id");

        List<Task> all = manager.getAllTasks();
        assertEquals(2, all.size(), "В менеджере должно быть 2 задачи");
    }

    @Test
    void taskFieldsAreNotChangedWhenAddedToManager() {
        TaskManager manager = new InMemoryTaskManager();
        Task task = new Task("Task", "Task description", Status.IN_PROGRESS);

        String originalTitle = task.getTitle();
        String originalDescription = task.getDescription();
        Status originalStatus = task.getStatus();
        int originalId = task.getId();
        Task created = manager.createTask(task);

        assertNotEquals(originalId, created.getId(), "После добавления менеджер должен назначить id");
        assertEquals(originalTitle, created.getTitle(), "Title не должен измениться при добавлении");
        assertEquals(originalDescription, created.getDescription(), "Description не должен измениться при добавлении");
        assertEquals(originalStatus, created.getStatus(), "Status не должен измениться при добавлении");

    }
}