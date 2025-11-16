package cs580;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        UserDatabase users = new UserDatabase();
        User currentUser = null;
        boolean running = true;

        while (running) {
            if (currentUser == null) {
                System.out.println("########################################");
                System.out.println("1. CREATE USER -------------------------");
                System.out.println("2. SIGN IN TO USER ---------------------");
                System.out.println("3. EXIT APP ----------------------------");
                System.out.println("########################################");

                int action = scanner.nextInt();
                scanner.nextLine();

                switch(action) {
                    case 1:
                        break;//???????????????????????????????????????
                    case 2:
                        break;//???????????????????????????????????????
                    case 3:
                        running = false;
                    default:
                        System.out.println("INVALID OPTION      ");
                }
            }
            else {
                System.out.println("########################################");
                System.out.println("1. ADD TASK ----------------------------");
                System.out.println("2. REMOVE TASK -------------------------");
                System.out.println("3. VIEW TASKS --------------------------");
                System.out.println("4. ADD EVENT ---------------------------");
                System.out.println("5. REMOVE EVENT ------------------------");
                System.out.println("6. VIEW EVENTS -------------------------");
                System.out.println("7. SIGN OUT OF USER --------------------");
                System.out.println("8. DELETE USER ACC ---------------------");
                System.out.println("########################################");

                int action = scanner.nextInt();
                scanner.nextLine();

                switch(action) {
                    case 1:
                        break;//???????????????????????????????????????
                    case 2:
                        break;//???????????????????????????????????????
                    case 3:
                        break;//???????????????????????????????????????
                    case 4:
                        break;//???????????????????????????????????????
                    case 5:
                        break;//???????????????????????????????????????
                    case 6:
                        break;//???????????????????????????????????????
                    case 7:
                        currentUser = null;
                    case 8:
                        users.removeUser(currentUser);
                    default:
                        System.out.println("INVALID OPTION      ");
                }
            }
        }
        scanner.close();
        System.out.println("########################################");
        System.out.println("---- EXITED APP ------------------------");
        System.out.println("########################################");
    }
}