package cs580;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskDatabase implements Iterable<Task> {
    private static TaskDatabase instance = null;
    private ArrayList<Task> taskList = new ArrayList<>();

    private TaskDatabase() {}

    public static TaskDatabase getInstance() {
        if (instance == null) {
            instance = new TaskDatabase();
        }
        return instance;
    }
    
    public void addTask(Task task) { taskList.add(task); }
    public void removeTask(Task task) { taskList.remove(task); }
    public ArrayList<Task> getTaskList() { return taskList; }

    public Task getTaskByID(int taskID) {
        for (Task task : taskList) {
            if (task.getTaskID() == taskID) {
                return task;
            }
        }
        return null;
    }

    public Task getTaskByName(String name) {
        for (Task task : taskList) {
            if (task.getTaskName().equals(name)) {
                return task;
            }
        }
        return null;
    }

    @Override
    public Iterator<Task> iterator() {
        return taskList.iterator();
    }
}
