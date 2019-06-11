package com.dam2.crynetenforcementlocallogistics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ReportActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }

    public void enviarMensaje(View vista){
        TextView summary = findViewById(R.id.txt_Report);
        summary.setText("");
        Spinner spn = findViewById(R.id.spn_Threat);
        spn.setSelection(0);
        Toast.makeText(this, "Message Successfully Sent", Toast.LENGTH_LONG).show();

    }
}
