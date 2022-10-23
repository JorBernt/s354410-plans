package apputvikling.jorber.s354410_plans.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Entity
public class Appointment {
    @PrimaryKey(autoGenerate = true)
    private long _ID;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "message")
    private String message;
    @ColumnInfo(name = "contacts")
    private Contacts contacts;

    public Appointment(LocalDateTime date, String title, String message, List<Contact> contacts) {
        this.title = title;
        this.message = message;
        this.contacts = new Contacts(contacts);
        handleDate(date);
    }

    public Appointment() {
    }

    private void handleDate(LocalDateTime date) {
        this.date = getFormatter().format(date);
    }

    private static DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm", Locale.ENGLISH);
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        handleDate(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = new Contacts(contacts);
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContactIDs() {
        return contacts.getContactIDs();
    }

    public String getDay() {
        LocalDateTime date = LocalDateTime.parse(getDate(), getFormatter());
        return String.valueOf(date.getDayOfMonth());
    }

    public String getMonth() {
        LocalDateTime date = LocalDateTime.parse(getDate(), getFormatter());
        return date.getMonth().toString();
    }

    public String getYear() {
        LocalDateTime date = LocalDateTime.parse(getDate(), getFormatter());
        return String.valueOf(date.getYear());
    }

    public String getTime() {
        LocalDateTime date = LocalDateTime.parse(getDate(), getFormatter());
        return String.format(Locale.ENGLISH, "%02d:%02d", date.getHour(), date.getMinute());
    }

    public LocalDateTime getLocalDate() {
        return LocalDateTime.parse(getDate(), getFormatter());
    }
}
