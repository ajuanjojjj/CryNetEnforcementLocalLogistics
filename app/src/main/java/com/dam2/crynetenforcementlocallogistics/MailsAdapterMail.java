package com.dam2.crynetenforcementlocallogistics;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Crea y rellena los viewholder
 */
class MailsAdapterMail extends RecyclerView.Adapter<MailsViewHolderMail> {
    private final List<Mail> listaMails;
    private final AppCompatActivity actividad;

    MailsAdapterMail(List<Mail> contacts, AppCompatActivity actividad) {
        listaMails = contacts;
        this.actividad = actividad;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public MailsViewHolderMail onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.mails_recyclerview_mail, parent, false);

        // Return a new holder instance
        return new MailsViewHolderMail(contactView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull MailsViewHolderMail viewHolder, int position) {
        // Get the data model based on position
        final Mail mail = listaMails.get(position);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(actividad, FocusedMailActivity.class);
                intent.putExtra(FocusedMailActivity.MAIL ,mail);
                //intent.setResult(RESULT_OK, intent);
                actividad.startActivity(intent);
                Toast.makeText(actividad, mail.getSubject(), Toast.LENGTH_SHORT).show();

            }
        });

        // Set item views based on your views and data model
        TextView txt_cabecera = viewHolder.txt_Cabecera;
        txt_cabecera.setText(mail.getSubject());

        TextView txt_cuerpo = viewHolder.txt_Cuerpo;
        txt_cuerpo.setText(mail.getBody());

        viewHolder.getAdapterPosition();
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return listaMails.size();
    }


}