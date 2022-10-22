package apputvikling.jorber.s354410_plans.models;

import java.util.List;
import java.util.stream.Collectors;

public class Contacts {
    private final List<Contact> contacts;

    public Contacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getContactIDs() {
        return contacts.stream().map(Contact::get_ID).map(String::valueOf).collect(Collectors.joining(":"));
    }

    public String getNames() {
        return contacts.stream().map(Contact::getName).collect(Collectors.joining(", "));
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
