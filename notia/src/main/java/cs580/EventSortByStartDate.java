package cs580;

import java.util.Date;
import java.util.List;

public class EventSortByStartDate implements EventSortStrategy {
    @Override
    public void sort(List<Event> events) {
        events.sort((a, b) -> {
            Date d1 = a.getEventStartDate();
            Date d2 = b.getEventStartDate();
            if (d1 == null && d2 == null) return 0;
            if (d1 == null) return -1;
            if (d2 == null) return 1;
            return d1.compareTo(d2);
        });
    }
}