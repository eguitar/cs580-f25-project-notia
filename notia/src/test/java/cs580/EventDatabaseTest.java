package cs580;

import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EventDatabaseTest {

    private EventDatabase db;

    @BeforeEach
    public void setup() {
        db = new EventDatabase();
    }

    @Test
    public void testAddAndGetEvent() {
        Event event = new Event("Meeting", "Discuss project", "Office", new Date(), new Date(), "Bring reports");
        db.addEvent(event);
        int id = event.getEventID();
        Event fetched = db.getEventByID(id);

        assertNotNull(fetched);
        assertEquals("Meeting", fetched.getEventName());
        assertEquals(id, fetched.getEventID());
    }

    @Test
    public void testRemoveEvent() {
        Event event = new Event("Workshop", "Java training", "Room 1", new Date(), new Date(), "Laptop required");
        db.addEvent(event);
        int id = event.getEventID();

        boolean removed = db.removeEvent(id);
        assertTrue(removed);
        assertNull(db.getEventByID(id));
    }

    @Test
    public void testUpdateEvent() {
        Event event = new Event("Seminar", "Tech trends", "Auditorium", new Date(), new Date(), "Notes");
        db.addEvent(event);
        int id = event.getEventID();

        Event newEvent = new Event("Seminar Updated", "Tech trends updated", "Auditorium", new Date(), new Date(), "New notes");
        boolean updated = db.updateEventByID(id, newEvent);

        assertTrue(updated);
        Event fetched = db.getEventByID(id);
        assertEquals("Seminar Updated", fetched.getEventName());
        assertEquals(id, fetched.getEventID());
    }

    @Test
    public void testGetEventByName() {
        Event event = new Event("Conference", "Annual meet", "Hall", new Date(), new Date(), null);
        db.addEvent(event);

        Event fetched = db.getEventByName("Conference");
        assertNotNull(fetched);
        assertEquals("Conference", fetched.getEventName());

        Event notFound = db.getEventByName("Nonexistent");
        assertNull(notFound);
    }

    @Test
    public void testCreateAndRestoreMemento() {
        Event event = new Event("Gala", "Charity event", "Banquet", new Date(), new Date(), "Formal dress");
        db.addEvent(event);

        EventDatabaseMemento memento = db.createMemento();
        db.removeEvent(event.getEventID());
        assertNull(db.getEventByID(event.getEventID()));

        db.restoreFromMemento(memento);
        assertNotNull(db.getEventByID(event.getEventID()));
    }

    @Test
    public void testIterator() {
        Event event1 = new Event("Event1", "Desc1", "Loc1", new Date(), new Date(), null);
        Event event2 = new Event("Event2", "Desc2", "Loc2", new Date(), new Date(), null);
        db.addEvent(event1);
        db.addEvent(event2);

        int count = 0;
        for (Event e : db) {
            assertNotNull(e);
            count++;
        }
        assertEquals(2, count);
    }
}