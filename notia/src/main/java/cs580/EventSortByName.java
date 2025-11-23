package cs580;

import java.util.List;

public class EventSortByName implements EventSortStrategy {
    @Override
    public void sort(List<Event> events) {
        events.sort((a, b) -> a.getEventName().compareToIgnoreCase(b.getEventName()));
    }
}