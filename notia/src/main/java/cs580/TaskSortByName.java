package cs580;

import java.util.List;

public class TaskSortByName implements TaskSortStrategy {
    @Override
    public void sort(List<Task> tasks) {
        tasks.sort((a, b) -> a.getTaskName().compareToIgnoreCase(b.getTaskName()));
    }
}