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
class MailsAdapterGPU extends RecyclerView.Adapter<MailsViewHolderMail> {
    private final List<GPU> listaGPUs;
    private final AppCompatActivity actividad;

    public MailsAdapterGPU(List<GPU> gpus, AppCompatActivity actividad) {
        listaGPUs = gpus;
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
        final GPU gpu = listaGPUs.get(position);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(actividad, FocusedMailActivity.class);
                //intent.putExtra("mail",mail);
                //intent.setResult(RESULT_OK, intent);
                //actividad.startActivity(intent);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, gpu.getUri());
                actividad.startActivity(browserIntent);
                Toast.makeText(actividad, gpu.getUri().toString(), Toast.LENGTH_SHORT).show();

            }
        });

        // Set item views based on your views and data model
        TextView txt_cabecera = viewHolder.txt_Cabecera;
        txt_cabecera.setText(viewHolder.itemView.getResources().getString(R.string.GPUName));

        TextView txt_cuerpo = viewHolder.txt_Cuerpo;
        txt_cuerpo.setText(viewHolder.itemView.getResources().getString(R.string.GPUActivated));

        viewHolder.getAdapterPosition();
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return listaGPUs.size();
    }


}