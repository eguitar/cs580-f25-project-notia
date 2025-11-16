package cs580.event;

import java.util.Date;

// Enum for valid event fields
public enum EventField {
    NAME(String.class, EventErrorMessage.NAME_MUST_BE_STRING),
    DESCRIPTION(String.class, EventErrorMessage.DESCRIPTION_MUST_BE_STRING),
    LOCATION(String.class, EventErrorMessage.LOCATION_MUST_BE_STRING),
    START_DATE(Date.class, EventErrorMessage.START_DATE_MUST_BE_DATE),
    END_DATE(Date.class, EventErrorMessage.END_DATE_MUST_BE_DATE),
    NOTES(String.class, EventErrorMessage.NOTES_MUST_BE_STRING);

    private final Class<?> expectedType;
    private final EventErrorMessage typeErrorMessage;

    EventField(Class<?> expectedType, EventErrorMessage typeErrorMessage) {
        this.expectedType = expectedType;
        this.typeErrorMessage = typeErrorMessage;
    }

    public static EventField fromString(String field) {
        String normalized = field.toUpperCase().replace("-", "_").replace(" ", "_");
        try {
            return EventField.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    EventErrorMessage.INVALID_FIELD_NAME.format(field)
            );
        }
    }

    public void validateType(Object value) {
        if (value != null && !expectedType.isInstance(value)) {
            throw new IllegalArgumentException(typeErrorMessage.getMessage());
        }
    }
}