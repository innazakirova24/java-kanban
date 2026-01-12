package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int nextId = 1;

    private int generateId() {
        return nextId++;
    }

    public Task createTask(Task task) {
        int id = generateId();
        task.setId(id);
        tasks.put(id, task);
        return task;
    }

    public Epic createEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        epics.put(id, epic);
        return epic;
    }

    public Subtask createSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        if (epic == null) {
            return null;
        }

        int subtaskId = generateId();
        subtask.setId(subtaskId);
        subtasks.put(subtaskId, subtask);
        epic.addSubtaskId(subtaskId);
        recalculateEpicStatus(epic.getId());
        return subtask;
    }

    private void recalculateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }

        ArrayList<Integer> subtaskIds = epic.getSubtaskIds();

        if (subtaskIds.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        int found = 0;

        for (Integer subId : subtaskIds) {
            Subtask subtask = subtasks.get(subId);
            if (subtask == null) {
                continue;
            }
            found++;

            Status st = subtask.getStatus();
            if (st != Status.NEW) {
                allNew = false;
            }
            if (st != Status.DONE) {
                allDone = false;
            }
        }

        if (found == 0) {
            epic.setStatus(Status.NEW);
            return;
        }

        if (allDone) {
            epic.setStatus(Status.DONE);
        } else if (allNew) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }


    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return new ArrayList<>();
        }

        ArrayList<Subtask> result = new ArrayList<>();
        for (Integer subId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subId);
            if (subtask != null) {
                result.add(subtask);
            }
        }
        return result;
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtaskIds();
            recalculateEpicStatus(epic.getId());
        }
    }


    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            return;
        }

        for (Integer subId : epic.getSubtaskIds()) {
            subtasks.remove(subId);
        }

        epics.remove(id);
    }

    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask == null) {
            return;
        }

        int epicId = subtask.getEpicId();

        subtasks.remove(id);

        Epic epic = epics.get(epicId);
        if (epic != null) {
            epic.removeSubtaskId(id);
            recalculateEpicStatus(epicId);
        }
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public void updateSubtask(Subtask subtask) {
        if (!subtasks.containsKey(subtask.getId())) {
            return;
        }

        subtasks.put(subtask.getId(), subtask);
        recalculateEpicStatus(subtask.getEpicId());
    }

    public void updateEpic(Epic epic) {
        Epic oldEpic = epics.get(epic.getId());
        if (oldEpic == null) {
            return;
        }

        oldEpic.setTitle(epic.getTitle());
        oldEpic.setDescription(epic.getDescription());

    }
}