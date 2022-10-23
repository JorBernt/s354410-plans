package apputvikling.jorber.s354410_plans.fragments;

import androidx.fragment.app.DialogFragment;

import apputvikling.jorber.s354410_plans.activity.MainActivity;
import apputvikling.jorber.s354410_plans.models.Appointment;
import apputvikling.jorber.s354410_plans.models.Contact;

public interface IOnClick {

    void openFragmentFromFragment(MainActivity.Fragments fragment);

    void openEditExistingContact(Contact contact);

    void deleteContact(Contact contact, int position);

    void goBack();

    void openEditExistingAppointment(Appointment appointment);

    void deleteAppointment(Appointment appointment, int position);

    void setDate(DialogFragment datePicker);
}
