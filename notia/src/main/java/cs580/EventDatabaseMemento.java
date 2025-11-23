package cs580;

import java.util.ArrayList;

public class EventDatabaseMemento {
    private final ArrayList<Event> eventListSnapshot;
    private final int eventIDSnapshot;

    public EventDatabaseMemento(ArrayList<Event> eventList, int eventID) {
        this.eventListSnapshot = new ArrayList<>();
        for (Event event : eventList) {
            this.eventListSnapshot.add(new Event(event));
        }
        this.eventIDSnapshot = eventID;
    }

    public ArrayList<Event> getEventListSnapshot() { return eventListSnapshot; }
    public int getEventIDSnapshot() { return eventIDSnapshot; }
}
