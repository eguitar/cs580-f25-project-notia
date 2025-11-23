package com.notia.task;

import java.util.*;

public class DemoDriver {

    public static void main(String[] args) {

        System.out.println("===== Task & TaskDatabase Demo =====");

        // Get Singleton DB
        TaskDatabase db = TaskDatabase.getInstance();

        // ----- Create Tasks (Builder Pattern) -----
        Task t1 = new Task.Builder(1)
                .name("Finish Homework")
                .description("Complete CS5800 assignment")
                .dueDate(new GregorianCalendar(2025, Calendar.FEBRUARY, 15).getTime())
                .tags(List.of("school", "urgent"))
                .notes("Check UML diagrams")
                .build();

        Task t2 = new Task.Builder(2)
                .name("Grocery Shopping")
                .description("Buy milk, eggs, bread")
                .dueDate(new GregorianCalendar(2025, Calendar.FEBRUARY, 20).getTime())
                .tags(List.of("home"))
                .notes("Use discount coupon")
                .build();

        Task t3 = new Task.Builder(3)
                .name("Workout")
                .description("Gym session: legs + cardio")
                .dueDate(new GregorianCalendar(2025, Calendar.FEBRUARY, 10).getTime())
                .tags(List.of("health"))
                .notes("Track progress")
                .build();


        // ----- Add Tasks to Database -----
        db.addTask(t1);
        db.addTask(t2);
        db.addTask(t3);

        System.out.println("\n--- After Adding Tasks ---");
        db.displayAllTasks();


        // ----- Get Task By ID -----
        System.out.println("\n--- Get Task with ID = 2 ---");
        Task found = db.getTaskById(2);
        if (found != null) {
            System.out.println("Found: " + found.getTaskInfo());
        } else {
            System.out.println("Task not found.");
        }


        // ----- Update a Task -----
        System.out.println("\n--- Updating Task 1 ---");

        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("description", "Complete CS5800 assignment + update test cases");
        updateMap.put("notes", "Ask professor about bonus question");
        updateMap.put("tags", List.of("school", "high-priority"));

        db.updateTask(1, updateMap);

        System.out.println("Updated Task 1:");
        System.out.println(db.getTaskById(1).getTaskInfo());


        // ----- Delete a Task -----
        System.out.println("\n--- Deleting Task 3 ---");
        db.deleteTask(3);

        System.out.println("Remaining tasks:");
        db.displayAllTasks();


        // ----- Final State -----
        System.out.println("\n===== Demo Complete =====");
    }
}
