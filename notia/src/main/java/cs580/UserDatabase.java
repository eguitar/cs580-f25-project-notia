package cs580;

import java.util.*;

public class UserDatabase {
    private static UserDatabase instance = null;
    private ArrayList<User> userList = new ArrayList<>();

    private UserDatabase() {}

    public static UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    public void addUser(User user) { userList.add(user); }
    public void removeUser(User user) { userList.remove(user); }

    public ArrayList<User> getUserList() { return userList; }
    
    public User findUser(String username, String password) {
        for (User user : userList) {
            if (user.loginUser(username, password)) { return user; }
        }
        return null;
    }
}
