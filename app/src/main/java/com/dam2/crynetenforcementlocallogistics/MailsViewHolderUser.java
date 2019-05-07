package com.dam2.crynetenforcementlocallogistics;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MailsViewHolderUser extends RecyclerView.ViewHolder{
    public final ImageView img_onlineBubble;
    public final TextView txt_username;

    public MailsViewHolderUser(View itemView) {
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
        super(itemView);

        img_onlineBubble = itemView.findViewById(R.id.img_onlineBubble);
        txt_username = itemView.findViewById(R.id.txt_username);
    }

}