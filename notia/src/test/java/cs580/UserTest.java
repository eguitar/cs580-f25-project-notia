package cs580;

import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;
    private UserData userData;

    @BeforeEach
    public void setup() {
        userData = new UserData("John", "Doe", "jdoe", "pw123", "john@example.com");
        user = new User(userData);
    }

    @Test
    public void testAddGetRemoveEditTask() {
        Task task = new Task("Task1", "Desc1", new Date(), new ArrayList<>(), "Notes1");
        user.addTask(task);
        int id = 0;
        Task fetched = user.getTask(id);

        assertNotNull(fetched);
        assertEquals("Task1", fetched.getTaskName());

        Task newTask = new Task("Task2", "Desc2", new Date(), new ArrayList<>(), "Notes2");
        assertTrue(user.editTask(id, newTask));
        Task updated = user.getTask(id);
        assertEquals("Task2", updated.getTaskName());

        assertTrue(user.removeTask(id));
        assertNull(user.getTask(id));
    }

    @Test
    public void testAddGetRemoveEditEvent() {
        Date start = new Date();
        Date end = new Date(start.getTime() + 10000);
        Event event = new Event("Event1", "Desc1", "Loc1", start, end, "Notes1");
        user.addEvent(event);
        int id = 0;
        Event fetched = user.getEvent(id);

        assertNotNull(fetched);
        assertEquals("Event1", fetched.getEventName());

        Event newEvent = new Event("Event2", "Desc2", "Loc2", start, end, "Notes2");
        assertTrue(user.editEvent(id, newEvent));
        Event updated = user.getEvent(id);
        assertEquals("Event2", updated.getEventName());

        assertTrue(user.removeEvent(id));
        assertNull(user.getEvent(id));
    }

    @Test
    public void testLoginUser() {
        assertTrue(user.loginUser("jdoe", "pw123"));
        assertFalse(user.loginUser("jdoe", "wrongpw"));
        assertFalse(user.loginUser("wrongname", "pw123"));
    }

    @Test
    public void testUndoFunctionality() {
        Task task = new Task("TaskUndo", "UndoTest", new Date(), new ArrayList<>(), "Notes");
        user.addTask(task);
        assertTrue(user.undo());

        assertFalse(user.undo());
    }

    @Test
    public void testGetUserInfo() {
        String info = user.getUserInfo();
        assertTrue(info.contains("John"));
        assertTrue(info.contains("Doe"));
        assertTrue(info.contains("jdoe"));
        assertTrue(info.contains("pw123"));
    }
}