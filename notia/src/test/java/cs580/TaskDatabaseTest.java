package cs580;

import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TaskDatabaseTest {

    private TaskDatabase db;

    @BeforeEach
    public void setup() {
        db = new TaskDatabase();
    }

    @Test
    public void testAddAndGetTask() {
        Task task = new Task("Code Review", "Review PR", new Date(), new ArrayList<>(), "Check tests");
        db.addTask(task);
        int id = task.getTaskID();
        Task fetched = db.getTaskByID(id);

        assertNotNull(fetched);
        assertEquals("Code Review", fetched.getTaskName());
        assertEquals(id, fetched.getTaskID());
    }

    @Test
    public void testRemoveTask() {
        Task task = new Task("Bug Fix", "Fix login bug", new Date(), new ArrayList<>(), null);
        db.addTask(task);
        int id = task.getTaskID();

        boolean removed = db.removeTask(id);
        assertTrue(removed);
        assertNull(db.getTaskByID(id));
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task("Design Doc", "Create design document", new Date(), new ArrayList<>(), "Initial draft");
        db.addTask(task);
        int id = task.getTaskID();

        Task newTask = new Task("Design Doc Updated", "Updated design", new Date(), new ArrayList<>(), "Final draft");
        boolean updated = db.updateTaskByID(id, newTask);

        assertTrue(updated);
        Task fetched = db.getTaskByID(id);
        assertEquals("Design Doc Updated", fetched.getTaskName());
        assertEquals(id, fetched.getTaskID());
    }

    @Test
    public void testGetTaskByName() {
        Task task = new Task("Deploy", "Deploy to production", new Date(), new ArrayList<>(), "Notify team");
        db.addTask(task);

        Task fetched = db.getTaskByName("Deploy");
        assertNotNull(fetched);
        assertEquals("Deploy", fetched.getTaskName());

        Task notFound = db.getTaskByName("Nonexistent");
        assertNull(notFound);
    }

    @Test
    public void testCreateAndRestoreMemento() {
        Task task = new Task("Refactor", "Refactor codebase", new Date(), new ArrayList<>(), null);
        db.addTask(task);

        TaskDatabaseMemento memento = db.createMemento();
        db.removeTask(task.getTaskID());
        assertNull(db.getTaskByID(task.getTaskID()));

        db.restoreFromMemento(memento);
        assertNotNull(db.getTaskByID(task.getTaskID()));
    }

    @Test
    public void testIterator() {
        Task task1 = new Task("Task1", "Desc1", new Date(), new ArrayList<>(), null);
        Task task2 = new Task("Task2", "Desc2", new Date(), new ArrayList<>(), null);
        db.addTask(task1);
        db.addTask(task2);

        int count = 0;
        for (Task t : db) {
            assertNotNull(t);
            count++;
        }
        assertEquals(2, count);
    }
}