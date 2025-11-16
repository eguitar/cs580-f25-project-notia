package cs580.even_database;

// Enum for all EventDatabase-related error messages
public enum EventDatabaseErrorMessage {
    // Event validation messages
    EVENT_NULL("Event cannot be null"),
    EVENT_ALREADY_EXISTS("Event with ID %d already exists"),
    EVENT_NOT_FOUND("Event with ID %d not found"),

    // Update validation messages
    UPDATE_INFO_NULL_OR_EMPTY("Update information cannot be null or empty"),

    // Database state messages
    DATABASE_EMPTY("No events in the database"),

    // General validation messages
    INVALID_EVENT_ID("Invalid event ID: %d");

    private final String message;

    EventDatabaseErrorMessage(String message) {
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