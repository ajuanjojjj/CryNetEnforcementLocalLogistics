package com.dam2.crynetenforcementlocallogistics;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class MailsAdapterUser extends RecyclerView.Adapter<MailsViewHolderUser>{
    private final List<User> listaMails;

    public MailsAdapterUser(List<User> users) {
        listaMails = users;
    }



    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public MailsViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.mails_recyclerview_user, parent, false);

        // Return a new holder instance
        return new MailsViewHolderUser(contactView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull MailsViewHolderUser viewHolder, int position) {
        Resources res = viewHolder.itemView.getContext().getResources();

        // Get the data model based on position
        User user = listaMails.get(position);

        // Set item views based on your views and data model
        ImageView img_onlineBubble = viewHolder.img_onlineBubble;
        int idImagen = user.isOnline() ? R.drawable.online : R.drawable.offline;
        img_onlineBubble.setImageDrawable(res.getDrawable(idImagen));

        TextView txt_username = viewHolder.txt_username;
        txt_username.setText(user.getUsername());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return listaMails.size();
    }
}
