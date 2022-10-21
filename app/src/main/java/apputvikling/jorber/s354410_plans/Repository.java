package apputvikling.jorber.s354410_plans;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import apputvikling.jorber.s354410_plans.db.DatabaseClient;
import apputvikling.jorber.s354410_plans.models.Contact;

public class Repository {
    private static Repository mInstance;

    private Repository() {}

    public static Repository getInstance() {
        if(mInstance == null)
            mInstance = new Repository();
        return mInstance;
    }

    public void saveContact(Contact contact, Context ctx) {
        DatabaseClient.getInstance(ctx).getAppDatabase().contactDao().insert(contact);
    }

    public List<Contact> getAllContacts(Context ctx) {
        List<Contact> contacts = DatabaseClient.getInstance(ctx).getAppDatabase()
                .contactDao()
                .getAll();
        return contacts;

    }

}
