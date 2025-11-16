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

    public Event(int eventID, String name, String description,
                 String location, Date startDate, Date endDate, String notes) {
        this.eventID = eventID;
        this.name = name;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
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