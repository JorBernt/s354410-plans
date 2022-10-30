package apputvikling.jorber.s354410_plans.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import apputvikling.jorber.s354410_plans.R;
import apputvikling.jorber.s354410_plans.Repository;
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
        Button addContactBtn = view.findViewById(R.id.addBtn);
        TextView contactTitleText = view.findViewById(R.id.contactTitleText);
        if (getArguments() != null) {
            String sName = getArguments().getString("name");
            String sPhone = getArguments().getString("phone");
            long _ID = getArguments().getLong("_ID");

            TextView name = view.findViewById(R.id.nameInput);
            TextView phone = view.findViewById(R.id.phoneInput);
            name.setText(sName);
            phone.setText(sPhone);

            Contact contact = new Contact(sName, sPhone, _ID);

            addContactBtn.setOnClickListener(v -> updateContact(view, contact));
            addContactBtn.setText(R.string.save);

            LinearLayout layout = view.findViewById(R.id.contactViewButtonLayout);
            Button deleteButton = new Button(layout.getContext());
            deleteButton.setText(R.string.delete);
            deleteButton.setBackground(getActivity().getDrawable(R.drawable.delete_button));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 10, 0);
            params.height = 100;
            deleteButton.setLayoutParams(params);
            deleteButton.setOnClickListener(v -> deleteContact(contact));
            layout.addView(deleteButton, 0);

            contactTitleText.setText(sName);
        } else {
            contactTitleText.setText(R.string.contactTitle);
            addContactBtn.setOnClickListener(v -> saveContact(view));
        }
    }

    private void saveContact(View view) {
        TextView name = view.findViewById(R.id.nameInput);
        TextView phone = view.findViewById(R.id.phoneInput);
        if (phone.getText().toString().isEmpty() || name.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), R.string.no_input, Toast.LENGTH_SHORT).show();
            return;
        }
        Contact contact = new Contact(name.getText().toString(), phone.getText().toString());
        class SaveContact extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Repository.getInstance(getContext()).saveContact(contact);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
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
        contact.setName(name.getText().toString());
        contact.setPhoneNumber(phone.getText().toString());
        class UpdateContact extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Repository.getInstance(getContext()).updateContact(contact);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                iOnClick.goBack();
                return;
            }
        }
        UpdateContact update = new UpdateContact();
        update.execute();
    }

    private void deleteContact(Contact contact) {
        class DeleteContact extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Repository.getInstance(getContext()).deleteContact(contact);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_LONG).show();
                iOnClick.goBack();
                return;
            }
        }
        DeleteContact delete = new DeleteContact();
        delete.execute();
    }
}
