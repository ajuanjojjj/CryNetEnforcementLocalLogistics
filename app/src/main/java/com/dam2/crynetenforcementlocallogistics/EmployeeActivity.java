package com.dam2.crynetenforcementlocallogistics;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {
    private final List<Employee> employees = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_employee);
        new RetrieveFeedTask().execute(employees);
    }

    private void mostrarDatos(final int emplNumber) {
        Employee empleado = employees.get(emplNumber % employees.size());
        TextView txt_zone = findViewById(R.id.txt_zone);
        TextView txt_empNo = findViewById(R.id.txt_empNo);
        TextView txt_name = findViewById(R.id.txt_title);
        TextView txt_rank = findViewById(R.id.txt_rank);
        TextView txt_city = findViewById(R.id.txt_location);
        TextView txt_payGrade = findViewById(R.id.txt_paygrade);
        ImageView fotoEmpl = findViewById(R.id.img_fotoEmpleado);
        ImageView iconDept = findViewById(R.id.img_dept);
        Button btnNextEmpl = findViewById(R.id.btn_next);

        txt_zone.setText(empleado.getWorkZone());
        txt_empNo.setText(empleado.getId());
        String nombre = empleado.getFistName() + " " + empleado.getLastName();
        txt_name.setText(nombre);
        txt_rank.setText(empleado.getRank());
        txt_city.setText(empleado.getCity());
        txt_payGrade.setText(empleado.getPayGrade());
        fotoEmpl.setImageBitmap(empleado.getPhoto().getMapa());
        iconDept.setImageBitmap(empleado.getDepartament().getPhoto().getMapa());
        btnNextEmpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatos(1 + emplNumber);
            }
        });
    }

    @SuppressWarnings("StaticFieldLeak") // No es relevante en esta situacion: stackoverflow.com/a/46166223
    private class RetrieveFeedTask extends AsyncTask<List<Employee>, Void, List<Employee>> {
        private final static String URI = "http://orpheon.es/juanjo/webservice.php";
        ProgressDialog progDailog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog = new ProgressDialog(EmployeeActivity.this);
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        @SafeVarargs
        protected final List<Employee> doInBackground(List<Employee>... params) {
            try {
                URL url = new URL(URI);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    urlConnection.setDoOutput(true);
                    urlConnection.setChunkedStreamingMode(0);
/*
                    OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                    writeStream(out);
*/
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    return readStream(params[0], in);

                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception ex) {
                Log.e("ROMPEDURA", ex.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Employee> unused) {
            super.onPostExecute(unused);
            progDailog.dismiss();
        }

        private List<Employee> readStream(List<Employee> employeeList, InputStream in) throws IOException {
            JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            reader.beginArray();
            while (reader.hasNext()) {
                employeeList.add(Employee.parseJson(reader));
            }
            reader.endArray();
            return employeeList;
        }
    }
}