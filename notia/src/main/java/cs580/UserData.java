package cs580;

public class UserData {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    
    public UserData(String firstName, String lastName, String username,
                                        String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }

    public void updateUserName(String username) { this.username = username; }
    public void updatePassword(String password) { this.password = password; }
    public void updateEmail(String email) { this.email = email; }

    public String getUserDataSummary() { 
        return "Name: " + firstName + " " + lastName +
               "\nEmail: " + email +
               "\nUsername: " + username +
               "\nPassword: " + password;
    }

    public boolean validateUserLogin(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            return true;
        }
        else {
            return false;
        }
    }
}
