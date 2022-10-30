package apputvikling.jorber.s354410_plans.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import apputvikling.jorber.s354410_plans.R;
import apputvikling.jorber.s354410_plans.fragments.IOnClick;
import apputvikling.jorber.s354410_plans.models.Appointment;
import apputvikling.jorber.s354410_plans.utility.MonthTranslator;

public class AppointmentViewAdapter extends RecyclerView.Adapter<AppointmentViewAdapter.AppointmentViewHolder> {

    List<Appointment> appointments;
    IOnClick iOnClick;

    public AppointmentViewAdapter(List<Appointment> appointments, IOnClick iOnClick) {
        this.appointments = appointments;
        this.iOnClick = iOnClick;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_cardview, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.title.setText(appointment.getTitle());
        holder.description.setText(appointment.getMessage());
        holder.attendees.setText(appointment.getContacts().getNames());
        holder.date.time.setText(appointment.getTime());
        holder.date.day.setText(appointment.getDay());
        holder.date.month.setText(MonthTranslator.translateMonth(appointment.getMonth()));
        holder.date.year.setText(appointment.getYear());
        holder.appointmentSettingsBtn.setOnClickListener(view -> {
            PopupMenu settingsMenu = new PopupMenu(view.getContext(), view);
            settingsMenu.inflate(R.menu.contact_settings_menu);
            settingsMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.edit) {
                    iOnClick.openEditExistingAppointment(appointment);
                    return true;
                }
                if (id == R.id.delete) {
                    appointments.remove(appointment);
                    iOnClick.deleteAppointment(appointment, position);
                    return true;
                }
                return false;
            });
            settingsMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class DateView {
        TextView time;
        TextView day;
        TextView month;
        TextView year;

        public DateView(TextView time, TextView day, TextView month, TextView year) {
            this.time = time;
            this.day = day;
            this.month = month;
            this.year = year;
        }
    }

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView description;
        TextView attendees;
        DateView date;

        ImageButton appointmentSettingsBtn;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.appointment_cardview);
            title = itemView.findViewById(R.id.appointment_title_view);
            description = itemView.findViewById(R.id.eventDescription);
            attendees = itemView.findViewById(R.id.attendees_textview);
            appointmentSettingsBtn = itemView.findViewById(R.id.appointmentSettingsBtn);
            date = new DateView(itemView.findViewById(R.id.dateTime), itemView.findViewById(R.id.dateDay), itemView.findViewById(R.id.dateMonth), itemView.findViewById(R.id.dateYear));
        }
    }
}
