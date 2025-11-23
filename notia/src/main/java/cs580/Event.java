package cs580;

import java.util.Date;

public class Event {
    private int eventID;
    private String name;
    private String description;
    private String location;
    private Date startDate;
    private Date endDate;
    private String notes;

    public Event(String name, String description,
                 String location, Date startDate, Date endDate, String notes) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
    }

    public Event(Event event) {
        this.eventID = event.eventID;
        this.name = event.name;
        this.description = event.description;
        this.location = event.location;
        this.startDate = (event.startDate != null) ? new Date(event.startDate.getTime()) : null;
        this.endDate = (event.endDate != null) ? new Date(event.endDate.getTime()) : null;
        this.notes = event.notes;
    }


    public int getEventID() { return eventID; }
    public void setEventID(int eventID) { this.eventID = eventID; }

    public String getEventName() { return name; }
    public void setEventName(String name) { this.name = name; }

    public String getEventDescription() { return description; }
    public void setEventDescription(String description) { this.description = description; }

    public String getEventLocation() { return location; }
    public void setEventLocation(String location) { this.location = location; }

    public Date getEventStartDate() { return startDate; }
    public void setEventStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEventEndDate() { return endDate; }
    public void setEventEndDate(Date endDate) { this.endDate = endDate; }

    public String getEventNotes() { return notes; }
    public void setEventNotes(String notes) { this.notes = notes; }

    public String getEventSummary() {
        return "Event ID: " + eventID +
               "\nName: " + name +
               "\nDescription: " + description +
               "\nLocation: " + location +
               "\nStart Date: " + startDate +
               "\nEnd Date: " + endDate +
               "\nNotes: " + notes;
    }
}