import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.text.SimpleDateFormat;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskDatabaseTest {

    private TaskDatabase db;

    @BeforeEach
    void setUp() {
        db = new TaskDatabase();
    }

    private Task mkTask(int id, String name, String notes) {
        Task t = new Task();
        Map<String, Object> init = new HashMap<>();
        init.put("taskId", id);
        init.put("name", name);
        init.put("description", "desc");
        init.put("dueDate", new Date());
        init.put("tags", new ArrayList<>(List.of("init")));
        init.put("notes", notes);
        t.editTask(init);
        return t;
    }

    private Task mkTask(int id, String name) {
        Task t = new Task();
        Map<String, Object> init = new HashMap<>();
        init.put("taskId", id);
        init.put("name", name);
        init.put("description", "D-" + id);
        init.put("dueDate", new Date());
        init.put("tags", List.of("tag" + id));
        init.put("notes", "N-" + id);
        t.editTask(init);
        return t;
    }

    @Test
    void testAddTask_thenGetById_andGetAllTasks() {
        Task t1 = mkTask(1, "A");
        Task t2 = mkTask(2, "B");
        db.addTask(t1);
        db.addTask(t2);

        assertEquals(2, db.getAllTasks().size());
        assertTrue(db.getTaskById(1).getTaskInfo().contains("A"));
        assertNotNull(db.getTaskById(2));
        assertNull(db.getTaskById(999));
    }

    @Test
    void testDeleteTask_removesTask_without_affectingOthers() {
        db.addTask(mkTask(10, "Keep"));
        db.addTask(mkTask(11, "DeleteMe"));

        db.deleteTask(11);

        assertNull(db.getTaskById(11));
        assertNotNull(db.getTaskById(10));
        assertEquals(1, db.getAllTasks().size());
    }

    @Test
    void testUpdateTask_appliesChanges_onExistingTask() {
        db.addTask(mkTask(20, "Old"));

        Map<String, Object> patch = new HashMap<>();
        patch.put("name", "New");
        patch.put("tags", List.of("x", "y"));
        db.updateTask(20, patch);

        String info = db.getTaskById(20).getTaskInfo();
        assertAll(
            () -> assertTrue(info.contains("New")),
            () -> assertTrue(info.contains("x")),
            () -> assertTrue(info.contains("y")),
            () -> assertFalse(info.contains("Old"))
        );
    }

    @Test
    void testDisplayAllTasks_printsAllTasks() {
        db.addTask(mkTask(30, "Alpha"));
        db.addTask(mkTask(31, "Beta"));

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream original = System.out;
        System.setOut(new java.io.PrintStream(baos));
        try {
            db.displayAllTasks();
        } finally {
            System.setOut(original);
        }

        String out = baos.toString();
        assertTrue(out.contains("Alpha"));
        assertTrue(out.contains("Beta"));
    }

    @Test
    void testUpdateTask_delegatesToTaskEdit_andMutatesSharedInstance() throws Exception {
        Task t = mkTask(200, "Before", "notes");
        db.addTask(t);

        Date newDue = new SimpleDateFormat("yyyy-MM-dd").parse("2025-11-30");
        Map<String, Object> patch = new HashMap<>();
        patch.put("name", "After");
        patch.put("dueDate", newDue);
        patch.put("tags", List.of("a", "b", "c"));

        db.updateTask(200, patch);

        String info = t.getTaskInfo();
        assertAll(
            () -> assertTrue(info.contains("After")),
            () -> assertTrue(info.contains("a")),
            () -> assertTrue(info.contains("b")),
            () -> assertTrue(info.contains("c")),
            () -> assertTrue(info.contains("2025") || info.contains("11") || info.contains("30"))
        );
    }

    @Test
    void testDeleteTask_removesFromContainer_andFromDisplay() {
        db.addTask(mkTask(300, "Keep", "k"));
        db.addTask(mkTask(301, "Gone", "g"));

        db.deleteTask(301);

        assertTrue(db.getAllTasks().stream().noneMatch(t -> t.getTaskInfo().contains("Gone")));

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream original = System.out;
        System.setOut(new java.io.PrintStream(baos));
        try {
            db.displayAllTasks();
        } finally {
            System.setOut(original);
        }
        String out = baos.toString();
        assertTrue(out.contains("Keep"));
        assertFalse(out.contains("Gone"));
    }

    @Test
    void testGetTaskById_returnsSameInstance_thatWasAdded() {
        Task t = mkTask(400, "SameRef", "n");
        db.addTask(t);

        Task fetched = db.getTaskById(400);
        assertSame(t, fetched, "DB should store and return the same Task instance (composition/containment)");
    }
}
