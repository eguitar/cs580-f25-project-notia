package cs580;

import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class MementoTest {

    private TaskDatabase taskDatabase;
    private EventDatabase eventDatabase;

    @BeforeEach
    public void setup() {
        taskDatabase = new TaskDatabase();
        eventDatabase = new EventDatabase();
    }

    @Test
    public void testTaskDatabaseMementoCreationAndRestore() {
        Task task1 = new Task("Task1", "Desc1", new Date(), new ArrayList<>(), "Notes1");
        taskDatabase.addTask(task1);
        int initialTaskID = task1.getTaskID();

        TaskDatabaseMemento memento = taskDatabase.createMemento();

        Task task2 = new Task("Task2", "Desc2", new Date(), new ArrayList<>(), "Notes2");
        taskDatabase.addTask(task2);

        taskDatabase.restoreFromMemento(memento);

        Task retrieved = taskDatabase.getTaskByID(initialTaskID);
        assertNotNull(retrieved);
        assertEquals("Task1", retrieved.getTaskName());

        assertNull(taskDatabase.getTaskByID(task2.getTaskID()));

        Task newTaskAfterRestore = new Task("NewTask", "Desc", new Date(), new ArrayList<>(), null);
        taskDatabase.addTask(newTaskAfterRestore);
        assertEquals(memento.getTaskIDSnapshot(), newTaskAfterRestore.getTaskID());
    }

    @Test
    public void testEventDatabaseMementoCreationAndRestore() {
        Event event1 = new Event("Event1", "Desc1", "Loc1", new Date(), new Date(), "Notes1");
        eventDatabase.addEvent(event1);
        int initialEventID = event1.getEventID();

        EventDatabaseMemento memento = eventDatabase.createMemento();

        Event event2 = new Event("Event2", "Desc2", "Loc2", new Date(), new Date(), "Notes2");
        eventDatabase.addEvent(event2);

        eventDatabase.restoreFromMemento(memento);

        Event retrieved = eventDatabase.getEventByID(initialEventID);
        assertNotNull(retrieved);
        assertEquals("Event1", retrieved.getEventName());

        assertNull(eventDatabase.getEventByID(event2.getEventID()));

        Event newEventAfterRestore = new Event("NewEvent", "Desc", "Loc", new Date(), new Date(), null);
        eventDatabase.addEvent(newEventAfterRestore);
        assertEquals(memento.getEventIDSnapshot(), newEventAfterRestore.getEventID());
    }

    @Test
    public void testUserMementoIntegration() {
        Task task = new Task("UserTask", "Desc", new Date(), new ArrayList<>(), "Notes");
        Event event = new Event("UserEvent", "Desc", "Loc", new Date(), new Date(), "Notes");
        taskDatabase.addTask(task);
        eventDatabase.addEvent(event);

        TaskDatabaseMemento taskMemento = taskDatabase.createMemento();
        EventDatabaseMemento eventMemento = eventDatabase.createMemento();

        UserMemento userMemento = new UserMemento(taskMemento, eventMemento);

        assertEquals(taskMemento, userMemento.getTaskMemento());
        assertEquals(eventMemento, userMemento.getEventMemento());
    }
}