package cs580;

import java.util.ArrayList;

public class TaskDatabaseMemento {
    private final ArrayList<Task> taskListSnapshot;
    private final int taskIDSnapshot;

    public TaskDatabaseMemento(ArrayList<Task> taskList, int taskID) {
        this.taskListSnapshot = new ArrayList<>();
        for (Task task : taskList) {
            this.taskListSnapshot.add(new Task(task));
        }
        this.taskIDSnapshot = taskID;
    }

    public ArrayList<Task> getTaskListSnapshot() { return taskListSnapshot; }
    public int getTaskIDSnapshot() { return taskIDSnapshot; }
}
