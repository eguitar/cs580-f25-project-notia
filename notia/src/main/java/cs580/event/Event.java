package cs580.event;

import cs580.exception_handling.Validators;

import java.util.Date;
import java.util.Map;

public class Event {
    private int eventID;
    private String name;
    private String description;
    private String location;
    private Date startDate;
    private Date endDate;
    private String notes;

    // Private constructor for EventBuilder pattern only
    private Event(EventBuilder builder) {
        this.eventID = builder.eventID;
        this.name = builder.name;
        this.description = builder.description;
        this.location = builder.location;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.notes = builder.notes;
    }

    // Getters
    public int getEventID() {
        return eventID;
    }

    // Setters with validation using Validator utility
    public void setEventID(int eventID) {
        Validators.illegalArgument().requireNonNegative(eventID, EventErrorMessage.EVENT_ID_NEGATIVE.getMessage());
        this.eventID = eventID;
    }

    public String getEventName() {
        return name;
    }

    public void setEventName(String name) {
        Validators.illegalArgument().requireNonEmpty(name, EventErrorMessage.EVENT_NAME_NULL_OR_EMPTY.getMessage());
        this.name = Validators.sanitizeString(name);
    }

    public String getEventDescription() {
        return description;
    }

    public void setEventDescription(String description) {
        this.description = Validators.sanitizeString(description);
    }

    public String getEventLocation() {
        return location;
    }

    public void setEventLocation(String location) {
        this.location = Validators.sanitizeString(location);
    }

    public Date getEventStartDate() {
        return startDate;
    }

    public void setEventStartDate(Date startDate) {
        Validators.DateValidator.requireRange(startDate, this.endDate,
                EventErrorMessage.START_DATE_AFTER_END_DATE.getMessage());
        this.startDate = startDate;
    }

    public Date getEventEndDate() {
        return endDate;
    }

    public void setEventEndDate(Date endDate) {
        Validators.DateValidator.requireRange(this.startDate, endDate,
                EventErrorMessage.END_DATE_BEFORE_START_DATE.getMessage());
        this.endDate = endDate;
    }

    public String getEventNotes() {
        return notes;
    }

    public void setEventNotes(String notes) {
        this.notes = Validators.sanitizeString(notes);
    }

    // Implementation of getEventInfo() as specified in UML
    public String getEventInfo() {
        return String.format(
                """
                        Event ID: %d
                        Name: %s
                        Description: %s
                        Location: %s
                        Start Date: %s
                        End Date: %s
                        Notes: %s""",
                eventID,
                name != null ? name : "N/A",
                description != null ? description : "N/A",
                location != null ? location : "N/A",
                startDate != null ? startDate.toString() : "N/A",
                endDate != null ? endDate.toString() : "N/A",
                notes != null ? notes : "N/A"
        );
    }

    // Implementation of editEvent() - allows updating multiple fields at once
    public void editEvent(Map<String, Object> updates) {
        Validators.illegalArgument().requireNonNull(updates, EventErrorMessage.UPDATES_MAP_NULL.getMessage());
        Validators.illegalArgument().requireCondition(!updates.isEmpty(), EventErrorMessage.UPDATES_MAP_EMPTY.getMessage());

        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            updateField(entry.getKey(), entry.getValue());
        }
    }

    // Helper method to update a single field
    private void updateField(String field, Object value) {
        String normalizedField = normalizeFieldName(field);
        validateAndSetField(normalizedField, value);
    }

    // Normalize field name for case-insensitive matching
    private String normalizeFieldName(String field) {
        Validators.illegalArgument().requireNonEmpty(field, EventErrorMessage.FIELD_NAME_NULL_OR_EMPTY.getMessage());
        return field.trim();
    }

    // Validate and set the appropriate field using Strategy pattern via enum
    private void validateAndSetField(String field, Object value) {
        EventField eventField = EventField.fromString(field);
        eventField.validateType(value);

        // Strategy pattern: Map enum to corresponding setter action
        switch (eventField) {
            case NAME:
                setEventName((String) value);
                break;
            case DESCRIPTION:
                setEventDescription((String) value);
                break;
            case LOCATION:
                setEventLocation((String) value);
                break;
            case START_DATE:
                setEventStartDate((Date) value);
                break;
            case END_DATE:
                setEventEndDate((Date) value);
                break;
            case NOTES:
                setEventNotes((String) value);
                break;
        }
    }

    // Private EventBuilder class - only accessible within Event
    private static class EventBuilder {
        private final int eventID;
        private final String name;
        private String description;
        private String location;
        private Date startDate;
        private Date endDate;
        private String notes;

        public EventBuilder(int eventID, String name) {
            Validators.illegalArgument().requireNonNegative(eventID, EventErrorMessage.EVENT_ID_NEGATIVE.getMessage());
            Validators.illegalArgument().requireNonEmpty(name, EventErrorMessage.EVENT_NAME_NULL_OR_EMPTY.getMessage());
            this.eventID = eventID;
            this.name = Validators.sanitizeString(name);
        }

        public EventBuilder description(String description) {
            this.description = Validators.sanitizeString(description);
            return this;
        }

        public EventBuilder location(String location) {
            this.location = Validators.sanitizeString(location);
            return this;
        }

        public EventBuilder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public EventBuilder endDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public EventBuilder notes(String notes) {
            this.notes = Validators.sanitizeString(notes);
            return this;
        }

        public Event build() {
            Validators.DateValidator.requireRange(startDate, endDate,
                    EventErrorMessage.END_DATE_BEFORE_START_DATE.getMessage());
            return new Event(this);
        }
    }
}