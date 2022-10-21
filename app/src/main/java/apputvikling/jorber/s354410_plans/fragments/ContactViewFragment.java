package apputvikling.jorber.s354410_plans.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import apputvikling.jorber.s354410_plans.Repository;
import apputvikling.jorber.s354410_plans.activity.MainActivity;
import apputvikling.jorber.s354410_plans.models.Contact;
import apputvikling.jorber.s354410_plans.adapters.ContactViewAdapter;
import apputvikling.jorber.s354410_plans.R;

public class ContactViewFragment extends Fragment {

    IOnClick iOnClick;

    public ContactViewFragment() {
        super(R.layout.fragment_viewcontacts);
    }

    public void setOnClick(IOnClick iOnClick) {
        this.iOnClick = iOnClick;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = view.findViewById(R.id.contactsView);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        DividerItemDecoration did = new DividerItemDecoration(rv.getContext(), lm.getOrientation());
        did.setDrawable(AppCompatResources.getDrawable(getActivity(), R.drawable.divider));
        rv.addItemDecoration(did);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> iOnClick.openFragmentFromFragment(MainActivity.Fragments.ADD_CONTACT_VIEW));

        class GetContacts extends AsyncTask<Void, Void, List<Contact>> {

            @Override
            protected List<Contact> doInBackground(Void... voids) {
                return Repository.getInstance().getAllContacts(getContext());
            }

            @Override
            protected void onPostExecute(List<Contact> contacts) {
                super.onPostExecute(contacts);
                rv.setAdapter(new ContactViewAdapter(contacts, iOnClick));
            }
        }
        GetContacts save = new GetContacts();
        save.execute();


    }


}
