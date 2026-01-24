package task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {


    @Test
    void taskEqualsById() {

        Task task1 = new Task("Task_1", "Task_1 description", Status.NEW);
        Task task2 = new Task("Task_2", "Task_2 description", Status.NEW);
        task1.setId(1);
        task2.setId(1);
        assertEquals(task1, task2, "Задачи не совпадают.");
    }

    @Test
    void epicEqualsById() {

        Epic epic1 = new Epic("Epic_1", "Epic_1 description");
        Epic epic2 = new Epic("Epic_2", "Epic_2 description");
        epic1.setId(1);
        epic2.setId(1);
        assertEquals(epic1, epic2, "Эпики не совпадают.");
    }

    @Test
    void subtaskEqualsById() {

        Subtask subtask1 = new Subtask("Subtask_1", "Subtask_1 description", Status.NEW, 1);
        Subtask subtask2 = new Subtask("Subtask_2", "Subtask_2 description", Status.NEW, 1);
        subtask1.setId(1);
        subtask2.setId(1);
        assertEquals(subtask1, subtask2, "Сабтаски не совпадают.");
    }

}
