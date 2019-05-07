package com.dam2.crynetenforcementlocallogistics;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Transforma el xml de entry_contact a codigo java
 */
public class MailsViewHolderMail extends RecyclerView.ViewHolder {
    public final TextView txt_Cabecera;
    public final TextView txt_Cuerpo;


    public MailsViewHolderMail(View itemView) {
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
        super(itemView);

        txt_Cabecera = itemView.findViewById(R.id.textView6);
        txt_Cuerpo = itemView.findViewById(R.id.textView7);
        }
}
