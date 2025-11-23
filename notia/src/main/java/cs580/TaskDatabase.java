package cs580;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskDatabase implements Iterable<Task> {
    private ArrayList<Task> taskList = new ArrayList<>();
    private int taskID = 0;

    public void addTask(Task task) {
        task.setTaskID(taskID);
        taskList.add(task);
        taskID++;
    }
    public boolean removeTask(int taskID) {
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getTaskID() == taskID) {
                taskList.remove(i);
                return true;
            }
        }
        return false;
    }

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

    public boolean updateTaskByID(int taskID, Task newTask) {
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getTaskID() == taskID) {
                newTask.setTaskID(taskID);
                taskList.set(i, newTask);
                return true;
            }
        }
        return false;
    }

    public TaskDatabaseMemento createMemento() {
        return new TaskDatabaseMemento(new ArrayList<>(taskList), taskID);
    }

    public void restoreFromMemento(TaskDatabaseMemento memento) {
        this.taskList = memento.getTaskListSnapshot();
        this.taskID = memento.getTaskIDSnapshot();
    }

    public void sortTasks(TaskSortStrategy strategy) {
        strategy.sort(taskList);
    }

    public ArrayList<Task> getAllTasks() {return taskList; }

    @Override
    public Iterator<Task> iterator() {
        return taskList.iterator();
    }
}