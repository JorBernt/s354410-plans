package apputvikling.jorber.s354410_plans.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import apputvikling.jorber.s354410_plans.R;
import apputvikling.jorber.s354410_plans.db.DatabaseClient;
import apputvikling.jorber.s354410_plans.fragments.AddContactFragment;
import apputvikling.jorber.s354410_plans.fragments.ContactViewFragment;
import apputvikling.jorber.s354410_plans.fragments.IOnClick;
import apputvikling.jorber.s354410_plans.models.Contact;

public class MainActivity extends AppCompatActivity implements IOnClick {

    public enum Fragments {
        ADD_CONTACT_VIEW(AddContactFragment.class),
        CONTACT_VIEW(ContactViewFragment.class);

        Class<? extends Fragment> klass;

        Fragments(Class<? extends Fragment> klass) {
            this.klass = klass;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        if (savedInstanceState == null) {
            ContactViewFragment cvf = new ContactViewFragment();
            AddContactFragment acf = new AddContactFragment();
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentContainerView, cvf, Fragments.CONTACT_VIEW.name())
                    .add(R.id.fragmentContainerView, acf, Fragments.ADD_CONTACT_VIEW.name())
                    .commit();
            cvf.setOnClick(this);
            acf.setOnClick(this);
        }
        DatabaseClient.getInstance(this);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.page_1) {
                return true;
            }
            if (id == R.id.page_2) {
                openFragment(Fragments.CONTACT_VIEW);
                return true;
            }
            if (id == R.id.page_3) {
                return true;
            }
            return false;
        });
    }

    public void openFragment(Fragments fragment, Bundle bundle) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //this is a helper class that replaces the container with the fragment. You can replace or add fragments.
        Fragment frag = null;
        if(bundle != null) {
            frag.setArguments(bundle);
        }
        else {
            frag = fm.findFragmentByTag(fragment.name());
        }
        transaction.replace(R.id.fragmentContainerView, frag);
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
    public void deleteContact(Contact contact) {

    }

    @Override
    public void goBack() {
        getSupportFragmentManager().popBackStack();
    }


}