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

        Mail mail = (Mail) Objects.requireNonNull(extras).getSerializable(MAIL);

        Objects.requireNonNull(mail, "Mail era null al sacarlo del bundle");

        ImageView imagen = findViewById(R.id.img_icono_perfil);
        new Employee.DownloadImageTask(imagen).execute(mail.getSender().getPhoto());

        TextView txt_sender = findViewById(R.id.txt_sender);
        String sender = mail.getSender().getFistName() + " (" + mail.getSender().getEmail() + ")";
        txt_sender.setText(sender);

        TextView txt_subject = findViewById(R.id.txt_title);
        txt_subject.setText(mail.getSubject());

        TextView txt_body = findViewById(R.id.txt_body);
        txt_body.setText(mail.getBody());
    }
}
