package task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description, Status.NEW);
    }

    public void addSubtaskId(int id) {
        if (id == getId()) return;
        subtaskIds.add(id);
    }

    public void removeSubtaskId(int id) {
        subtaskIds.remove(Integer.valueOf(id));
    }

    public void clearSubtaskIds() {
        subtaskIds.clear();
    }

    public List<Integer> getSubtaskIds() {
        return new ArrayList<>(subtaskIds);
    }

    @Override
    public String toString() {
        return "EpicID " + getId() + " [" + getStatus() + "]\n" +
                "  Название: " + getTitle() + "\n" +
                "  Описание: " + getDescription() + "\n";
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

}
