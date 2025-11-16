package cs580.event_database;

import cs580.event.Event;
import cs580.exception_handling.ValidationStrategy;

import java.util.Map;

// Concrete validation strategies
public class EventDatabaseValidations {
    /**
     * Validates that an event is not null
     */
    public static ValidationStrategy notNull(Event event) {
        return () -> {
            if (event == null) {
                throw new IllegalArgumentException(EventDatabaseErrorMessage.EVENT_NULL.getMessage());
            }
        };
    }

    /**
     * Validates that an event doesn't already exist
     */
    public static ValidationStrategy notDuplicate(Event event, EventDatabase database) {
        return () -> {
            if (database.getEventById(event.getEventID()) != null) {
                throw new IllegalArgumentException(
                        EventDatabaseErrorMessage.EVENT_ALREADY_EXISTS.format(event.getEventID())
                );
            }
        };
    }

    /**
     * Validates that an event exists in the database
     */
    public static ValidationStrategy exists(int eventId, EventDatabase database) {
        return () -> {
            if (database.getEventById(eventId) == null) {
                throw new IllegalArgumentException(
                        EventDatabaseErrorMessage.EVENT_NOT_FOUND.format(eventId)
                );
            }
        };
    }

    /**
     * Validates that update information is not null or empty
     */
    public static ValidationStrategy updateInfoValid(Map<String, Object> newInfo) {
        return () -> {
            if (newInfo == null || newInfo.isEmpty()) {
                throw new IllegalArgumentException(
                        EventDatabaseErrorMessage.UPDATE_INFO_NULL_OR_EMPTY.getMessage()
                );
            }
        };
    }

    /**
     * Validates that event ID is positive
     */
    public static ValidationStrategy validEventId(int eventId) {
        return () -> {
            if (eventId < 0) {
                throw new IllegalArgumentException(
                        EventDatabaseErrorMessage.INVALID_EVENT_ID.format(eventId)
                );
            }
        };
    }
}