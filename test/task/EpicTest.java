package task;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EpicTest {

    @Test
    void cantAddEpicAsOwnSubtask() {
        Epic epic = new Epic("Epic_1", "Epic_1 description");
        epic.setId(1);
        epic.addSubtaskId(epic.getId());
        Assertions.assertFalse(epic.getSubtaskIds().contains(epic.getId()));
        Assertions.assertEquals(0, epic.getSubtaskIds().size());
    }

    @Test
    void cantMakeSubtaskOwnEpic() {
        TaskManager manager = Managers.getDefault();
        Epic epic = manager.createEpic(new Epic("Epic", "Description"));
        Subtask subtask = manager.createSubtask(new Subtask("Subtask", "Description", Status.NEW, epic.getId()));
        Assertions.assertNotNull(subtask);
        Assertions.assertNotEquals(subtask.getEpicId(), subtask.getId(), "Subtask ID совпадает с Epic ID");
    }
}