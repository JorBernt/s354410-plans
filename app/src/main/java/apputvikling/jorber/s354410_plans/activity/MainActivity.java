package apputvikling.jorber.s354410_plans.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import apputvikling.jorber.s354410_plans.R;
import apputvikling.jorber.s354410_plans.Repository;
import apputvikling.jorber.s354410_plans.db.DatabaseClient;
import apputvikling.jorber.s354410_plans.fragments.AddAppointmentFragment;
import apputvikling.jorber.s354410_plans.fragments.AddContactFragment;
import apputvikling.jorber.s354410_plans.fragments.AppointmentViewFragment;
import apputvikling.jorber.s354410_plans.fragments.ContactViewFragment;
import apputvikling.jorber.s354410_plans.fragments.IOnClick;
import apputvikling.jorber.s354410_plans.models.Appointment;
import apputvikling.jorber.s354410_plans.models.Contact;

public class MainActivity extends AppCompatActivity implements IOnClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        DatabaseClient.getInstance(this);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        openFragment(Fragments.CONTACT_VIEW);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.page_1) {
                openFragment(Fragments.CONTACT_VIEW);
                return true;
            }
            if (id == R.id.contacts_page) {
                openFragment(Fragments.CONTACT_VIEW);
                return true;
            }
            openFragment(Fragments.APPOINTMENT_VIEW);
            return true;
        });
    }

    public void openFragment(Fragments fragment, Bundle bundle) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment frag;
        switch (fragment) {
            case ADD_CONTACT_VIEW:
                frag = new AddContactFragment();
                ((AddContactFragment) frag).setOnClick(this);
                break;
            case CONTACT_VIEW:
                frag = new ContactViewFragment();
                ((ContactViewFragment) frag).setOnClick(this);
                break;
            case APPOINTMENT_VIEW:
                frag = new AppointmentViewFragment();
                ((AppointmentViewFragment) frag).setOnClick(this);
                break;
            case ADD_APPOINTMENT_VIEW:
                frag = new AddAppointmentFragment();
                ((AddAppointmentFragment) frag).setOnClick(this);
                break;
            default:
                return;
        }
        if (bundle != null)
            frag.setArguments(bundle);
        transaction.replace(R.id.fragmentContainerView, frag, fragment.name());
        transaction.addToBackStack(null); //if you add fragments it will be added to the backStack. If you replace the fragment it will add only the last fragment
        transaction.commit(); // commit() performs the action
    }

    public void openFragment(Fragments fragment) {
        openFragment(fragment, null);
    }

    @Override
    public void openFragmentFromFragment(Fragments fragment) {
        openFragment(fragment);
    }

    @Override
    public void openEditExistingContact(Contact contact) {
        Bundle bundle = new Bundle();
        bundle.putString("name", contact.getName());
        bundle.putString("phone", contact.getPhoneNumber());
        bundle.putLong("_ID", contact.get_ID());
        openFragment(Fragments.ADD_CONTACT_VIEW, bundle);
    }

    @Override
    public void deleteContact(Contact contact, int position) {
        class DeleteContact extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Repository.getInstance(getBaseContext()).deleteContact(contact);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getBaseContext(), "Deleted", Toast.LENGTH_LONG).show();
                ContactViewFragment frag = (ContactViewFragment) getSupportFragmentManager().findFragmentByTag(Fragments.CONTACT_VIEW.name());
                if (frag == null)
                    return;
                frag.updateView(position);
            }
        }
        DeleteContact delete = new DeleteContact();
        delete.execute();
    }

    @Override
    public void goBack() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void openEditExistingAppointment(Appointment appointment) {
        Bundle bundle = new Bundle();
        bundle.putString("title", appointment.getTitle());
        bundle.putString("description", appointment.getMessage());
        bundle.putString("attendees", appointment.getContactIDs());
        bundle.putString("time", appointment.getTime());
        bundle.putString("date", appointment.getDate());
        bundle.putLong("_ID", appointment.get_ID());
        openFragment(Fragments.ADD_APPOINTMENT_VIEW, bundle);
    }

    @Override
    public void deleteAppointment(Appointment appointment, int position) {
        class DeleteAppointment extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Repository.getInstance(getBaseContext()).deleteAppointment(appointment);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                AppointmentViewFragment frag = (AppointmentViewFragment) getSupportFragmentManager().findFragmentByTag(Fragments.APPOINTMENT_VIEW.name());
                if(frag == null)
                    return;
                frag.updateView(position);
            }
        }
        DeleteAppointment delete = new DeleteAppointment();
        delete.execute();
    }

    @Override
    public void setDate(DialogFragment datePicker) {
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    public enum Fragments {
        ADD_CONTACT_VIEW(AddContactFragment.class),
        CONTACT_VIEW(ContactViewFragment.class),
        APPOINTMENT_VIEW(AppointmentViewFragment.class),
        ADD_APPOINTMENT_VIEW(AddAppointmentFragment.class);
        Class<? extends Fragment> klass;

        Fragments(Class<? extends Fragment> klass) {
            this.klass = klass;
        }
    }


}