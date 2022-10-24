package apputvikling.jorber.s354410_plans.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import apputvikling.jorber.s354410_plans.R;
import apputvikling.jorber.s354410_plans.Repository;
import apputvikling.jorber.s354410_plans.adapters.AttendeesAdapter;
import apputvikling.jorber.s354410_plans.models.Appointment;
import apputvikling.jorber.s354410_plans.models.Contact;

public class AddAppointmentFragment extends Fragment {
    IOnClick iOnClick;
    List<Contact> attendees = new ArrayList<>();


    public AddAppointmentFragment() {
        super(R.layout.fragment_addappointment);
    }

    public void setOnClick(IOnClick iOnClick) {
        this.iOnClick = iOnClick;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        TextView title = view.findViewById(R.id.appointmentTitleText);
        title.setText(R.string.newAppointment);
        TextView dateView = view.findViewById(R.id.dateView);
        dateView.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
                return;
            DatePickerFragment datePicker = new DatePickerFragment();
            datePicker.setDateText(dateView);
            iOnClick.setDate(datePicker);
        });

        TextView timeView = view.findViewById(R.id.timeView);
        timeView.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
                return;
            TimePickerFragment datePicker = new TimePickerFragment();
            datePicker.setDateText(timeView);
            iOnClick.setDate(datePicker);
        });


        RecyclerView rv = view.findViewById(R.id.attendee_recycler_view);
        LinearLayoutManager layout = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv.setLayoutManager(layout);
        AttendeesAdapter adapter = new AttendeesAdapter(attendees, iOnClick);
        rv.setAdapter(adapter);
        Spinner contactSpinner = view.findViewById(R.id.contactSpinner);

        class GetAllContacts extends AsyncTask<Void, Void, List<Contact>> {

            @Override
            protected List<Contact> doInBackground(Void... voids) {
                return Repository.getInstance(getContext()).getAllContacts();
            }

            @Override
            protected void onPostExecute(List<Contact> contacts) {
                contacts.add(0, new Contact(getString(R.string.select_attendees), null));
                if(getArguments() != null) {
                    List<Long> ids = Arrays.stream(getArguments().getString("attendees")
                            .split(":"))
                            .mapToLong(Long::parseLong)
                            .boxed()
                            .collect(Collectors.toList());
                    for(long id : ids) {
                        for(Contact c : contacts) {
                            if(id == c.get_ID()) {
                                attendees.add(c);
                                adapter.notifyItemInserted(attendees.size());
                                break;
                            }
                        }
                    }
                }
                ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(getContext(),
                        R.layout.spinner_item,
                        contacts) {
                    @Override
                    public boolean isEnabled(int position) {
                        return position != 0;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        Contact contact = getItem(position);
                        if (position == 0 || attendeesContains(contact)) {
                            // Set the hint text color gray
                            tv.setTextColor(getResources().getColor(R.color.light_gray));
                        } else {
                            tv.setTextColor(getResources().getColor(R.color.white));
                        }
                        return view;
                    }
                };
                adapter.setDropDownViewResource(R.layout.spinner_item);
                contactSpinner.setAdapter(adapter);

            }
        }
        GetAllContacts getAllContacts = new GetAllContacts();
        getAllContacts.execute();

        contactSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos == 0 || attendeesContains((Contact) parent.getItemAtPosition(pos))) {
                    parent.setSelection(0);
                    return;
                }
                Contact name = (Contact) parent.getItemAtPosition(pos);
                attendees.add(name);
                adapter.notifyItemInserted(attendees.size());
                parent.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button button = view.findViewById(R.id.addBtn);

        TextView titleInput = view.findViewById(R.id.appointment_titleInput);
        TextView description = view.findViewById(R.id.appointment_descriptionInput);
        if(getArguments() != null) {
            title.setText(getArguments().getString("title"));
            titleInput.setText(getArguments().getString("title"));
            description.setText(getArguments().getString("description"));
            dateView.setText(getArguments().getString("date").split(" ")[0]);
            timeView.setText(getArguments().getString("time"));
            button.setText(R.string.save);
            button.setOnClickListener(v -> updateAppointment(view, getArguments().getLong("_ID")));
        }
        else {
            button.setOnClickListener(v -> saveAppointment(view));
        }

    }

    private boolean attendeesContains(Contact contact) {
        for (Contact c : attendees)
            if (c.get_ID() == contact.get_ID())
                return true;
        return false;
    }

    private void saveAppointment(View view) {
        Appointment appointment = getAppointment(view);
        class SaveContact extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Repository.getInstance(getContext()).saveAppointment(appointment);
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

    @NonNull
    private Appointment getAppointment(View view) {
        String title = ((TextView) (view.findViewById(R.id.appointment_titleInput))).getText().toString();
        String description = ((TextView) (view.findViewById(R.id.appointment_descriptionInput))).getText().toString();
        String date = ((TextView) (view.findViewById(R.id.dateView))).getText().toString();
        String time = ((TextView) (view.findViewById(R.id.timeView))).getText().toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm", Locale.ENGLISH);
        LocalDateTime dateFromString = LocalDateTime.parse(date + " " + time, formatter);
        return new Appointment(dateFromString, title, description, attendees);
    }

    private void updateAppointment(View view, long id) {
        Appointment appointment = getAppointment(view);
        appointment.set_ID(id);

        class UpdateAppointment extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Repository.getInstance(getContext()).updateAppointment(appointment);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getContext(), "Updated", Toast.LENGTH_LONG).show();
                iOnClick.goBack();
                return;
            }
        }
        UpdateAppointment update = new UpdateAppointment();
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
