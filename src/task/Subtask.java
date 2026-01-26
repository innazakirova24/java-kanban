package task;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String title, String description, Status status, int epicId) {
        super(title, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubtaskID " + getId() + " [" + getStatus() + "]\n" +
                "  Название: " + getTitle() + "\n" +
                "  Описание: " + getDescription() + "\n";
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }


}
