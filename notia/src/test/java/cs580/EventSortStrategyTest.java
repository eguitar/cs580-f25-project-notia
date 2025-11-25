package cs580;

import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventSortStrategyTest {

    private Event event1, event2, event3;
    private List<Event> events;

    @BeforeEach
    public void setup() throws Exception {
        event1 = new Event("AlphaEvent", "Desc A", "Loc A", new Date(1700000000000L), new Date(1700001000000L), "Notes A");
        event1.setEventID(1);

        event2 = new Event("CharlieEvent", "Desc C", "Loc C", new Date(1710000000000L), new Date(1710001000000L), "Notes C");
        event2.setEventID(3);

        event3 = new Event("BravoEvent", "Desc B", "Loc B", new Date(1690000000000L), new Date(1690001000000L), "Notes B");
        event3.setEventID(2);

        events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);
    }

    @Test
    public void testSortByID() {
        EventSortStrategy strategy = new EventSortByID();
        strategy.sort(events);

        assertEquals(1, events.get(0).getEventID());
        assertEquals(2, events.get(1).getEventID());
        assertEquals(3, events.get(2).getEventID());
    }

    @Test
    public void testSortByName() {
        EventSortStrategy strategy = new EventSortByName();
        strategy.sort(events);

        assertEquals("AlphaEvent", events.get(0).getEventName());
        assertEquals("BravoEvent", events.get(1).getEventName());
        assertEquals("CharlieEvent", events.get(2).getEventName());
    }

    @Test
    public void testSortByStartDate() {
        EventSortStrategy strategy = new EventSortByStartDate();
        strategy.sort(events);

        assertEquals("BravoEvent", events.get(0).getEventName());
        assertEquals("AlphaEvent", events.get(1).getEventName());
        assertEquals("CharlieEvent", events.get(2).getEventName());
    }

    @Test
    public void testSortByEndDate() {
        EventSortStrategy strategy = new EventSortByEndDate();
        strategy.sort(events);

        assertEquals("BravoEvent", events.get(0).getEventName());
        assertEquals("AlphaEvent", events.get(1).getEventName());
        assertEquals("CharlieEvent", events.get(2).getEventName());
    }

    @Test
    public void testSortByStartDateWithNulls() {
        events.clear();

        Event nullStartDateEvent = new Event("NullStart", "Desc", "Loc", null, new Date(1700000500000L), "Notes");
        nullStartDateEvent.setEventID(4);

        Event validStartDateEvent = new Event("ValidStart", "Desc", "Loc", new Date(1700000000000L), new Date(1700001000000L), "Notes");
        validStartDateEvent.setEventID(5);

        events.add(nullStartDateEvent);
        events.add(validStartDateEvent);

        EventSortStrategy strategy = new EventSortByStartDate();
        strategy.sort(events);

        assertEquals("NullStart", events.get(0).getEventName());
        assertEquals("ValidStart", events.get(1).getEventName());
    }

    @Test
    public void testSortByEndDateWithNulls() {
        events.clear();

        Event nullEndDateEvent = new Event("NullEnd", "Desc", "Loc", new Date(1700000000000L), null, "Notes");
        nullEndDateEvent.setEventID(6);

        Event validEndDateEvent = new Event("ValidEnd", "Desc", "Loc", new Date(1700000000000L), new Date(1700001000000L), "Notes");
        validEndDateEvent.setEventID(7);

        events.add(nullEndDateEvent);
        events.add(validEndDateEvent);

        EventSortStrategy strategy = new EventSortByEndDate();
        strategy.sort(events);

        assertEquals("NullEnd", events.get(0).getEventName());
        assertEquals("ValidEnd", events.get(1).getEventName());
    }
}