package cs580;

public class User {
    private final UserData userInfo;
    private final TaskDatabase taskDatabase;
    private final EventDatabase eventDatabase;
    private UserMemento lastMemento;

    public User(UserData userInfo) {
        this.userInfo = userInfo;
        this.taskDatabase = new TaskDatabase();
        this.eventDatabase = new EventDatabase();
    }

    public void addTask(Task task) {
        this.saveState();
        taskDatabase.addTask(task);
    }
    public boolean removeTask(int taskID) {
        this.saveState();
        return taskDatabase.removeTask(taskID);
    }
    public Task getTask(int taskID) { return taskDatabase.getTaskByID(taskID); }
    public boolean editTask(int taskID, Task newTask) {
        this.saveState();
        return taskDatabase.updateTaskByID(taskID, newTask);
    }

    public void addEvent(Event event) {
        this.saveState();
        eventDatabase.addEvent(event);
    }
    public boolean removeEvent(int eventID) {
        this.saveState();
        return eventDatabase.removeEvent(eventID);
    }
    public Event getEvent(int eventID) { return eventDatabase.getEventByID(eventID); }
    public boolean editEvent(int eventID, Event newEvent) {
        this.saveState();
        return eventDatabase.updateEventByID(eventID, newEvent);
    }

    public String getUserInfo() { return userInfo.getUserDataSummary(); }

    public void displayTaskDatabaseSummary(TaskSortStrategy strategy) {
        strategy.sort(taskDatabase.getAllTasks());
        displayTaskDatabaseSummary();
    }

    public void displayEventDatabaseSummary(EventSortStrategy strategy) {
        strategy.sort(eventDatabase.getAllEvents());
        displayEventDatabaseSummary();
    }

    public void displayTaskDatabaseSummary() {
        System.out.println("----------------------------------------");
        for (Task task : taskDatabase) {
            System.out.println(task.getTaskSummary());
            System.out.println("----------------------------------------");
        }
    }

    public void displayEventDatabaseSummary() {
        System.out.println("----------------------------------------");
        for (Event event : eventDatabase) {
            System.out.println(event.getEventSummary());
            System.out.println("----------------------------------------");
        }
    }

    public boolean loginUser(String username, String password) {
        if (userInfo.validateUserLogin(username, password)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void saveState() {
        lastMemento = new UserMemento(taskDatabase.createMemento(), eventDatabase.createMemento());
    }

    public boolean undo() {
        if (lastMemento != null) {
            taskDatabase.restoreFromMemento(lastMemento.getTaskMemento());
            eventDatabase.restoreFromMemento(lastMemento.getEventMemento());
            lastMemento = null;
            return true;
        }
        else {
            return false;
        }
    }
}