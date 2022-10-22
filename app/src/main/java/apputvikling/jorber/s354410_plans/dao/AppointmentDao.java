package apputvikling.jorber.s354410_plans.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import apputvikling.jorber.s354410_plans.models.Appointment;

@Dao
public interface AppointmentDao {
    @Query("SELECT * From Appointment")
    List<Appointment> getAll();

    @Insert
    void insert(Appointment appointment);

    @Delete
    void delete(Appointment appointment);

    @Update
    void update(Appointment appointment);
}
