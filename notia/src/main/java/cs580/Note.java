package cs580;

import java.util.Date;

public class Note {
    private static int counter = 1;

    private int id;
    private String text;
    private Date timestamp;

    public Note(String text) {
        this.id = counter++;
        this.text = text;
        this.timestamp = new Date();
    }

    public int getId() { return id; }
    public String getText() { return text; }
    public Date getTimestamp() { return timestamp; }

    public void setText(String newText) {
        this.text = newText;
        this.timestamp = new Date(); // update timestamp on edit
    }

    public String getNoteSummary() {
        return "ID: " + id +
               "\nText: " + text +
               "\nLast Updated: " + timestamp.toString();
    }
}