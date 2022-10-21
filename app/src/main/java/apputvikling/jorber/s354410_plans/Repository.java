package apputvikling.jorber.s354410_plans;

import android.content.Context;

import java.util.List;

import apputvikling.jorber.s354410_plans.db.DatabaseClient;
import apputvikling.jorber.s354410_plans.models.Contact;

public class Repository {
    private static Repository mInstance;
    private final Context mCtx;

    private Repository(Context ctx) {
        this.mCtx = ctx;
    }

    public static Repository getInstance(Context ctx) {
        if (mInstance == null)
            mInstance = new Repository(ctx);
        return mInstance;
    }

    public void saveContact(Contact contact) {
        DatabaseClient.getInstance(mCtx).getAppDatabase().contactDao().insert(contact);
    }

    public void deleteContact(Contact contact) {
        DatabaseClient.getInstance(mCtx).getAppDatabase().contactDao().delete(contact);
    }

    public void updateContact(Contact contact) {
        DatabaseClient.getInstance(mCtx).getAppDatabase().contactDao().update(contact);
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = DatabaseClient.getInstance(mCtx).getAppDatabase()
                .contactDao()
                .getAll();
        return contacts;
    }

}
