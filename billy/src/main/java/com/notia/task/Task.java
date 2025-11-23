package com.notia.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Task domain object.
 *
 * UML:
 *  - taskId: int
 *  - name: String
 *  - description: String
 *  - dueDate: Date
 *  - tags: List<String>
 *  - notes: String
 *
 *  + getTaskInfo(): String
 *  + editTask(newInfo: Map): void
 *
 * Design pattern: Builder (for convenient construction).
 */
public class Task {

    // ----- attributes (from UML) -----
    private int taskId;
    private String name;
    private String description;
    private Date dueDate;
    private List<String> tags;
    private String notes;

    // ----- constructors -----

    /** For frameworks / deserialization. */
    public Task() {
        this.tags = new ArrayList<>();
    }

    /** Private ctor used by Builder. */
    private Task(Builder builder) {
        this.taskId = builder.taskId;
        this.name = builder.name;
        this.description = builder.description;
        this.dueDate = builder.dueDate;
        this.tags = builder.tags != null ? new ArrayList<>(builder.tags) : new ArrayList<>();
        this.notes = builder.notes;
    }

    // ----- Builder pattern -----

    public static class Builder {
        // required
        private final int taskId;

        // optional
        private String name;
        private String description;
        private Date dueDate;
        private List<String> tags;
        private String notes;

        public Builder(int taskId) {
            this.taskId = taskId;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder dueDate(Date dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }

    // ----- UML methods -----

    /**
     * Human-readable info string for display.
     */
    public String getTaskInfo() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String due = (dueDate != null) ? fmt.format(dueDate) : "N/A";

        return "Task{" +
                "id=" + taskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + due +
                ", tags=" + tags +
                ", notes='" + notes + '\'' +
                '}';
    }

    /**
     * Keys -- optional:
     *  - "taskId": Integer
     *  - "name": String
     *  - "description": String
     *  - "dueDate": java.util.Date
     *  - "tags": List<String>
     *  - "notes": String
     */
    @SuppressWarnings("unchecked")
    public void editTask(Map<String, Object> newInfo) {
        if (newInfo == null) return;

        if (newInfo.containsKey("taskId")) {
            Object v = newInfo.get("taskId");
            if (v instanceof Integer i) {
                this.taskId = i;
            }
        }
        if (newInfo.containsKey("name")) {
            Object v = newInfo.get("name");
            if (v instanceof String s) {
                this.name = s;
            }
        }
        if (newInfo.containsKey("description")) {
            Object v = newInfo.get("description");
            if (v instanceof String s) {
                this.description = s;
            }
        }
        if (newInfo.containsKey("dueDate")) {
            Object v = newInfo.get("dueDate");
            if (v instanceof Date d) {
                this.dueDate = d;
            }
        }
        if (newInfo.containsKey("tags")) {
            Object v = newInfo.get("tags");
            if (v instanceof List<?> list) {
                List<String> newTags = new ArrayList<>();
                for (Object o : list) {
                    newTags.add(String.valueOf(o));
                }
                this.tags = newTags;
            }
        }
        if (newInfo.containsKey("notes")) {
            Object v = newInfo.get("notes");
            if (v instanceof String s) {
                this.notes = s;
            }
        }
    }

    // ----- getters (useful for DB / tests) -----

    public int getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getNotes() {
        return notes;
    }
}
