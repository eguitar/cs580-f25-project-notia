package cs580;

import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    @Test
    public void testEventValidInitialization() {
        Date start = new Date(1700000000000L);
        Date end = new Date(1700003600000L);
        Event e = new Event("Hackathon", "Programming challenge", "Lab", start, end, "Bring your laptop.");
        e.setEventID(42);

        assertEquals(42, e.getEventID());
        assertEquals("Hackathon", e.getEventName());
        assertEquals("Programming challenge", e.getEventDescription());
        assertEquals("Lab", e.getEventLocation());
        assertEquals(start, e.getEventStartDate());
        assertEquals(end, e.getEventEndDate());
        assertEquals("Bring your laptop.", e.getEventNotes());
        assertTrue(e.getEventSummary().contains("Hackathon"));
        assertTrue(e.getEventSummary().contains("Programming challenge"));
        assertTrue(e.getEventSummary().contains("Lab"));
        assertTrue(e.getEventSummary().contains("Bring your laptop."));
    }

    @Test
    public void testEventInvalidInitialization() {
        Event e = new Event(null, null, null, null, null, null);

        assertNull(e.getEventName());
        assertNull(e.getEventDescription());
        assertNull(e.getEventLocation());
        assertNull(e.getEventStartDate());
        assertNull(e.getEventEndDate());
        assertNull(e.getEventNotes());
        assertTrue(e.getEventSummary().contains("Event ID"));
    }

    @Test
    public void testSettersAndGetters() {
        Date start = new Date();
        Date end = new Date();
        Event e = new Event("a", "b", "c", start, end, "d");

        e.setEventID(123);
        assertEquals(123, e.getEventID());
        e.setEventName("NAME");
        assertEquals("NAME", e.getEventName());
        e.setEventDescription("DESC");
        assertEquals("DESC", e.getEventDescription());
        e.setEventLocation("ZONE");
        assertEquals("ZONE", e.getEventLocation());

        Date other = new Date(System.currentTimeMillis() + 1000);
        e.setEventStartDate(other);
        assertEquals(other, e.getEventStartDate());
        e.setEventEndDate(null);
        assertNull(e.getEventEndDate());

        e.setEventNotes("xyz");
        assertEquals("xyz", e.getEventNotes());
    }

    @Test
    public void testEventCopyConstructor() {
        Date start = new Date(1730000000000L);
        Date end = new Date(1730003600000L);
        Event e1 = new Event("NAME", "DESC", "LOC", start, end, "NT");
        e1.setEventID(500);
        Event e2 = new Event(e1);

        assertEquals(e1.getEventID(), e2.getEventID());
        assertEquals(e1.getEventName(), e2.getEventName());
        assertEquals(e1.getEventDescription(), e2.getEventDescription());
        assertEquals(e1.getEventLocation(), e2.getEventLocation());
        assertEquals(e1.getEventStartDate(), e2.getEventStartDate());
        assertEquals(e1.getEventEndDate(), e2.getEventEndDate());
        assertEquals(e1.getEventNotes(), e2.getEventNotes());

        start.setTime(0L);
        end.setTime(0L);
        assertNotEquals(start, e2.getEventStartDate());
        assertNotEquals(end, e2.getEventEndDate());
    }

    @Test
    public void testSettersAcceptNulls() {
        Date start = new Date();
        Date end = new Date();
        Event e = new Event("name", "desc", "loc", start, end, "notes");

        e.setEventName(null);
        e.setEventDescription(null);
        e.setEventLocation(null);
        e.setEventStartDate(null);
        e.setEventEndDate(null);
        e.setEventNotes(null);

        assertNull(e.getEventName());
        assertNull(e.getEventDescription());
        assertNull(e.getEventLocation());
        assertNull(e.getEventStartDate());
        assertNull(e.getEventEndDate());
        assertNull(e.getEventNotes());
    }

    @Test
    public void testEventSummaryAllNulls() {
        Event e = new Event(null, null, null, null, null, null);
        e.setEventID(0);
        String summary = e.getEventSummary();
        assertTrue(summary.contains("Event ID: 0"));
    }
}