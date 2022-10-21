package apputvikling.jorber.s354410_plans.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import apputvikling.jorber.s354410_plans.R;
import apputvikling.jorber.s354410_plans.Repository;
import apputvikling.jorber.s354410_plans.activity.MainActivity;
import apputvikling.jorber.s354410_plans.db.DatabaseClient;
import apputvikling.jorber.s354410_plans.models.Contact;

public class AddContactFragment extends Fragment {
    IOnClick iOnClick;

    public AddContactFragment() {
        super(R.layout.fragment_addcontact);
    }

    public void setOnClick(IOnClick iOnClick) {
        this.iOnClick = iOnClick;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Button addContactBtn = view.findViewById(R.id.addContactBtn);
        if(savedInstanceState != null) {
            String sName = savedInstanceState.getString("name");
            String sPhone = savedInstanceState.getString("phone");
            long _ID = savedInstanceState.getLong("_ID");
            TextView name = view.findViewById(R.id.nameInput);
            TextView phone = view.findViewById(R.id.phoneInput);
            name.setText(sName);
            phone.setText(sPhone);
            addContactBtn.setOnClickListener(v -> updateContact(v, new Contact(sName, sPhone, _ID)));
        }
        addContactBtn.setOnClickListener(this::saveContact);
    }

    private void saveContact(View view) {
        TextView name = view.findViewById(R.id.nameInput);
        TextView phone = view.findViewById(R.id.phoneInput);
        Contact contact = new Contact(name.getText().toString(), phone.getText().toString());
        class SaveContact extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getContext()).getAppDatabase().contactDao().insert(contact);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                name.setText("");
                phone.setText("");
                iOnClick.goBack();
                return;
            }
        }
        SaveContact save = new SaveContact();
        save.execute();
    }

    private void updateContact(View view, Contact contact) {
        TextView name = view.findViewById(R.id.nameInput);
        TextView phone = view.findViewById(R.id.phoneInput);
        class SaveContact extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getContext()).getAppDatabase().contactDao().update(contact);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                name.setText("");
                phone.setText("");
                iOnClick.goBack();
                return;
            }
        }
        SaveContact save = new SaveContact();
        save.execute();
    }
}
