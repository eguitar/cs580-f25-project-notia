package cs580;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Birthday {
    private static int counter = 0;
    private final int birthdayID;
    private String name;
    private Date date;   // month + day

    public Birthday(String name, Date date) {
        this.birthdayID = counter++;
        this.name = name;
        this.date = date;
    }

    public int getBirthdayID() { return birthdayID; }
    public String getName() { return name; }
    public Date getDate() { return date; }

    public void setName(String name) { this.name = name; }
    public void setDate(Date date) { this.date = date; }

    public String getBirthdaySummary() {
        SimpleDateFormat fmt = new SimpleDateFormat("MMMM dd");
        return "ID: " + birthdayID +
               "\nName: " + name +
               "\nBirthday: " + fmt.format(date);
    }
}