package cs580;

import java.util.*;

public class UserDatabase {
    private ArrayList<User> userList = new ArrayList<>();

    public void addUser() {}
    public void removeUser() {}
    
    public User findUser(String username, String password) {
        for (User user : userList) {
            if (user.loginUser(username, password)) { return user; }
        }
        return null;
    }
}
