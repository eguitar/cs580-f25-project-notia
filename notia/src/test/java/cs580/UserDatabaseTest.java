package cs580;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDatabaseTest {

    @Test
    public void testSingleton() {
        UserDatabase db1 = UserDatabase.getInstance();
        UserDatabase db2 = UserDatabase.getInstance();
        assertSame(db1, db2);
    }

    @Test
    public void testAddRemoveUser() {
        UserDatabase db = UserDatabase.getInstance();
        int initialSize = db.getUserList().size();

        UserData ud = new UserData("F", "L", "un", "pw", "email");
        User user = new User(ud);

        db.addUser(user);
        assertEquals(initialSize + 1, db.getUserList().size());

        db.removeUser(user);
        assertEquals(initialSize, db.getUserList().size());
    }

    @Test
    public void testFindUser() {
        UserDatabase db = UserDatabase.getInstance();

        UserData ud = new UserData("First", "Last", "username123", "pass123", "email@example.com");
        User user = new User(ud);
        db.addUser(user);

        User found = db.findUser("username123", "pass123");
        assertNotNull(found);
        assertEquals("username123", ud.getUserDataSummary().split("\n")[2].split(": ")[1]);

        User notFound = db.findUser("username123", "wrongpass");
        assertNull(notFound);

        db.removeUser(user);
    }
}