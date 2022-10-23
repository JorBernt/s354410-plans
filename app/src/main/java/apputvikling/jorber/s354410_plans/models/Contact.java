package apputvikling.jorber.s354410_plans.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private long _ID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "phone")
    private String phoneNumber;

    private String shortHandName;


    public Contact() { }

    public Contact(String name, String phoneNumber) {
        this.name = trimExcessWhitespace(name);
        this.phoneNumber = phoneNumber;
        this.shortHandName = generateShortHand(name);
    }

    public Contact(String name, String phoneNumber, long _ID) {
        this(name, phoneNumber);
        this._ID = _ID;
    }

    private String trimExcessWhitespace(String name) {
        return name.trim().replaceAll("[ ]{2,}", " ");
    }

    private String generateShortHand(String name) {
        String[] parts = name.toUpperCase().split(" ");
        return String.format("%s%s", parts[0].charAt(0), parts.length > 1 ? parts[parts.length - 1].charAt(0) : "");
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = trimExcessWhitespace(name);
        setShortHandName(generateShortHand(this.name));
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getShortHandName() {
        return shortHandName;
    }

    public void setShortHandName(String shortHandName) {
        if(shortHandName.length() > 2)
            return;
        this.shortHandName = shortHandName;
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    @Override
    public String toString() {
        return name;
    }
}
