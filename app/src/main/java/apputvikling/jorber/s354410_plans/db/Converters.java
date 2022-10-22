package apputvikling.jorber.s354410_plans.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import apputvikling.jorber.s354410_plans.models.Contacts;


public class Converters {
    @TypeConverter
    public static String contactToIdString(Contacts contacts) {
        Gson gson = new Gson();
        String json = gson.toJson(contacts);
        return json;
    }

    @TypeConverter
    public static Contacts idStringToContacts(String string) {
        Gson gson = new Gson();
        return gson.fromJson(string, Contacts.class);
    }
}