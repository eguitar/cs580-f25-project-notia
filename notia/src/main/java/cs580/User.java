package cs580;

public class User {
    private UserData userInfo;
    private TaskDatabase taskDatabase;
    private EventDatabase eventDatabase;

    public User(UserData userInfo, TaskDatabase taskDatabase, EventDatabase eventDatabase) {
        this.userInfo = userInfo;
        this.taskDatabase = taskDatabase;
        this.eventDatabase = eventDatabase;
    }

    public void addTask() {}
    public void removeTask() {}

    public void addEvent() {}
    public void removeEvent() {}

    // public String getTaskDatabaseSummary() {}
    // public String getEventDatabaseSummary() {}

    public boolean loginUser(String username, String password) {
        if (userInfo.validateUserLogin(username, password)) {
            return true;
        }
        else {
            return false;
        }
    }
}