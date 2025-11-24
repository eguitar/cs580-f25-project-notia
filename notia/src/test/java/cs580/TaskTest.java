package cs580;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void testTaskValidInitialization() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse("2025-11-24");
        ArrayList<String> tags = new ArrayList<>();
        tags.add("home");
        Task t = new Task("Buy milk", "Buy 2 gallons", d, tags, "Remember coupon");
        assertEquals("Buy milk", t.getTaskName());
        assertEquals("Buy 2 gallons", t.getTaskDescription());
        assertEquals(d, t.getTaskDate());
        assertEquals(tags, t.getTaskTags());
        assertEquals("Remember coupon", t.getTaskNotes());
    }

    @Test
    public void testTaskNullDateAndTags() {
        Task t = new Task("NoDate", "desc", null, null, null);
        assertNull(t.getTaskDate());
        assertNull(t.getTaskTags());
        assertNull(t.getTaskNotes());
    }

    @Test
    public void testTaskCopyConstructorDeepCopy() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse("2025-11-24");
        ArrayList<String> tags = new ArrayList<>();
        tags.add("a");
        Task t1 = new Task("A", "B", d, tags, "N");
        Task t2 = new Task(t1);
        assertEquals(t1.getTaskName(), t2.getTaskName());
        assertNotSame(t1.getTaskTags(), t2.getTaskTags());
        t1.getTaskTags().add("new");
        assertFalse(t2.getTaskTags().contains("new"));
    }

    @Test
    public void testGetTaskSummaryFormatting() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse("2025-11-24");
        Task t = new Task("Name", "Desc", d, new ArrayList<>(), "Notes");
        String summary = t.getTaskSummary();
        assertTrue(summary.contains("Task ID:"));
        assertTrue(summary.contains("Name: Name"));
        assertTrue(summary.contains("Date: 2025-11-24"));
    }
}
