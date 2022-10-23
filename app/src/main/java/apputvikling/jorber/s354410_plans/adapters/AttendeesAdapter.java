package apputvikling.jorber.s354410_plans.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import apputvikling.jorber.s354410_plans.R;
import apputvikling.jorber.s354410_plans.fragments.IOnClick;
import apputvikling.jorber.s354410_plans.models.Contact;

public class AttendeesAdapter extends RecyclerView.Adapter<AttendeesAdapter.ContactViewHolder> {

    List<Contact> attendees;
    IOnClick iOnClick;

    public AttendeesAdapter(List<Contact> contacts, IOnClick iOnClick) {
        this.attendees = contacts;
        this.iOnClick = iOnClick;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendees_cardview, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = attendees.get(position);
        holder.name.setText(contact.getName());
        holder.removeAttendeeBtn.setOnClickListener(view -> {
            attendees.remove(contact);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return attendees.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        ImageButton removeAttendeeBtn;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.attendee_cardview);
            name = itemView.findViewById(R.id.attendee_name_view);
            removeAttendeeBtn = itemView.findViewById(R.id.removeAttendeeBtn);
        }
    }
}
