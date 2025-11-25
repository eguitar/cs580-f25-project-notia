package cs580;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testTaskValidInitialization() {
        Date date = new Date(1700000000000L);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("urgent");
        tags.add("home");

        Task task = new Task("Homework", "Math exercises", date, tags, "Do before Monday");
        task.setTaskID(101);

        assertEquals(101, task.getTaskID());
        assertEquals("Homework", task.getTaskName());
        assertEquals("Math exercises", task.getTaskDescription());
        assertEquals(date, task.getTaskDate());
        assertEquals(tags, task.getTaskTags());
        assertEquals("Do before Monday", task.getTaskNotes());

        String summary = task.getTaskSummary();
        assertTrue(summary.contains("101"));
        assertTrue(summary.contains("Homework"));
        assertTrue(summary.contains("Math exercises"));
        assertTrue(summary.contains("urgent"));
        assertTrue(summary.contains("Do before Monday"));
    }

    @Test
    public void testTaskCopyConstructor() {
        Date date = new Date(1700000000000L);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("work");

        Task original = new Task("Project", "Complete report", date, tags, "Check references");
        original.setTaskID(202);

        Task copy = new Task(original);

        assertEquals(original.getTaskID(), copy.getTaskID());
        assertEquals(original.getTaskName(), copy.getTaskName());
        assertEquals(original.getTaskDescription(), copy.getTaskDescription());
        assertEquals(original.getTaskDate(), copy.getTaskDate());
        assertEquals(original.getTaskTags(), copy.getTaskTags());
        assertEquals(original.getTaskNotes(), copy.getTaskNotes());

        // Mutate original date and tags to ensure deep copy
        date.setTime(0L);
        tags.add("extra");

        assertNotEquals(date, copy.getTaskDate());
        assertFalse(copy.getTaskTags().contains("extra"));
    }

    @Test
    public void testSettersAndGetters() {
        Task task = new Task(null, null, null, null, null);

        task.setTaskID(303);
        assertEquals(303, task.getTaskID());

        task.setTaskName("Shopping");
        assertEquals("Shopping", task.getTaskName());

        task.setTaskDescription("Buy groceries");
        assertEquals("Buy groceries", task.getTaskDescription());

        Date now = new Date();
        task.setTaskDate(now);
        assertEquals(now, task.getTaskDate());

        ArrayList<String> tags = new ArrayList<>();
        tags.add("errands");
        task.setTaskTags(tags);
        assertEquals(tags, task.getTaskTags());

        task.setTaskNotes("Use discount coupons");
        assertEquals("Use discount coupons", task.getTaskNotes());
    }

    @Test
    public void testSettersAcceptNulls() {
        Date date = new Date();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("test");

        Task task = new Task("Title", "Desc", date, tags, "Notes");

        task.setTaskName(null);
        task.setTaskDescription(null);
        task.setTaskDate(null);
        task.setTaskTags(null);
        task.setTaskNotes(null);

        assertNull(task.getTaskName());
        assertNull(task.getTaskDescription());
        assertNull(task.getTaskDate());
        assertNull(task.getTaskTags());
        assertNull(task.getTaskNotes());
    }
}