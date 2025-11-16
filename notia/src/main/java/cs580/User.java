package cs580;

import cs580.event.Event;
import cs580.event_database.EventDatabase;

public class User {
    private UserData userInfo;
    private TaskDatabase taskDatabase;
    private EventDatabase eventDatabase;

    public User(UserData userInfo, TaskDatabase taskDatabase, EventDatabase eventDatabase) {
        this.userInfo = userInfo;
        this.taskDatabase = taskDatabase;
        this.eventDatabase = eventDatabase;
    }

    public void addTask() {
    }

    public void removeTask() {
    }

    public void addEvent() {
    }

    public void removeEvent() {
    }

    public void displayTaskDatabaseSummary() {
        System.out.println();
        for (Task task : taskDatabase) {
            System.out.println(task.getTaskSummary());
        }
        System.out.println();
    }

    public void displayEventDatabaseSummary() {
        System.out.println();
        for (Event event : eventDatabase) {
            System.out.println(event.getEventInfo());
        }
        System.out.println();
    }

    public boolean loginUser(String username, String password) {
        if (userInfo.validateUserLogin(username, password)) {
            return true;
        } else {
            return false;
        }
    }
}