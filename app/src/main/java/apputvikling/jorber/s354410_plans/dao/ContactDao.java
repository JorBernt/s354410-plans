package apputvikling.jorber.s354410_plans.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import apputvikling.jorber.s354410_plans.models.Contact;

@Dao
public interface ContactDao {

    @Query("SELECT * From Contact")
    List<Contact> getAll();

    @Insert
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Update
    void update(Contact contact);

}
