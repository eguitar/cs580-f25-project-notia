package cs580.Event_EventDatabase;

// Enum for all Event-related error messages
public enum EventErrorMessage {
    // ID validation messages
    EVENT_ID_NEGATIVE("Event ID cannot be negative"),

    // Name validation messages
    EVENT_NAME_NULL_OR_EMPTY("Event name cannot be null or empty"),

    // Date validation messages
    END_DATE_BEFORE_START_DATE("End date cannot be before start date"),
    START_DATE_AFTER_END_DATE("Start date cannot be after end date"),

    // Field validation messages
    INVALID_FIELD_NAME("Invalid field: %s"),
    FIELD_NAME_NULL_OR_EMPTY("Field name cannot be null or empty"),

    // Type validation messages
    NAME_MUST_BE_STRING("Name must be a String"),
    DESCRIPTION_MUST_BE_STRING("Description must be a String"),
    LOCATION_MUST_BE_STRING("Location must be a String"),
    START_DATE_MUST_BE_DATE("Start date must be a Date"),
    END_DATE_MUST_BE_DATE("End date must be a Date"),
    NOTES_MUST_BE_STRING("Notes must be a String"),

    // Update validation messages
    UPDATES_MAP_NULL("Updates map cannot be null"),
    UPDATES_MAP_EMPTY("Updates map cannot be empty");

    private final String message;

    EventErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Format message with parameters
     *
     * @param args arguments to format the message
     * @return formatted message
     */
    public String format(Object... args) {
        return String.format(message, args);
    }
}
