package cs580;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class EventFactory implements ItemFactory<Event> {

    private static EventFactory instance;  
    private Scanner scanner;

    private EventFactory(Scanner scanner) {
        this.scanner = scanner;
    }

    public static EventFactory getInstance(Scanner scanner) {
        if (instance == null) {
            instance = new EventFactory(scanner);
        }
        return instance;
    }

    @Override
    public Event createItem() {
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("ENTER EVENT NAME -----------------------");
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        String eventName = scanner.nextLine();

        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("ENTER EVENT DESCRIPTION ----------------");
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        String eventDescription = scanner.nextLine();

        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("ENTER EVENT LOCATION -------------------");
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        String eventLocation = scanner.nextLine();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        Date eventStartDate = null;
        Date eventEndDate = null;

        boolean validStartDate = false;
        boolean validEndDate = false;

        while (!validStartDate) {
            System.out.println("++++++++++++++++++++++++++++++++++++++++");
            System.out.println("ENTER EVENT START DATE (format: yyyy-MM-dd)");
            System.out.println("++++++++++++++++++++++++++++++++++++++++");
            String inputStartDate = scanner.nextLine();

            try {
                eventStartDate = sdf.parse(inputStartDate);
                validStartDate = true;
            } catch (ParseException e) {
                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                System.out.println("INVALID FORMAT ----- ENTER AS YYYY-MM-DD");
                System.out.println("++++++++++++++++++++++++++++++++++++++++");
            }
        }

        while (!validEndDate) {
            System.out.println("++++++++++++++++++++++++++++++++++++++++");
            System.out.println("ENTER EVENT END DATE (format: yyyy-MM-dd)");
            System.out.println("++++++++++++++++++++++++++++++++++++++++");
            String inputEndDate = scanner.nextLine();

            try {
                eventEndDate = sdf.parse(inputEndDate);
                if (eventEndDate.before(eventStartDate)) {
                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                    System.out.println("INVALID DATE -- MUST BE AFTER START DATE");
                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                } else {
                    validEndDate = true;
                }
            } catch (ParseException e) {
                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                System.out.println("INVALID FORMAT ----- ENTER AS YYYY-MM-DD");
                System.out.println("++++++++++++++++++++++++++++++++++++++++");
            }
        }

        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("ENTER EVENT NOTES ----------------------");
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        String eventNotes = scanner.nextLine();

        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("EVENT SUCCESSFULLY CREATED -------------");
        System.out.println("++++++++++++++++++++++++++++++++++++++++");

        return new Event(eventName, eventDescription, eventLocation, eventStartDate, eventEndDate, eventNotes);
    }
}