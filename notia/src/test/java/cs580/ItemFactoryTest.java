package cs580;

import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ItemFactoryTest {

    private InputStream systemInBackup;

    @BeforeEach
    public void setUp() {
        systemInBackup = System.in;
    }

    @AfterEach
    public void tearDown() {
        System.setIn(systemInBackup);
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(testIn);
    }

    @Test
    public void testEventFactoryCreateItem() throws Exception {
        String input = ""
                + "Sample Event\n"
                + "Description of Event\n"
                + "Event Location\n"
                + "2025-12-10\n"
                + "2025-12-12\n"
                + "Event notes here\n";

        provideInput(input);
        EventFactory factory = EventFactory.getInstance(new java.util.Scanner(System.in));
        Event event = factory.createItem();

        assertEquals("Sample Event", event.getEventName());
        assertEquals("Description of Event", event.getEventDescription());
        assertEquals("Event Location", event.getEventLocation());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        Date expectedStartDate = sdf.parse("2025-12-10");
        Date expectedEndDate = sdf.parse("2025-12-12");

        assertEquals(expectedStartDate, event.getEventStartDate());
        assertEquals(expectedEndDate, event.getEventEndDate());

        assertEquals("Event notes here", event.getEventNotes());
    }

    @Test
    public void testTaskFactoryCreateItem() throws Exception {
        String input = ""
                + "Sample Task\n"
                + "Task Description\n"
                + "2025-11-30\n"
                + "tag1, tag2, tag3\n"
                + "Task notes here\n";

        provideInput(input);
        TaskFactory factory = TaskFactory.getInstance(new java.util.Scanner(System.in));
        Task task = factory.createItem();

        assertEquals("Sample Task", task.getTaskName());
        assertEquals("Task Description", task.getTaskDescription());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        Date expectedDate = sdf.parse("2025-11-30");

        assertEquals(expectedDate, task.getTaskDate());

        assertNotNull(task.getTaskTags());
        assertEquals(3, task.getTaskTags().size());
        assertTrue(task.getTaskTags().contains("tag1"));
        assertTrue(task.getTaskTags().contains("tag2"));
        assertTrue(task.getTaskTags().contains("tag3"));

        assertEquals("Task notes here", task.getTaskNotes());
    }
}