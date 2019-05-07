package com.dam2.crynetenforcementlocallogistics;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class FocusedMailActivity extends AppCompatActivity {
    public static final String MAIL = "Mail"; //NON-NLS

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();

        setContentView(R.layout.activity_focused_mail);

        Mail mail =  (Mail) extras.getSerializable(MAIL);

        ImageView imagen = findViewById(R.id.img_icono_perfil);
        imagen.setImageBitmap(mail.getRemitente().getIcono());

        TextView txt_sender = findViewById(R.id.txt_sender);
        String sender = mail.getRemitente().getUsername() + " (" + mail.getRemitente().getMail() + ")";
        txt_sender.setText(sender);

        TextView txt_subject = findViewById(R.id.txt_subject);
        txt_subject.setText(mail.getSubject());

        TextView txt_body = findViewById(R.id.txt_body);
        txt_body.setText(mail.getBody());
    }
}
