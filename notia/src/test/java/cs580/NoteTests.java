package cs580;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoteTests {

    @Test
    void testCreateNote() {
        Note n = new Note("Hello world");
        assertEquals("Hello world", n.getText());
        assertNotNull(n.getTimestamp());
        assertTrue(n.getId() > 0);
    }

    @Test
    void testEditNoteUpdatesTimestamp() throws InterruptedException {
        Note n = new Note("Original");
        var oldTime = n.getTimestamp();

        Thread.sleep(5); // ensure timestamp difference
        n.setText("Updated");

        assertEquals("Updated", n.getText());
        assertNotEquals(oldTime, n.getTimestamp());
    }

    @Test
    void testNoteDatabaseAddRetrieve() {
        NoteDatabase db = new NoteDatabase();
        Note n = new Note("Test");
        db.addNote(n);

        Note found = db.getNote(n.getId());
        assertNotNull(found);
        assertEquals("Test", found.getText());
    }

    @Test
    void testNoteDatabaseRemove() {
        NoteDatabase db = new NoteDatabase();
        Note n = new Note("Delete Me");
        db.addNote(n);

        boolean removed = db.removeNote(n.getId());
        assertTrue(removed);

        assertNull(db.getNote(n.getId()));
    }

    @Test
    void testNoteDatabaseUpdate() {
        NoteDatabase db = new NoteDatabase();
        Note n = new Note("Old");
        db.addNote(n);

        Note updated = new Note("New");
        boolean success = db.updateNote(n.getId(), updated);

        assertTrue(success);
        assertEquals("New", db.getNote(updated.getId()).getText());
    }
}