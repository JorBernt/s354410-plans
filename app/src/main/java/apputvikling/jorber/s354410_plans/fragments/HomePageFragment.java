package apputvikling.jorber.s354410_plans.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import apputvikling.jorber.s354410_plans.R;
import apputvikling.jorber.s354410_plans.Repository;
import apputvikling.jorber.s354410_plans.adapters.AppointmentViewAdapter;
import apputvikling.jorber.s354410_plans.models.Appointment;

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
                appointments.sort(Comparator.comparing(Appointment::getLocalDate));
                singleAppointment = new ArrayList<>();
                if (appointments.isEmpty()) {
                    ((TextView) view.findViewById(R.id.nextAppointment_textView)).setText(R.string.no_appointments);
                    return;
                }

                singleAppointment.add(appointments.get(0));
                adapter = new AppointmentViewAdapter(singleAppointment, iOnClick);
                rv.setAdapter(adapter);
            }
        }
        GetAppointments get = new GetAppointments();
        get.execute();
    }

    public void updateView(int position) {
        adapter.notifyItemRemoved(position);
        onViewCreated(getView(), null);
    }
}
