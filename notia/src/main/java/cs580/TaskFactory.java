package cs580;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class TaskFactory implements ItemFactory<Task> {

    private static TaskFactory instance;
    private Scanner scanner;

    private TaskFactory(Scanner scanner) {
        this.scanner = scanner;
    }

    public static TaskFactory getInstance(Scanner scanner) {
        if (instance == null) {
            instance = new TaskFactory(scanner);
        }
        return instance;
    }

    @Override
    public Task createItem() {
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("ENTER TASK NAME ------------------------");
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        String taskName = scanner.nextLine();

        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("ENTER TASK DESCRIPTION -----------------");
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        String taskDescription = scanner.nextLine();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        Date taskDate = null;
        boolean validDate = false;

        while (!validDate) {
            System.out.println("++++++++++++++++++++++++++++++++++++++++");
            System.out.println("ENTER TASK DATE (format: yyyy-MM-dd) ---");
            System.out.println("++++++++++++++++++++++++++++++++++++++++");
            String inputDate = scanner.nextLine();
            try {
                taskDate = sdf.parse(inputDate);
                validDate = true;
            } catch (ParseException e) {
                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                System.out.println("INVALID FORMAT ----- ENTER AS YYYY-MM-DD");
                System.out.println("++++++++++++++++++++++++++++++++++++++++");
            }
        }

        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("ENTER TASK TAGS (comma separated) ------");
        System.out.println("Ex. tag1,tag2,tag3 ---------------------");
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        String tagsInput = scanner.nextLine();

        ArrayList<String> tags = new ArrayList<>();
        if (!tagsInput.trim().isEmpty()) {
            String[] tagsArray = tagsInput.split(",");
            for (String tag : tagsArray) {
                tags.add(tag.trim());
            }
        }

        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("ENTER TASK NOTES -----------------------");
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        String taskNotes = scanner.nextLine();

        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("TASK SUCCESSFULLY CREATED --------------");
        System.out.println("++++++++++++++++++++++++++++++++++++++++");

        return new Task(taskName, taskDescription, taskDate, tags, taskNotes);
    }
}