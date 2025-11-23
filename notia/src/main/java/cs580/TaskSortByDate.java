package cs580;

import java.util.Date;
import java.util.List;

public class TaskSortByDate implements TaskSortStrategy {
    @Override
    public void sort(List<Task> tasks) {
        tasks.sort((a, b) -> {
            Date d1 = a.getTaskDate();
            Date d2 = b.getTaskDate();
            if (d1 == null && d2 == null) return 0;
            if (d1 == null) return -1;
            if (d2 == null) return 1;
            return d1.compareTo(d2);
        });
    }
}