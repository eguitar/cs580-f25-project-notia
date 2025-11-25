package cs580;

import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskSortStrategyTest {

    private Task task1, task2, task3;
    private List<Task> tasks;

    @BeforeEach
    public void setup() throws Exception {
        task1 = new Task("Alpha", "Desc A", new Date(1700000000000L), new ArrayList<>(), "Notes A");
        task1.setTaskID(1);

        task2 = new Task("Charlie", "Desc C", new Date(1710000000000L), new ArrayList<>(), "Notes C");
        task2.setTaskID(3);

        task3 = new Task("Bravo", "Desc B", new Date(1690000000000L), new ArrayList<>(), "Notes B");
        task3.setTaskID(2);

        tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
    }

    @Test
    public void testSortByDate() {
        TaskSortStrategy strategy = new TaskSortByDate();
        strategy.sort(tasks);

        assertEquals("Bravo", tasks.get(0).getTaskName());
        assertEquals("Alpha", tasks.get(1).getTaskName());
        assertEquals("Charlie", tasks.get(2).getTaskName());
    }

    @Test
    public void testSortByID() {
        TaskSortStrategy strategy = new TaskSortByID();
        strategy.sort(tasks);

        assertEquals(1, tasks.get(0).getTaskID());
        assertEquals(2, tasks.get(1).getTaskID());
        assertEquals(3, tasks.get(2).getTaskID());
    }

    @Test
    public void testSortByName() {
        TaskSortStrategy strategy = new TaskSortByName();
        strategy.sort(tasks);

        assertEquals("Alpha", tasks.get(0).getTaskName());
        assertEquals("Bravo", tasks.get(1).getTaskName());
        assertEquals("Charlie", tasks.get(2).getTaskName());
    }

    @Test
    public void testSortByDateWithNulls() {
        tasks.clear();

        Task nullDateTask = new Task("NullDate", "Desc", null, new ArrayList<>(), "Notes");
        nullDateTask.setTaskID(4);

        Task validDateTask = new Task("ValidDate", "Desc", new Date(1700000000000L), new ArrayList<>(), "Notes");
        validDateTask.setTaskID(5);

        tasks.add(nullDateTask);
        tasks.add(validDateTask);

        TaskSortStrategy strategy = new TaskSortByDate();
        strategy.sort(tasks);

        assertEquals("NullDate", tasks.get(0).getTaskName());
        assertEquals("ValidDate", tasks.get(1).getTaskName());
    }
}