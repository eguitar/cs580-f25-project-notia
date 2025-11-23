package cs580;

import java.util.List;

public class TaskSortByID implements TaskSortStrategy {
    @Override
    public void sort(List<Task> tasks) {
        tasks.sort((a, b) -> Integer.compare(a.getTaskID(), b.getTaskID()));
    }
}