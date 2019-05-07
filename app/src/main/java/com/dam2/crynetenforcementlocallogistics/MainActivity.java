package com.dam2.crynetenforcementlocallogistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_mail = findViewById(R.id.btn_mail);
        btn_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MailActivity.class);
                startActivity(i);
            }
        });

        Button btn_map = findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(i);
            }
        });

        Button btn_personal = findViewById(R.id.btn_personal);
        btn_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EmployeeActivity.class);
                startActivity(i);
            }
        });

        Button btn_keyPad = findViewById(R.id.btn_keypad);
        btn_keyPad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), KeypadActivity.class);
                startActivity(i);
            }
        });

        Button btn_report = findViewById(R.id.btn_report);
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(i);
            }
        });

        Button btn_wharehouse = findViewById(R.id.btn_wharehouse);
        btn_wharehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.notImplemented, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
