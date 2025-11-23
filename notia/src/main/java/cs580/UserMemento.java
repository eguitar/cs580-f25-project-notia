package cs580;

public class UserMemento {
    private final TaskDatabaseMemento taskMemento;
    private final EventDatabaseMemento eventMemento;

    public UserMemento(TaskDatabaseMemento taskMemento,
                        EventDatabaseMemento eventMemento) {
        this.taskMemento = taskMemento;
        this.eventMemento = eventMemento;
    }

    public TaskDatabaseMemento getTaskMemento() { return taskMemento; }
    public EventDatabaseMemento getEventMemento() { return eventMemento; }
}
