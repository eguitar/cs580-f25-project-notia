package cs580;

import java.util.ArrayList;
import java.util.Iterator;

public class NoteDatabase implements Iterable<Note> {

    private ArrayList<Note> notes;

    public NoteDatabase() {
        notes = new ArrayList<>();
    }

    public void addNote(Note n) {
        notes.add(n);
    }

    public boolean removeNote(int id) {
        return notes.removeIf(n -> n.getId() == id);
    }

    public Note getNote(int id) {
        for (Note n : notes) {
            if (n.getId() == id) return n;
        }
        return null;
    }

    public boolean updateNote(int id, Note newN) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == id) {
                notes.set(i, newN);
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Note> iterator() {
        return notes.iterator();
    }
}