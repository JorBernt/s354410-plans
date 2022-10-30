package apputvikling.jorber.s354410_plans.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import apputvikling.jorber.s354410_plans.R;
import apputvikling.jorber.s354410_plans.Repository;
import apputvikling.jorber.s354410_plans.adapters.AppointmentViewAdapter;
import apputvikling.jorber.s354410_plans.models.Appointment;
import apputvikling.jorber.s354410_plans.utility.MonthTranslator;

public class HomePageFragment extends Fragment {
    private IOnClick iOnClick;
    private AppointmentViewAdapter adapter;
    private List<Appointment> singleAppointment;

    public HomePageFragment() {
        super(R.layout.home_page_view);
    }

    public void setiOnClick(IOnClick iOnClick) {
        this.iOnClick = iOnClick;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        TextView dateView = view.findViewById(R.id.homeDateView);
        LocalDateTime dateToday = LocalDateTime.now();
        String translatedMonth = getString(MonthTranslator.translateMonth(dateToday.getMonth().toString()));
        String month = String.format("%s%s", translatedMonth.charAt(0), translatedMonth.substring(1).toLowerCase(Locale.ROOT));
        dateView.setText(String.format(Locale.ENGLISH, "%02d. %s %d", dateToday.getDayOfMonth(), month, dateToday.getYear()));
        RecyclerView rv = view.findViewById(R.id.singleAppiontmentView);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        class GetAppointments extends AsyncTask<Void, Void, List<Appointment>> {
            @Override
            protected List<Appointment> doInBackground(Void... voids) {
                return Repository.getInstance(getContext()).getAllAppointments();
            }

            @Override
            protected void onPostExecute(List<Appointment> appointments) {
                super.onPostExecute(appointments);
                appointments.sort(Comparator.comparing(Appointment::getLocalDateTime));
                singleAppointment = new ArrayList<>();

                Appointment appointment = null;
                for (Appointment a : appointments) {
                    if (a.getLocalDateTime().isAfter(dateToday)) {
                        appointment = a;
                        break;
                    }
                }

                if (appointment == null) {
                    ((TextView) view.findViewById(R.id.nextAppointment_textView)).setText(R.string.no_appointments);
                    return;
                }
                appointment.getContacts().setContext(getContext());
                if (appointment.getTitle().isEmpty())
                    appointment.setTitle(getResources().getString(R.string.untitled_appointment));
                if (appointment.getMessage().isEmpty())
                    appointment.setMessage(getString(R.string.no_message));

                singleAppointment.add(appointment);
                adapter = new AppointmentViewAdapter(singleAppointment, iOnClick);
                rv.setAdapter(adapter);
            }
        }
        GetAppointments get = new GetAppointments();
        get.execute();


        ToggleButton sendSmsBtn = view.findViewById(R.id.sendSmsToggleBtn);
        sendSmsBtn.setTextOn(getString(R.string.sms_on));
        sendSmsBtn.setTextOff(getString(R.string.sms_off));
        sendSmsBtn.setOnClickListener((listener) -> {
            getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit().putBoolean("SEND_SMS", sendSmsBtn.isChecked()).commit();
        });
        sendSmsBtn.setChecked(getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).getBoolean("SEND_SMS", false));
    }

    public void updateView(int position) {
        adapter.notifyItemRemoved(position);
        onViewCreated(getView(), null);
    }
}

