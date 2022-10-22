package apputvikling.jorber.s354410_plans.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
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
    @ColumnInfo(name = "message")
    private String message;
    @ColumnInfo(name = "contacts")
    private Contacts contacts;

    public Appointment(Date date, String message, List<Contact> contacts) {
        this.message = message;
        this.contacts = new Contacts(contacts);
        handleDate(date);
    }

    public Appointment() {
    }

    private void handleDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.date = calendar.toString();
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

    public void setDate(Date date) {
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

    public String getContactIDs() {
        return contacts.getContactIDs();
    }

    public String getDay() {
        Calendar calendar = Calendar.getInstance();
        //calendar.setTime(new Date(date));
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public String getMonth() {
        Calendar calendar = Calendar.getInstance();
        //calendar.setTime(new Date(date));
        return new SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.getTime());
    }

    public String getYear() {
        Calendar calendar = Calendar.getInstance();
        //calendar.setTime(new Date(date));
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

}
