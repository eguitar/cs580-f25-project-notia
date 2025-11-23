package cs580;

import java.util.ArrayList;
import java.util.Iterator;

public class EventDatabase implements Iterable<Event> {
    private ArrayList<Event> eventList = new ArrayList<>();
    private int eventID = 0;

    public void addEvent(Event event) {
        event.setEventID(eventID);
        eventList.add(event);
        eventID++;
    }
    public boolean removeEvent(int eventID) {
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getEventID() == eventID) {
                eventList.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Event> getEventList() { return eventList; }

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

    public boolean updateEventByID(int eventID, Event newEvent) {
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getEventID() == eventID) {
                newEvent.setEventID(eventID);
                eventList.set(i, newEvent);
                return true;
            }
        }
        return false;
    }

    public EventDatabaseMemento createMemento() {
        return new EventDatabaseMemento(new ArrayList<>(eventList), eventID);
    }

    public void restoreFromMemento(EventDatabaseMemento memento) {
        this.eventList = memento.getEventListSnapshot();
        this.eventID = memento.getEventIDSnapshot();
    }

    public void sortEvents(EventSortStrategy strategy) {
        strategy.sort(eventList);
    }

    public ArrayList<Event> getAllEvents() { return eventList; }

    @Override
    public Iterator<Event> iterator() {
        return eventList.iterator();
    }
}
