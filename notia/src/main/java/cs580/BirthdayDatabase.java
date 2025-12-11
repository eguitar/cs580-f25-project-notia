package cs580;

import java.util.ArrayList;
import java.util.Iterator;

public class BirthdayDatabase implements Iterable<Birthday> {
    private ArrayList<Birthday> birthdayList = new ArrayList<>();

    public void addBirthday(Birthday b) { birthdayList.add(b); }

    public boolean removeBirthday(int id) {
        return birthdayList.removeIf(b -> b.getBirthdayID() == id);
    }

    public Birthday getBirthday(int id) {
        for (Birthday b : birthdayList) {
            if (b.getBirthdayID() == id)
                return b;
        }
        return null;
    }

    public boolean updateBirthday(int id, Birthday newBirthday) {
        for (int i = 0; i < birthdayList.size(); i++) {
            if (birthdayList.get(i).getBirthdayID() == id) {
                birthdayList.set(i, newBirthday);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Birthday> getAllBirthdays() {
        return birthdayList;
    }

    @Override
    public Iterator<Birthday> iterator() {
        return birthdayList.iterator();
    }
}