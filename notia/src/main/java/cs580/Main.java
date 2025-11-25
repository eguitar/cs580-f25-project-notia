package cs580;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        UserDatabase users = UserDatabase.getInstance();
        User currentUser = null;
        boolean running = true;
        int action;
        String username, password;
        int taskID, eventID;
        SimpleDateFormat sdf;
        Date taskDate, eventStartDate, eventEndDate;
        boolean validDate;
        Task newTask;
        Event newEvent;

        while (running) {
            if (currentUser == null) {
                System.out.println("################################################################################");
                System.out.println("1. CREATE USER -------------------------");
                System.out.println("2. SIGN IN TO USER ---------------------");
                System.out.println("3. EXIT APP ----------------------------");
                System.out.println("################################################################################");
                try {
                    action = scanner.nextInt();
                    scanner.nextLine();

                    switch(action) {
                        case 1:
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("PLEASE ENTER FIRST NAME ----------------");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            String firstName = scanner.nextLine();

                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("PLEASE ENTER LAST NAME -----------------");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            String lastName = scanner.nextLine();

                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("PLEASE ENTER EMAIL ---------------------");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            String email = scanner.nextLine();

                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("PLEASE ENTER USERNAME ------------------");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            username = scanner.nextLine();

                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("PLEASE ENTER PASSWORD ------------------");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            password = scanner.nextLine();

                            UserData newUserInfo = new UserData(firstName,lastName,username,password,email);
                            currentUser = new User(newUserInfo);
                            users.addUser(currentUser);

                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("NEW USER ADDED SUCCESSFULLY ------------");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            break;

                        case 2:
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("PLEASE ENTER USERNAME ------------------");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            username = scanner.nextLine();

                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("PLEASE ENTER PASSWORD ------------------");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            password = scanner.nextLine();

                            User searchedUser = users.findUser(username, password);
                            if (searchedUser != null) {
                                currentUser = searchedUser;
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                System.out.println("SUCCESSFULLY LOGGED IN -----------------");
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            } else {
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                System.out.println(" INCORRECT LOGIN OR USER DOES NOT EXIST ");
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            }
                            break;

                        case 3:
                            running = false;
                            break;

                        default:
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("INVALID INPUT --------- PLEASE TRY AGAIN");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            break;
                    }
                }
                catch (Exception e) {
                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                    System.out.println("INVALID INPUT --------- PLEASE TRY AGAIN");
                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                    scanner.nextLine(); 
                    continue;
                }
            }
            else {
                System.out.println("################################################################################");
                System.out.println("1.  ADD TASK ---------------------------");
                System.out.println("2.  REMOVE TASK ------------------------");
                System.out.println("3.  EDIT TASK --------------------------");
                System.out.println("4.  VIEW TASKS -------------------------");
                System.out.println("5.  ADD EVENT --------------------------");
                System.out.println("6.  REMOVE EVENT -----------------------");
                System.out.println("7.  EDIT EVENT --------------------------");
                System.out.println("8.  VIEW EVENTS ------------------------");
                System.out.println("9.  REDO LAST ADD / REMOVE / EDIT ------");
                System.out.println("10. VIEW USER INFO ---------------------");
                System.out.println("11. SIGN OUT OF USER -------------------");
                System.out.println("12. DELETE USER ACC --------------------");
                System.out.println("################################################################################");

                action = scanner.nextInt();
                scanner.nextLine();

                try {
                    switch(action) {
                        case 1:
                            newTask = TaskFactory.getInstance(scanner).createItem();
                            currentUser.addTask(newTask);
                            break;
                        case 2:
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("PLEASE ENTER THE ID OF THE TASK --------");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            taskID = scanner.nextInt();
                            scanner.nextLine();

                            if (currentUser.removeTask(taskID)) {
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                System.out.println("TASK SUCCESSFULLY REMOVED --------------");
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            } else {
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                System.out.println("TASK DOES NOT EXIST OR REMOVAL ERROR ---");
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            }
                            break;

                        case 3:
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("ENTER TASKID OF TASK TO BE EDITED ------");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            int editTaskID = scanner.nextInt();
                            scanner.nextLine();

                            Task taskToEdit = currentUser.getTask(editTaskID);
                            if (taskToEdit == null) {
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                System.out.println("TASK NOT FOUND -------------------------");
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                break;
                            }

                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("PLEASE SELECT AN ATTRIBUTE TO EDIT -----");
                            System.out.println("1.  TASK NAME --------------------------");
                            System.out.println("2.  TASK DESCRIPTION -------------------");
                            System.out.println("3.  TASK DATE --------------------------");
                            System.out.println("4.  TASK TAGS --------------------------");
                            System.out.println("5.  TASK NOTES -------------------------");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");

                            action = scanner.nextInt();
                            scanner.nextLine();

                            switch(action) {
                                case 1:
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("ENTER NEW TASK NAME --------------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    String newName = scanner.nextLine();
                                    taskToEdit.setTaskName(newName);
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("TASK NAME UPDATED ----------------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    break;
                                case 2: 
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("ENTER NEW TASK DESCRIPTION -------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    String newDesc = scanner.nextLine();
                                    taskToEdit.setTaskDescription(newDesc);
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("TASK DESCRIPTION UPDATED ----------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    break;
                                case 3:
                                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    sdf.setLenient(false);
                                    Date newDate = null;
                                    boolean validDate2 = false;

                                    while (!validDate2) {
                                        System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                        System.out.println("ENTER NEW TASK DATE (FORMAT: YYYY-MM-DD)");
                                        System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                        String dateStr = scanner.nextLine();
                                        try {
                                            newDate = sdf.parse(dateStr);
                                            validDate2 = true;
                                        } catch (ParseException e) {
                                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                            System.out.println("INVALID FORMAT ----- ENTER AS YYYY-MM-DD");
                                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                        }
                                    }
                                    taskToEdit.setTaskDate(newDate);
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("TASK DATE UPDATED SUCCESSFULLY ---------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    break;
                                case 4:
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("ENTER NEW TAGS (COMMA SEPARATED) -------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    String newTagInput = scanner.nextLine();
                                    ArrayList<String> newTags = new ArrayList<>();

                                    if (!newTagInput.trim().isEmpty()) {
                                        for (String t : newTagInput.split(",")) {
                                            newTags.add(t.trim());
                                        }
                                    }
                                    taskToEdit.setTaskTags(newTags);
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("TASK TAGS UPDATED ----------------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    break;
                                case 5:
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("ENTER NEW NOTES ------------------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    String newNotes = scanner.nextLine();
                                    taskToEdit.setTaskNotes(newNotes);
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("TASK NOTES UPDATED ---------------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    break;
                            }

                            if (currentUser.editTask(editTaskID, taskToEdit)) {
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                System.out.println("TASK UPDATED SUCCESSFULLY --------------");
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            } else {
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                System.out.println("ERROR IN TASK UPDATE -------------------");
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            }
                            break;
                        case 4:
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("SORT TASKS BY: -------------------------");
                            System.out.println("1. ID ----------------------------------");
                            System.out.println("2. NAME --------------------------------");
                            System.out.println("3. DATE --------------------------------");
                            System.out.println("4. DEFAULT (LAST VIEWED) ---------------");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");

                            int sortChoiceT = scanner.nextInt();
                            scanner.nextLine();

                            switch (sortChoiceT) {
                                case 1: currentUser.displayTaskDatabaseSummary(new TaskSortByID()); break;
                                case 2: currentUser.displayTaskDatabaseSummary(new TaskSortByName()); break;
                                case 3: currentUser.displayTaskDatabaseSummary(new TaskSortByDate()); break;
                                default: currentUser.displayTaskDatabaseSummary(); break;
                            }
                            break;
                        case 5:
                            newEvent = EventFactory.getInstance(scanner).createItem();
                            currentUser.addEvent(newEvent);
                            break;
                        case 6:
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("PLEASE ENTER THE ID OF THE EVENT -------");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            eventID = scanner.nextInt();
                            scanner.nextLine();

                            if (currentUser.removeEvent(eventID)) {
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                System.out.println("EVENT SUCCESSFULLY REMOVED -------------");
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            } else {
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                System.out.println("EVENT DOES NOT EXIST OR REMOVAL ERROR --");
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            }
                            break;
                        case 7:
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("ENTER EVENT ID OF EVENT TO BE EDITED ---");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            int editEventID = scanner.nextInt();
                            scanner.nextLine();

                            Event eventToEdit = currentUser.getEvent(editEventID);
                            if (eventToEdit == null) {
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                System.out.println("EVENT NOT FOUND ------------------------");
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                break;
                            }

                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("PLEASE SELECT AN ATTRIBUTE TO EDIT -----");
                            System.out.println("1.  EVENT NAME -------------------------");
                            System.out.println("2.  EVENT DESCRIPTION ------------------");
                            System.out.println("3.  EVENT LOCATION ---------------------");
                            System.out.println("4.  EVENT START DATE -------------------");
                            System.out.println("5.  EVENT END DATE ---------------------");
                            System.out.println("6.  EVENT NOTES ------------------------");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");

                            int attributeChoice = scanner.nextInt();
                            scanner.nextLine();

                            sdf = new SimpleDateFormat("yyyy-MM-dd");
                            sdf.setLenient(false);
                            Date newDate = null;
                            validDate = false;

                            switch (attributeChoice) {
                                case 1:
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("ENTER NEW EVENT NAME -------------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    String newName = scanner.nextLine();
                                    eventToEdit.setEventName(newName);
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("EVENT NAME UPDATED ---------------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    break;

                                case 2:
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("ENTER NEW EVENT DESCRIPTION ------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    String newDescription = scanner.nextLine();
                                    eventToEdit.setEventDescription(newDescription);
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("EVENT DESCRIPTION UPDATED --------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    break;

                                case 3:
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("ENTER NEW EVENT LOCATION ---------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    String newLocation = scanner.nextLine();
                                    eventToEdit.setEventLocation(newLocation);
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("EVENT LOCATION UPDATED ------------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    break;

                                case 4:
                                    while (!validDate) {
                                        System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                        System.out.println("ENTER NEW EVENT START DATE -------------");
                                        System.out.println("(FORMAT: YYYY-MM-DD) -------------------");
                                        System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                        String dateStr = scanner.nextLine();
                                        try {
                                            newDate = sdf.parse(dateStr);
                                            validDate = true;
                                        } catch (ParseException e) {
                                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                            System.out.println("INVALID FORMAT ----- ENTER AS YYYY-MM-DD");
                                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                        }
                                    }
                                    eventToEdit.setEventStartDate(newDate);
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("EVENT START DATE UPDATED ---------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    break;

                                case 5:
                                    validDate = false;
                                    while (!validDate) {
                                        System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                        System.out.println("ENTER NEW EVENT END DATE ---------------");
                                        System.out.println("(FORMAT: YYYY-MM-DD) -------------------");
                                        System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                        String dateStr = scanner.nextLine();
                                        try {
                                            newDate = sdf.parse(dateStr);
                                            if (newDate.before(eventToEdit.getEventStartDate())) {
                                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                                System.out.println("END DATE CANNOT BE BEFORE START DATE ---");
                                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                            } else {
                                                validDate = true;
                                            }
                                        } catch (ParseException e) {
                                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                            System.out.println("INVALID FORMAT ----- ENTER AS YYYY-MM-DD");
                                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                        }
                                    }
                                    eventToEdit.setEventEndDate(newDate);
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("EVENT END DATE UPDATED -----------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    break;

                                case 6:
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("ENTER NEW EVENT NOTES ------------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    String newNotes = scanner.nextLine();
                                    eventToEdit.setEventNotes(newNotes);
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("EVENT NOTES UPDATED --------------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    break;

                                default:
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    System.out.println("INVALID SELECTION ----------------------");
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                    break;
                            }

                            if (currentUser.editEvent(editEventID, eventToEdit)) {
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                System.out.println("EVENT UPDATED SUCCESSFULLY -------------");
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            } else {
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                System.out.println("ERROR IN EVENT UPDATE ------------------");
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            }
                            break;
                        case 8:
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("SORT EVENTS BY: ------------------------");
                            System.out.println("1. ID ----------------------------------");
                            System.out.println("2. NAME --------------------------------");
                            System.out.println("3. START DATE --------------------------");
                            System.out.println("4. END DATE ----------------------------");
                            System.out.println("5. DEFAULT (LAST VIEWED) ---------------");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++");

                            int sortChoiceE = scanner.nextInt();
                            scanner.nextLine();

                            switch (sortChoiceE) {
                                case 1: currentUser.displayEventDatabaseSummary(new EventSortByID()); break;
                                case 2: currentUser.displayEventDatabaseSummary(new EventSortByName()); break;
                                case 3: currentUser.displayEventDatabaseSummary(new EventSortByStartDate()); break;
                                case 4: currentUser.displayEventDatabaseSummary(new EventSortByEndDate()); break;
                                default: currentUser.displayEventDatabaseSummary(); break;
                            }
                            break;

                        case 9:
                            if (currentUser.undo()) {
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                System.out.println("SUCCESSFULLY REVERT LAST CHANGE --------");
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            }
                            else {
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                                System.out.println("UNABLE TO REVERT LAST CHANGE -----------");
                                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            }
                            break;
                        case 10:
                            System.out.println(currentUser.getUserInfo());
                            break;
                        case 11:
                            currentUser = null;
                            break;
                        case 12:
                            users.removeUser(currentUser);
                            currentUser = null;
                            break;
                    }
                }
                catch (Exception e) {
                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                    System.out.println("INVALID INPUT --------- PLEASE TRY AGAIN");
                    System.out.println("++++++++++++++++++++++++++++++++++++++++");
                    scanner.nextLine(); 
                    continue;
                }
            }
        }

        scanner.close();
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("---- EXITED APP ------------------------");
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
    }
}
