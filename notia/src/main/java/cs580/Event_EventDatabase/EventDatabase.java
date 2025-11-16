package cs580.Event_EventDatabase;

import java.util.ArrayList;
import java.util.Iterator;

public class EventDatabase implements Iterable<Event> {
    private static EventDatabase instance = null;
    private ArrayList<Event> eventList = new ArrayList<>();

    private EventDatabase() {
    }

    public static EventDatabase getInstance() {
        if (instance == null) {
            instance = new EventDatabase();
        }
        return instance;
    }

    public void addEvent(Event event) {
        eventList.add(event);
    }

    public void removeEvent(Event event) {
        eventList.remove(event);
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public Event getEventByID(int eventID) {
        for (Event event : eventList) {
            if (event.getEventID() == eventID) {
                return event;
            }
        }
        return null;
    }

    public Event getEventByName(String name) {
        for (Event event : eventList) {
            if (event.getEventName().equals(name)) {
                return event;
            }
        }
        return null;
    }

    @Override
    public Iterator<Event> iterator() {
        return eventList.iterator();
    }
}
