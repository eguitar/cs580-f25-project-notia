package cs580;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

public class BirthdayTests {

    // Helper to generate consistent date (any year is fine)
    private Date makeDate(int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2000);          // arbitrary stable year
        c.set(Calendar.MONTH, month - 1);    // Java months = 0–11
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    @Test
    void testCreateBirthday() {
        Date date = makeDate(5, 20);
        Birthday b = new Birthday("Alice", date);

        assertEquals("Alice", b.getName());
        assertEquals(date, b.getDate());

        // ID must be non-negative, but exact value varies due to static counter
        assertTrue(b.getBirthdayID() >= 0);
    }

    @Test
    void testSetters() {
        Birthday b = new Birthday("Bob", makeDate(3, 10));

        b.setName("Bobby");
        b.setDate(makeDate(4, 11));

        assertEquals("Bobby", b.getName());
        assertEquals(makeDate(4, 11), b.getDate());
    }

    @Test
    void testBirthdaySummary() {
        Birthday b = new Birthday("Carol", makeDate(12, 25));
        String summary = b.getBirthdaySummary();

        assertTrue(summary.contains("Carol"));
        assertTrue(summary.contains("December 25"));
        assertTrue(summary.contains("ID:"));
    }
}