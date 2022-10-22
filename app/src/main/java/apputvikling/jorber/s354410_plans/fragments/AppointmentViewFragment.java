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

import java.util.List;

import apputvikling.jorber.s354410_plans.R;
import apputvikling.jorber.s354410_plans.Repository;
import apputvikling.jorber.s354410_plans.activity.MainActivity;
import apputvikling.jorber.s354410_plans.adapters.AppointmentViewAdapter;
import apputvikling.jorber.s354410_plans.models.Appointment;

public class AppointmentViewFragment extends Fragment {

    private IOnClick iOnClick;
    private AppointmentViewAdapter adapter;

    public AppointmentViewFragment() {
        super(R.layout.fragment_viewappointments);
    }

    public void setOnClick(IOnClick iOnClick) {
        this.iOnClick = iOnClick;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = view.findViewById(R.id.appointmentView);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        DividerItemDecoration did = new DividerItemDecoration(rv.getContext(), lm.getOrientation());
        did.setDrawable(AppCompatResources.getDrawable(getActivity(), R.drawable.divider));
        rv.addItemDecoration(did);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> iOnClick.openFragmentFromFragment(MainActivity.Fragments.ADD_CONTACT_VIEW));

        class GetAppointments extends AsyncTask<Void, Void, List<Appointment>> {

            @Override
            protected List<Appointment> doInBackground(Void... voids) {
                return Repository.getInstance(getContext()).getAllAppointments();
            }

            @Override
            protected void onPostExecute(List<Appointment> appointments) {
                super.onPostExecute(appointments);
                adapter = new AppointmentViewAdapter(appointments, iOnClick);
                rv.setAdapter(adapter);
            }
        }
        GetAppointments get = new GetAppointments();
        get.execute();
    }
}
