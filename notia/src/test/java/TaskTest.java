package com.notia.task;

import org.junit.jupiter.api.Test;
import java.text.SimpleDateFormat;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private Task newTask(int id) {
        Task t = new Task();
        Map<String, Object> info = new HashMap<>();
        info.put("taskId", id);
        info.put("name", "Task " + id);
        info.put("description", "Desc " + id);
        info.put("dueDate", new GregorianCalendar(2025, Calendar.NOVEMBER, 5).getTime());
        info.put("tags", new ArrayList<>(List.of("school", "urgent")));
        info.put("notes", "Initial notes");
        t.editTask(info);
        return t;
    }

    @Test
    void testGetTaskInfoIncludesAllKeyFields() {
        Task t = newTask(100);
        String info = t.getTaskInfo();
        assertAll(
            () -> assertTrue(info.contains("id=100")),
            () -> assertTrue(info.contains("Task 100")),
            () -> assertTrue(info.contains("Desc 100")),
            () -> assertTrue(info.toLowerCase().contains("tags")),
            () -> assertTrue(info.toLowerCase().contains("notes"))
        );
    }

    @Test
    void testEditTaskUpdatesOnlyProvidedFieldsAndPreservesOthers() {
        Task t = newTask(101);

        Date oldDue = t.getDueDate();
        long oldDueMs = oldDue == null ? -1 : oldDue.getTime();
        String oldDesc = "Desc 101";

        Map<String, Object> patch = new HashMap<>();
        patch.put("name", "Renamed");
        patch.put("tags", List.of("work", "review"));
        t.editTask(patch);

        String info = t.getTaskInfo();
        assertAll(
            () -> assertTrue(info.contains("Renamed")),
            () -> assertTrue(info.contains("work")),
            () -> assertTrue(info.contains("review")),
            () -> assertTrue(info.contains(oldDesc)), // description unchanged
            () -> {
                Date newDue = t.getDueDate();
                long newDueMs = newDue == null ? -1 : newDue.getTime();
                assertEquals(oldDueMs, newDueMs, "dueDate should remain unchanged");
            }
        );
    }

    @Test
    void testEditTaskUpdatesDatesAndHandlesUnknownKeysGracefully() throws Exception {
        Task t = newTask(102);
        Date newDue = new SimpleDateFormat("yyyy-MM-dd").parse("2025-12-31");

        Map<String, Object> patch = new HashMap<>();
        patch.put("dueDate", newDue);
        patch.put("unknownKey", "ignored");
        t.editTask(patch);

        assertNotNull(t.getDueDate());
        assertEquals(newDue.getTime(), t.getDueDate().getTime(), "dueDate should update to new value");
        assertFalse(t.getTaskInfo().contains("ignored"), "unknown patch keys should not leak into info");
    }

    @Test
    void testEditTaskNullValueClearsOptionalFieldLikeNotes() {
        Task t = newTask(103);
        assertNotNull(t.getNotes());

        Map<String, Object> patch = new HashMap<>();
        patch.put("notes", null); // convention: null clears an optional field
        t.editTask(patch);

        assertNull(t.getNotes(), "notes should be cleared to null");
        assertFalse(t.getTaskInfo().toLowerCase().contains("notes:'"), "info should not display notes after clearing");
    }
}
