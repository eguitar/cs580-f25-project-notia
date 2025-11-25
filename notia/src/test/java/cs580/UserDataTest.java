package cs580;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserDataTest {

    @Test
    public void testUserDataFieldsAndSummary() {
        UserData userData = new UserData("Jane", "Smith", "jsmith", "pass", "jane@example.com");

        assertEquals("Jane", userData.getFirstName());
        assertEquals("Smith", userData.getLastName());
        assertEquals("jane@example.com", userData.getEmail());

        String summary = userData.getUserDataSummary();
        assertTrue(summary.contains("Jane"));
        assertTrue(summary.contains("Smith"));
        assertTrue(summary.contains("jsmith"));
        assertTrue(summary.contains("pass"));
        assertTrue(summary.contains("jane@example.com"));
    }

    @Test
    public void testUpdateMethods() {
        UserData userData = new UserData("A", "B", "user", "pw", "email@example.com");

        userData.updateUserName("newuser");
        assertEquals("newuser", userData.getUserDataSummary().split("\n")[2].split(": ")[1]);

        userData.updatePassword("newpass");
        assertTrue(userData.getUserDataSummary().contains("newpass"));

        userData.updateEmail("newemail@example.com");
        assertEquals("newemail@example.com", userData.getEmail());
    }

    @Test
    public void testValidateUserLogin() {
        UserData userData = new UserData("X", "Y", "username", "password", "email");

        assertTrue(userData.validateUserLogin("username", "password"));
        assertFalse(userData.validateUserLogin("username", "wrong"));
        assertFalse(userData.validateUserLogin("wrong", "password"));
    }
}