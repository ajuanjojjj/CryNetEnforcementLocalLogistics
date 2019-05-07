package com.dam2.crynetenforcementlocallogistics;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {
    private final List<Employee> employees = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_employee);
        new RetrieveFeedTask(this).execute(employees);
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


    private class RetrieveFeedTask extends AsyncTask<List<Employee>, Void, List<Employee>> {
        private final static String URI = "http://orpheon.es/juanjo/webservice.php"; //NON-NLS
        static final String WEB_SERVICE = "WebService"; //NON-NLS
        static final String DEPT_ICON = "DeptIcon"; //NON-NLS
        static final String EMPL_PHOTO = "EmplPhoto"; //NON-NLS
        static final String WORK_ZONE = "WorkZone"; //NON-NLS
        private static final String PAY_GRADE = "PayGrade"; //NON-NLS
        private static final String CITY = "City"; //NON-NLS
        private static final String EMPL_RANK = "EmplRank"; //NON-NLS
        private static final String LAST_NAME = "LastName"; //NON-NLS
        private static final String FIRST_NAME = "FirstName"; //NON-NLS
        private static final String EMPL_ID = "EmplID"; //NON-NLS
        private static final String ARRAY_NAME = "cities"; //NON-NLS
        private final EmployeeActivity parent;

        RetrieveFeedTask(EmployeeActivity parent) {
            this.parent = parent;
        }

        @SafeVarargs
        protected final List<Employee> doInBackground(List<Employee>... params) {
            List<Employee> employees = params[0];
            try {
                // Llamamos al servicio web para recuperar los datos
                HttpGet httpGet = new HttpGet(URI);
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                BufferedHttpEntity buffer = new BufferedHttpEntity(entity);
                InputStream iStream = buffer.getContent();

                StringBuilder aux = new StringBuilder();

                BufferedReader r = new BufferedReader(new InputStreamReader(iStream));
                String line;
                while ((line = r.readLine()) != null) {
                    aux.append(line);
                }

                // Parseamos la respuesta obtenida del servidor a un objeto JSON
                JSONObject jsonObject = new JSONObject(aux.toString());
                JSONArray cities = jsonObject.getJSONArray(ARRAY_NAME);

                // Recorremos el array con los elementos cities
                for (int i = 0; i < cities.length(); i++) {
                    JSONObject employee = cities.getJSONObject(i);

                    // Creamos el objeto City
                    Employee e = new Employee();
                    e.setId(employee.getString(EMPL_ID));
                    e.setFistName(employee.getString(FIRST_NAME));
                    e.setLastName(employee.getString(LAST_NAME));
                    e.setRank(employee.getString(EMPL_RANK));
                    e.setCity(employee.getString(CITY));
                    e.setPayGrade(employee.getString(PAY_GRADE));
                    e.setWorkZone(employee.getString(WORK_ZONE));
                    SerializableBitmap foto = SerializableBitmap.parseBase64(employee.getString(EMPL_PHOTO));
                    e.setPhoto(foto);
                    Departament dept = new Departament();
                    dept.setPhoto(SerializableBitmap.parseBase64(employee.getString(DEPT_ICON)));
                    e.setDepartament(dept);

                    // Almacenamos el objeto en el array que hemos creado anteriormente
                    employees.add(e);
                }
                return employees;
            } catch (Exception ex) {
                Log.e(WEB_SERVICE, ex.getMessage());
                Employee e = new Employee();
                e.setId(ex.toString());
                e.setFistName(ex.toString());
                e.setLastName(ex.toString());
                e.setRank(ex.toString());
                e.setCity("");
                e.setPayGrade("");
                e.setWorkZone("");
                e.setPhoto(new SerializableBitmap(Bitmap.createBitmap(10 ,10, Bitmap.Config.ALPHA_8)));
                Departament dept = new Departament();
                dept.setPhoto(new SerializableBitmap(Bitmap.createBitmap(10 ,10, Bitmap.Config.ALPHA_8)));
                e.setDepartament(dept);
                employees.add(e);
            }
            return employees;
        }

        @Override
        protected void onPostExecute(List<Employee> employees) {
            parent.mostrarDatos(0);
        }
    }
}