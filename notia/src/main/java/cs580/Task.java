package cs580;

import java.util.Date;
import java.util.ArrayList;

public class Task {
    private int taskID;
    private String name;
    private String description;
    private Date date;
    private ArrayList<String> tags;
    private String notes;

    public Task(int taskID, String name, String description, 
                Date date, ArrayList<String> tags, String notes) {
        this.taskID = taskID;
        this.name = name;
        this.description = description;
        this.date = date;
        this.tags = tags;
        this.notes = notes;
    }

    public int getTaskID() { return taskID; }
    public void setTaskID(int taskID) { this.taskID = taskID; }

    public String getTaskName() { return name; }
    public void setTaskName(String name) { this.name = name; }

    public String getTaskDescription() { return description; }
    public void setTaskDescription(String description) { this.description = description; }

    public Date getTaskDate() { return date; }
    public void setTaskDate(Date date) { this.date = date; }

    public ArrayList<String> getTaskTags() { return tags; }
    public void setTaskTags(ArrayList<String> tags) { this.tags = tags; }

    public String getTaskNotes() { return notes; }
    public void setTaskNotes(String notes) { this.notes = notes; }

    public String getTaskSummary() {
        return "Task ID: " + taskID +
               "\nName: " + name +
               "\nDescription: " + description +
               "\nDate: " + date +
               "\nTags: " + tags +
               "\nNotes: " + notes;
    }
}
