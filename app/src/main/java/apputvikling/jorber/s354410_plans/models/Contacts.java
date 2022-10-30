package apputvikling.jorber.s354410_plans.models;

import android.content.Context;

import java.util.List;
import java.util.stream.Collectors;

import apputvikling.jorber.s354410_plans.R;

public class Contacts {
    private final List<Contact> contacts;
    private String names = "";
    private Context ctx;

    public Contacts(List<Contact> contacts) {
        this.contacts = contacts;
        if (contacts != null)
            this.names = contacts.stream().map(Contact::getName).collect(Collectors.joining(", "));
    }

    public void setContext(Context ctx) {
        this.ctx = ctx;
    }

    public String getContactIDs() {
        return contacts.stream().map(Contact::get_ID).map(String::valueOf).collect(Collectors.joining(":"));
    }

    public String getNames() {
        return names == null ? "" : String.format("%s:\n%s", ctx.getString(R.string.attendees), names);
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
