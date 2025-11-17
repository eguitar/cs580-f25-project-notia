import cs580.event.Event;
import cs580.event.EventErrorMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class EventTest {

    // --- Helpers ---
    private static Date date(int y, int m, int d) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.clear();
        m = Math.max(1, Math.min(m, 12)) - 1;
        c.set(y, m, d, 0, 0, 0);
        return c.getTime();
    }

    private static Event buildEvent(int id, String name, Date start, Date end) {
        return new Event.EventBuilder(id, name)
                .startDate(start)
                .endDate(end)
                .build();
    }

    @Test
    public void testEventValidInitialization() {
        Date s = date(2025, 1, 1);
        Date e = date(2025, 1, 2);
        Event ev = new Event.EventBuilder(1, "Kickoff")
                .description("desc")
                .location("Room 101")
                .notes("bring snacks")
                .startDate(s)
                .endDate(e)
                .build();

        Assertions.assertEquals(1, ev.getEventID());
        Assertions.assertEquals("Kickoff", ev.getEventName());
        Assertions.assertEquals("desc", ev.getEventDescription());
        Assertions.assertEquals("Room 101", ev.getEventLocation());
        Assertions.assertEquals(s, ev.getEventStartDate());
        Assertions.assertEquals(e, ev.getEventEndDate());
        Assertions.assertEquals("bring snacks", ev.getEventNotes());
    }

    @Test
    public void testEventInvalidInitialization() {
        // Negative ID rejected in builder ctor
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Event.EventBuilder(-1, "A"));
        // Empty name rejected in builder ctor
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Event.EventBuilder(2, " "));
        // Date range rejected in build()
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Event.EventBuilder(3, "X")
                .startDate(date(2025, 2, 2))
                .endDate(date(2025, 2, 1))
                .build());
    }

    @Test
    public void testSetTitleValid() {
        Event ev = buildEvent(10, "Init", null, null);
        ev.setEventName(" New Title ");
        Assertions.assertEquals("New Title", ev.getEventName());
    }

    @Test
    public void testSetTitleInvalid() {
        Event ev = buildEvent(11, "Init", null, null);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> ev.setEventName("\t \n"));
        Assertions.assertTrue(ex.getMessage().contains(EventErrorMessage.EVENT_NAME_NULL_OR_EMPTY.getMessage()));
    }

    @Test
    public void testSetDateValid() {
        Event ev = buildEvent(12, "D", null, null);
        Date s = date(2025, 3, 1);
        Date e = date(2025, 3, 1); // equal is allowed
        ev.setEventStartDate(s);
        ev.setEventEndDate(e);
        Assertions.assertEquals(s, ev.getEventStartDate());
        Assertions.assertEquals(e, ev.getEventEndDate());
    }

    @Test
    public void testSetDateInvalid() {
        Event ev = buildEvent(13, "D", null, null);
        // End before start -> when setting end after start exists
        ev.setEventStartDate(date(2025, 4, 2));
        Event finalEv = ev;
        IllegalArgumentException ex1 = Assertions.assertThrows(IllegalArgumentException.class,
                () -> finalEv.setEventEndDate(date(2025, 4, 1)));
        Assertions.assertTrue(ex1.getMessage().contains(EventErrorMessage.END_DATE_BEFORE_START_DATE.getMessage()));

        // Start after end -> when end already set earlier
        ev = buildEvent(14, "D2", null, null);
        ev.setEventEndDate(date(2025, 5, 1));
        Event finalEv1 = ev;
        IllegalArgumentException ex2 = Assertions.assertThrows(IllegalArgumentException.class,
                () -> finalEv1.setEventStartDate(date(2025, 5, 2)));
        Assertions.assertTrue(ex2.getMessage().contains(EventErrorMessage.START_DATE_AFTER_END_DATE.getMessage()));
    }

    @Test
    public void testSetTagsValid() {
        // Event has no tags; treat "notes" as the closest analogue
        Event ev = buildEvent(15, "T", null, null);
        ev.setEventNotes(" alpha, beta ");
        Assertions.assertEquals("alpha, beta", ev.getEventNotes());
    }

    @Test
    public void testSetTagsInvalid() {
        // No invalidity for notes (null or empty allowed -> sanitized), so just verify null handling
        Event ev = buildEvent(16, "T", null, null);
        ev.setEventNotes(null);
        Assertions.assertNull(ev.getEventNotes());
    }

    @Test
    public void testGetTitle() {
        Event ev = buildEvent(20, "Title", null, null);
        Assertions.assertEquals("Title", ev.getEventName());
    }

    @Test
    public void testGetDate() {
        Date s = date(2025, 6, 1);
        Date e = date(2025, 6, 3);
        Event ev = buildEvent(21, "D", s, e);
        Assertions.assertEquals(s, ev.getEventStartDate());
        Assertions.assertEquals(e, ev.getEventEndDate());
    }

    @Test
    public void testGetTags() {
        Event ev = buildEvent(22, "T", null, null);
        ev.setEventNotes("n1");
        Assertions.assertEquals("n1", ev.getEventNotes());
    }

    @Test
    public void testEditEventWithValidData() {
        Event ev = new Event.EventBuilder(30, "Base")
                .description("desc")
                .location("loc")
                .startDate(date(2025, 7, 1))
                .endDate(date(2025, 7, 2))
                .build();

        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "Renamed");
        updates.put("location", "Main Hall");
        updates.put("notes", "bring id");
        ev.editEvent(updates);

        Assertions.assertEquals("Renamed", ev.getEventName());
        Assertions.assertEquals("Main Hall", ev.getEventLocation());
        Assertions.assertEquals("bring id", ev.getEventNotes());
    }

    @Test
    public void testEditEventWithInvalidData() {
        Event ev = new Event.EventBuilder(31, "Base")
                .startDate(date(2025, 8, 1))
                .endDate(date(2025, 8, 2))
                .build();

        // Empty map -> UPDATES_MAP_EMPTY
        IllegalArgumentException ex1 = Assertions.assertThrows(IllegalArgumentException.class, () -> ev.editEvent(new HashMap<>()));
        Assertions.assertTrue(ex1.getMessage().toLowerCase().contains("empty"));

        // Invalid field name -> INVALID_FIELD_NAME
        Map<String, Object> bad = Map.of("invalidField", "x");
        IllegalArgumentException ex2 = Assertions.assertThrows(IllegalArgumentException.class, () -> ev.editEvent(bad));
        Assertions.assertTrue(ex2.getMessage().toLowerCase().contains("invalid field"));

        // Wrong type -> e.g., START_DATE requires Date
        Map<String, Object> wrong = Map.of("start_date", "not a date");
        IllegalArgumentException ex3 = Assertions.assertThrows(IllegalArgumentException.class, () -> ev.editEvent(wrong));
        Assertions.assertTrue(ex3.getMessage().toLowerCase().contains("date"));
    }

    @Test
    public void testNullTitleException() {
        Event ev = buildEvent(40, "Init", null, null);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> ev.setEventName(null));
        Assertions.assertTrue(ex.getMessage().contains(EventErrorMessage.EVENT_NAME_NULL_OR_EMPTY.getMessage()));
    }

    @Test
    public void testNullDateException() {
    
    }

    @Test
    public void testEventOwnershipInEventDatabase() {
    }
}