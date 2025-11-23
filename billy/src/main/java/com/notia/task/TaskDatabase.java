package com.notia.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * In-memory "database" of Tasks.
 *
 * UML:
 *  - tasks: List<Task>
 *
 *  + addTask(t: Task): void
 *  + deleteTask(taskId: int): void
 *  + getAllTasks(): List<Task>
 *  + getTaskById(taskId: int): Task
 *  + displayAllTasks(): void
 *  + updateTask(taskId: int, newInfo: Map): void
 *
 * Design pattern: Singleton (one shared instance),
 * with Repository-style CRUD methods.
 */
public class TaskDatabase {

    // ----- Singleton -----

    private static final TaskDatabase INSTANCE = new TaskDatabase();

    public static TaskDatabase getInstance() {
        return INSTANCE;
    }

    /** Private constructor: only accessible via getInstance(). */
    private TaskDatabase() {
        this.tasks = new ArrayList<>();
    }

    // ----- attribute from UML -----
    private final List<Task> tasks;

    // ----- UML methods -----

    public void addTask(Task t) {
        if (t == null) return;
        tasks.add(t);
    }

    public void deleteTask(int taskId) {
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            Task t = it.next();
            if (t.getTaskId() == taskId) {
                it.remove();
                return;
            }
        }
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks); // defensive copy
    }

    public Task getTaskById(int taskId) {
        for (Task t : tasks) {
            if (t.getTaskId() == taskId) {
                return t;
            }
        }
        return null;
    }

    public void displayAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("[TaskDatabase] No tasks stored.");
            return;
        }
        System.out.println("[TaskDatabase] All tasks:");
        for (Task t : tasks) {
            System.out.println(" - " + t.getTaskInfo());
        }
    }

    public void updateTask(int taskId, Map<String, Object> newInfo) {
        Task t = getTaskById(taskId);
        if (t != null) {
            t.editTask(newInfo);
        }
    }
}
