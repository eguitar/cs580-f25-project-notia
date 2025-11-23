package cs580;

import java.util.List;

public class EventSortByID implements EventSortStrategy {
    @Override
    public void sort(List<Event> events) {
        events.sort((a, b) -> Integer.compare(a.getEventID(), b.getEventID()));
    }
}