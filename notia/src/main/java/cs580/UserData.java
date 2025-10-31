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

    public void updateUserName() {}

    public void updatePassword() {}

    public void updateEmail() {}

    public String getUserDataSummary() { return "";}

        
}
