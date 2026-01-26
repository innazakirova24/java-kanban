package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history = new LinkedList<>();

    private static final int MAX_HISTORY_SIZE = 10;

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        Task copy;

        switch (task.getType()) {
            case SUBTASK:
                Subtask s = (Subtask) task;
                Subtask subCopy = new Subtask(s.getTitle(), s.getDescription(), s.getStatus(), s.getEpicId());
                subCopy.setId(s.getId());
                copy = subCopy;
                break;

            case EPIC:
                Epic e = (Epic) task;
                Epic epicCopy = new Epic(e.getTitle(), e.getDescription());
                epicCopy.setId(e.getId());
                epicCopy.setStatus(e.getStatus());
                copy = epicCopy;
                break;

            case TASK:
            default:
                Task taskCopy = new Task(task.getTitle(), task.getDescription(), task.getStatus());
                taskCopy.setId(task.getId());
                copy = taskCopy;
                break;
        }

        history.add(copy);

        if (history.size() > MAX_HISTORY_SIZE) {
            history.removeFirst();
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }

}
