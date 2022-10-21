package apputvikling.jorber.s354410_plans.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import apputvikling.jorber.s354410_plans.dao.ContactDao;
import apputvikling.jorber.s354410_plans.models.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
}