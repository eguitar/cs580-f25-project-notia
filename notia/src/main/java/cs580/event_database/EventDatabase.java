package cs580.event_database;

import cs580.event.Event;
import cs580.exception_handling.ValidationExecutor;
import cs580.exception_handling.Validators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EventDatabase implements Iterable<Event> {
    // Singleton instance with thread-safe initialization
    private static volatile EventDatabase instance = null;

    // List to store events
    private final List<Event> events = new ArrayList<>();

    // Private constructor to prevent instantiation
    private EventDatabase() {
    }

    /**
     * Thread-safe Singleton getInstance method using double-checked locking
     *
     * @return the single instance of EventDatabase
     */
    public static EventDatabase getInstance() {
        if (instance == null) {
            synchronized (EventDatabase.class) {
                if (instance == null) {
                    instance = new EventDatabase();
                }
            }
        }
        return instance;
    }

    /**
     * Adds an event to the database
     *
     * @param e the event to add
     * @throws IllegalArgumentException if event is null or already exists
     */
    public void addEvent(Event e) {
        // Execute validation strategies
        ValidationExecutor.execute(Validators.illegalArgument().requireNonNull(e,
                EventDatabaseErrorMessage.EVENT_NULL.getMessage()));
        ValidationExecutor.execute(Validators.illegalArgument().requireNull(this.getEventById(e.getEventID()),
                EventDatabaseErrorMessage.EVENT_ALREADY_EXISTS.format(e.getEventID())));

        events.add(e);
    }

    /**
     * Deletes an event from the database by event ID
     *
     * @param eventId the ID of the event to delete
     * @return true if event was deleted, false if not found
     */
    public boolean deleteEvent(int eventId) {
        // Validate event ID
        ValidationExecutor.execute(Validators.illegalArgument().requireNonNegative(eventId,
                EventDatabaseErrorMessage.INVALID_EVENT_ID.format(eventId)));

        Event event = getEventById(eventId);
        if (event != null) {
            events.remove(event);
            return true;
        }
        return false;
    }

    /**
     * Gets all events in the database
     *
     * @return a list of all events (defensive copy to prevent external modification)
     */
    public List<Event> getAllEvents() {
        return new ArrayList<>(events);
    }

    /**
     * Gets an event by its ID
     *
     * @param eventId the ID of the event to retrieve
     * @return the event with the specified ID, or null if not found
     */
    public Event getEventById(int eventId) {
        // Validate event ID
        ValidationExecutor.execute(Validators.illegalArgument().requireNonNegative(eventId,
                EventDatabaseErrorMessage.INVALID_EVENT_ID.format(eventId)));

        for (Event event : events) {
            if (event.getEventID() == eventId) {
                return event;
            }
        }
        return null;
    }

    /**
     * Displays all events in the database
     * Prints event information to the console
     */
    public void displayAllEvents() {
        if (events.isEmpty()) {
            System.out.println(EventDatabaseErrorMessage.DATABASE_EMPTY.getMessage());
            return;
        }

        System.out.println("=== All Events ===");
        for (Event event : events) {
            System.out.println(event.getEventInfo());
            System.out.println("-------------------");
        }
    }

    /**
     * Updates an event with new information
     *
     * @param eventId the ID of the event to update
     * @param newInfo a map containing the fields to update and their new values
     * @throws IllegalArgumentException if event not found or newInfo is null/empty
     */
    public void updateEvent(int eventId, Map<String, Object> newInfo) {
        // Execute validation strategies
        ValidationExecutor.executeAll(
                Validators.illegalArgument().requireNonNegative(eventId,
                        EventDatabaseErrorMessage.INVALID_EVENT_ID.format(eventId)),
                Validators.illegalArgument().requireCondition(newInfo != null && !newInfo.isEmpty(),
                        EventDatabaseErrorMessage.UPDATE_INFO_NULL_OR_EMPTY.getMessage()),
                Validators.illegalArgument().requireNonNull(this.getEventById(eventId),
                        EventDatabaseErrorMessage.EVENT_NOT_FOUND.format(eventId))
        );

        Event event = getEventById(eventId);
        event.editEvent(newInfo);
    }

    /**
     * Gets the total number of events in the database
     *
     * @return the number of events
     */
    public int getEventCount() {
        return events.size();
    }

    /**
     * Checks if the database is empty
     *
     * @return true if no events exist, false otherwise
     */
    public boolean isEmpty() {
        return events.isEmpty();
    }

    /**
     * Clears all events from the database
     */
    public void clearAllEvents() {
        events.clear();
    }

    /**
     * Iterator implementation for Iterable interface
     * Allows EventDatabase to be used in enhanced for loops
     *
     * @return an iterator over the events
     */
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
