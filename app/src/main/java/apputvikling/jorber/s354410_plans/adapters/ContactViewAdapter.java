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
import apputvikling.jorber.s354410_plans.models.Contact;

public class ContactViewAdapter extends RecyclerView.Adapter<ContactViewAdapter.ContactViewHolder> {

    List<Contact> contacts;
    IOnClick iOnClick;

    public ContactViewAdapter(List<Contact> contacts, IOnClick iOnClick){
        this.contacts = contacts;
        this.iOnClick = iOnClick;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_cardview, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.name.setText(contact.getName());
        holder.phoneNumber.setText(contact.getPhoneNumber());
        holder.profileIcon.setText(contact.getShortHandName());
        holder.contactSettingsBtn.setOnClickListener(view -> {
            PopupMenu settingsMenu = new PopupMenu(view.getContext(), view);
            settingsMenu.inflate(R.menu.contact_settings_menu);
            settingsMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if(id == R.id.edit) {
                    iOnClick.openEditExistingContact(contact);
                    return true;
                }
                if(id == R.id.delete) {
                    contacts.remove(contact);
                    iOnClick.deleteContact(contact, position);
                    return true;
                }
                return false;
            });
            settingsMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView phoneNumber;
        TextView profileIcon;
        ImageButton contactSettingsBtn;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.contactsView);
            name = itemView.findViewById(R.id.nameView);
            phoneNumber = itemView.findViewById(R.id.phoneNumberView);
            profileIcon = itemView.findViewById(R.id.contactIconView);
            contactSettingsBtn = itemView.findViewById(R.id.contactSettingsBtn);
        }
    }
}
