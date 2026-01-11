public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        Task task1 = manager.createTask(new Task("Eat", "Поесть", Status.NEW));
        Task task2 = manager.createTask(new Task("Drink", "Попить", Status.NEW));


        Epic epic1 = manager.createEpic(new Epic("Moving", "Переезд"));
        Subtask sub1 = manager.createSubtask(new Subtask("Pack boxes", "Собрать коробки", Status.NEW, epic1.getId()));
        Subtask sub2 = manager.createSubtask(new Subtask("Pack cat", "Упаковать кошку", Status.NEW, epic1.getId()));


        Epic epic2 = manager.createEpic(new Epic("Relax", "Отдых"));
        Subtask sub3 = manager.createSubtask(new Subtask("Do nothing", "Ничего не делать весь день", Status.NEW, epic2.getId()));

        System.out.println("СОЗДАЛИ ЗАДАЧИ, ЭПИКИ, ПОДЗАДАЧИ");
        System.out.println();
        printAll(manager);



        task1.setStatus(Status.DONE);
        manager.updateTask(task1);

        sub1.setStatus(Status.DONE);
        manager.updateSubtask(sub1);

        sub2.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(sub2);


        System.out.println();
        System.out.println("ИЗМЕНИЛИ СТАТУСЫ ПОДЗАДАЧ");
        System.out.println();
        printAll(manager);




        sub2.setStatus(Status.DONE);
        manager.updateSubtask(sub2);

        System.out.println();
        System.out.println("СНОВА ИЗМЕНИЛИ СТАТУСЫ ПОДЗАДАЧ");
        System.out.println();
        printAll(manager);



        manager.deleteTaskById(task2.getId());
        manager.deleteEpicById(epic2.getId());

        System.out.println();
        System.out.println("УДАЛИЛИ task2 И epic2");
        System.out.println();
        printAll(manager);
    }

    private static void printAll(TaskManager manager) {
        System.out.println("Tasks: " + manager.getAllTasks());
        System.out.println("Epics: " + manager.getAllEpics());
        System.out.println("Subtasks: " + manager.getAllSubtasks());

        for (Epic epic : manager.getAllEpics()) {
            System.out.println("Subtasks of epic " + epic.getId() + ": " + manager.getSubtasksOfEpic(epic.getId()));
        }
    }
}

