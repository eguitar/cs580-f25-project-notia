import cs580.event.Event;
import cs580.event.EventErrorMessage;
import cs580.event_database.EventDatabase;
import cs580.event_database.EventDatabaseErrorMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;

public class EventDatabaseTest {
    private EventDatabase db;

    private static Date date(int year, int month, int day) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.clear();
        month = Math.max(1, Math.min(month, 12)) - 1;
        c.set(year, month, day, 0, 0, 0);
        return c.getTime();
    }

    @BeforeEach
    void setUp() {
        db = EventDatabase.getInstance();
        // ensure clean slate between tests
        db.clearAllEvents();
        Assertions.assertEquals(0, db.getEventCount());
    }

    private Event buildEvent(int id, String name, String description, String location,
                             Date start, Date end, String notes) {
        Event.EventBuilder builder = new Event.EventBuilder(id, name);
        if (description != null) builder = builder.description(description);
        if (location != null) builder = builder.location(location);
        if (start != null) builder = builder.startDate(start);
        if (end != null) builder = builder.endDate(end);
        if (notes != null) builder = builder.notes(notes);
        return builder.build();
    }

    @Test
    public void testEventDatabaseValidInitialization() {
        EventDatabase a = EventDatabase.getInstance();
        EventDatabase b = EventDatabase.getInstance();
        Assertions.assertSame(a, b);
    }

    @Test
    public void testEventDatabaseInvalidInitialization() throws NoSuchMethodException {
        // Validate constructor is private so external initialization is not allowed
        Constructor<EventDatabase> ctor = EventDatabase.class.getDeclaredConstructor();
        Assertions.assertTrue(Modifier.isPrivate(ctor.getModifiers()), "Constructor should be private to enforce singleton");
    }

    @Test
    public void testAddEventValid() {
        Event e = buildEvent(100, "Kickoff", "desc", "Room", date(2024, 0, 1), date(2025, 1, 2), "n");
        db.addEvent(e);
        Assertions.assertEquals(1, db.getEventCount());
        Assertions.assertEquals("Kickoff", db.getEventById(100).getEventName());
    }

    @Test
    public void testAddEventDuplicate() {
        Event e1 = buildEvent(101, "A", null, null, date(2025, 1, 1), date(2025, 1, 1), null);
        Event e2 = buildEvent(101, "B", null, null, date(2025, 2, 1), date(2025, 2, 1), null);
        db.addEvent(e1);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> db.addEvent(e2));
        Assertions.assertTrue(ex.getMessage().contains(EventDatabaseErrorMessage.EVENT_ALREADY_EXISTS.format(101)));
    }

    @Test
    public void testAddEventInvalid() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> db.addEvent(null));
        Assertions.assertTrue(ex.getMessage().contains(EventDatabaseErrorMessage.EVENT_NULL.getMessage()));
    }

    @Test
    public void testDeleteEventValidId() {
        Event e = buildEvent(102, "Del", null, null, date(2025, 1, 1), date(2025, 1, 1), null);
        db.addEvent(e);
        Assertions.assertTrue(db.deleteEvent(102));
        Assertions.assertNull(db.getEventById(102));
        Assertions.assertEquals(0, db.getEventCount());
    }

    @Test
    public void testDeleteEventInvalidId() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> db.deleteEvent(-10));
        Assertions.assertTrue(ex.getMessage().contains(EventDatabaseErrorMessage.INVALID_EVENT_ID.format(-10)));
    }

    @Test
    public void testDeleteNotAddedEvent() {
        Assertions.assertFalse(db.deleteEvent(101));
    }

    @Test
    public void testGetAllEventsValid() {
        Event e1 = buildEvent(103, "E1", null, null, date(2025, 1, 1), date(2025, 1, 1), null);
        Event e2 = buildEvent(104, "E2", null, null, date(2025, 1, 2), date(2025, 1, 2), null);
        db.addEvent(e1);
        db.addEvent(e2);
        List<Event> copy = db.getAllEvents();
        Assertions.assertEquals(2, copy.size());
        Assertions.assertEquals(Set.of("E1", "E2"), new HashSet<>(copy.stream().map(Event::getEventName).toList()));
        // defensive copy check
        copy.clear();
        Assertions.assertEquals(2, db.getEventCount());
    }

    @Test
    void testGetAllEventsDefensiveCopy() {
        Event e = buildEvent(4, "Y", null, null, date(2025, 1, 1), date(2025, 1, 1), null);
        db.addEvent(e);
        List<Event> copy = db.getAllEvents();
        Assertions.assertEquals(1, copy.size());
        copy.clear();
        // clearing the returned list must not clear the database
        Assertions.assertEquals(1, db.getEventCount());
    }

    @Test
    public void testGetAllEventsEmpty() {
        Assertions.assertTrue(db.getAllEvents().isEmpty());
    }

    @Test
    public void testGetEventByIdValid() {
        Event e = buildEvent(105, "FindMe", null, null, date(2025, 1, 1), date(2025, 1, 1), null);
        db.addEvent(e);
        Assertions.assertEquals("FindMe", db.getEventById(105).getEventName());
    }

    @Test
    public void testGetEventByIdInvalid() {
        // Negative IDs are invalid and should throw
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> db.getEventById(-1));
        Assertions.assertTrue(ex.getMessage().contains(EventDatabaseErrorMessage.INVALID_EVENT_ID.format(-1)));
    }

    @Test
    void testGetNotAddedEventById() {
        Assertions.assertNull(db.getEventById(12345));
    }

    @Test
    public void testUpdateEventValidId() {
        Event e = buildEvent(106, "Old", null, null, date(2025, 3, 1), date(2025, 3, 2), null);
        db.addEvent(e);
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "New");
        updates.put("location", "Hall");
        db.updateEvent(106, updates);
        Event got = db.getEventById(106);
        Assertions.assertEquals("New", got.getEventName());
        Assertions.assertEquals("Hall", got.getEventLocation());
    }

    @Test
    public void testUpdateEventInvalidId() {
        Map<String, Object> updates = Map.of("name", "X");
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> db.updateEvent(-2, updates));
        Assertions.assertTrue(ex.getMessage().contains(EventDatabaseErrorMessage.INVALID_EVENT_ID.format(-2)));
    }

    @Test
    public void testUpdateEventWithNullData() {
        Event e = buildEvent(107, "HasData", null, null, date(2025, 4, 1), date(2025, 4, 1), null);
        db.addEvent(e);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> db.updateEvent(107, null));
        Assertions.assertTrue(ex.getMessage().contains(EventDatabaseErrorMessage.UPDATE_INFO_NULL_OR_EMPTY.getMessage()));
    }

    @Test
    void testUpdateEmptyEvent() {
        Event e = buildEvent(9, "E2", null, null, date(2025, 5, 1), date(2025, 5, 1), null);
        db.addEvent(e);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> db.updateEvent(9, new HashMap<>()));
        Assertions.assertTrue(ex.getMessage().contains(EventDatabaseErrorMessage.UPDATE_INFO_NULL_OR_EMPTY.getMessage()));
    }

    @Test
    void testUpdateMissingEvent() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> db.updateEvent(404, Map.of("name", "X")));
        Assertions.assertEquals(ex.getMessage(), EventDatabaseErrorMessage.EVENT_NOT_FOUND.format(404));
    }

    @Test
    void testUpdateEventWithInvalidFieldName() {
        Event e = buildEvent(10, "E3", null, null, date(2025, 6, 1), date(2025, 6, 1), null);
        db.addEvent(e);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> db.updateEvent(10, Map.of("invalidField", "x")));
        Assertions.assertEquals(ex.getMessage(), EventErrorMessage.INVALID_FIELD_NAME.format("invalidField"));
    }

    @Test
    void updateEvent_wrongType_throwsIllegalArgument() {
        Event e = buildEvent(11, "E4", null, null, date(2025, 7, 1), date(2025, 7, 1), null);
        db.addEvent(e);
        // START_DATE expects a Date; pass a String to trigger type error
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> db.updateEvent(11, Map.of("start_date", "not a date")));
        Assertions.assertEquals(ex.getMessage(), EventErrorMessage.START_DATE_MUST_BE_DATE.getMessage());
    }

    @Test
    void testDisplayAllEventsEmpty() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream prev = System.out;
        System.setOut(new PrintStream(out));
        try {
            db.displayAllEvents();
            String printed = out.toString();
            Assertions.assertTrue(printed.contains(EventDatabaseErrorMessage.DATABASE_EMPTY.getMessage()));
        } finally {
            System.setOut(prev);
        }
    }

    @Test
    void testDisplayAllEvents() {
        Event e = buildEvent(6, "Team Sync", "Weekly", "Zoom",
                date(2025, 2, 1), date(2025, 2, 1), null);
        db.addEvent(e);


        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream prev = System.out;
        System.setOut(new PrintStream(out));
        try {
            db.displayAllEvents();
            String printed = out.toString();
            Assertions.assertTrue(printed.contains("All Events"));
            Assertions.assertTrue(printed.contains("Team Sync"));
        } finally {
            System.setOut(prev);
        }
    }

    @Test
    void testCountAndIsEmptyReflectState() {
        Assertions.assertTrue(db.isEmpty());
        Event e = buildEvent(12, "E5", null, null, date(2025, 8, 1), date(2025, 8, 1), null);
        db.addEvent(e);
        Assertions.assertEquals(1, db.getEventCount());
        Assertions.assertFalse(db.isEmpty());
        db.clearAllEvents();
        Assertions.assertTrue(db.isEmpty());
    }

    @Test
    void testIteratorTraversesAllEventsInInsertionOrder() {
        Event e1 = buildEvent(13, "A", null, null, date(2025, 9, 1), date(2025, 9, 1), null);
        Event e2 = buildEvent(14, "B", null, null, date(2025, 9, 2), date(2025, 9, 2), null);
        db.addEvent(e1);
        db.addEvent(e2);
        List<String> names = new ArrayList<>();
        for (Event e : db) names.add(e.getEventName());
        Assertions.assertEquals(List.of("A", "B"), names);
    }

    @Test
    public void testUpdateEventOwnership() {
    }

    @Test
    public void testEventIntegrationWithUser() {
    }

    @Test
    public void testNullEventListException() {
    }
}