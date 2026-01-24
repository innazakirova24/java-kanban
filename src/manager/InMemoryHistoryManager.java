package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history = new ArrayList<>();

  
    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        Task copy;
        if (task instanceof Subtask) {
            Subtask s = (Subtask) task;
            Subtask subCopy = new Subtask(s.getTitle(), s.getDescription(), s.getStatus(), s.getEpicId());
            subCopy.setId(s.getId());
            copy = subCopy;

        } else if (task instanceof Epic) {
            Epic e = (Epic) task;
            Epic epicCopy = new Epic(e.getTitle(), e.getDescription());
            epicCopy.setId(e.getId());
            epicCopy.setStatus(e.getStatus());
            copy = epicCopy;

        } else {
            Task taskCopy = new Task(task.getTitle(), task.getDescription(), task.getStatus());
            taskCopy.setId(task.getId());
            copy = taskCopy;
        }

        history.add(copy);
        if (history.size() > 10) {
            history.remove(0);
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }

}
