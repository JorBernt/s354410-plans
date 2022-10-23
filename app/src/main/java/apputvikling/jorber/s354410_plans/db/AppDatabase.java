package apputvikling.jorber.s354410_plans.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import apputvikling.jorber.s354410_plans.dao.AppointmentDao;
import apputvikling.jorber.s354410_plans.dao.ContactDao;
import apputvikling.jorber.s354410_plans.models.Appointment;
import apputvikling.jorber.s354410_plans.models.Contact;

@Database(entities = {Contact.class, Appointment.class}, version = 4)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract AppointmentDao appointmentDao();
}