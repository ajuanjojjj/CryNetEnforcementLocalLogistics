package com.dam2.crynetenforcementlocallogistics;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MailActivity extends AppCompatActivity {
    private List<Mail> mailsInbox = new ArrayList<>();
    private List<Mail> mailsSent = new ArrayList<>();
    private List<Task> tasks;
    private List<GPU> gpus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mails);

        fillInbox();
        fillUsers();

        final TextView lblInbox = findViewById(R.id.lbl_inbox);
        final TextView lblSent = findViewById(R.id.lbl_sent);
        final TextView lblTasks = findViewById(R.id.lbl_tasks);
        final TextView lblGPUs = findViewById(R.id.lbl_gpus);

        lblInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lblInbox.setBackgroundResource(R.color.crynetTranslucidWhite);
                lblSent.setBackgroundResource(0);
                lblTasks.setBackgroundResource(0);
                lblGPUs.setBackgroundResource(0);
                fillInbox();
            }
        });

        lblSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lblInbox.setBackgroundResource(0);
                lblSent.setBackgroundResource(R.color.crynetTranslucidWhite);
                lblTasks.setBackgroundResource(0);
                lblGPUs.setBackgroundResource(0);
                fillSent();
            }
        });

        lblTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lblInbox.setBackgroundResource(0);
                lblSent.setBackgroundResource(0);
                lblTasks.setBackgroundResource(R.color.crynetTranslucidWhite);
                lblGPUs.setBackgroundResource(0);
                fillTasks();
            }
        });

        lblGPUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lblInbox.setBackgroundResource(0);
                lblSent.setBackgroundResource(0);
                lblTasks.setBackgroundResource(0);
                lblGPUs.setBackgroundResource(R.color.crynetTranslucidWhite);
                fillGPUs();
            }
        });
    }

    private void fillUsers(){
        RecyclerView rvUsers = findViewById(R.id.ryv_users);

        // Initialize mails_inbox
        List<User> users = createUsersList(8);

        // Create adapter passing in the sample user data
        MailsAdapterUser adapterUsers = new MailsAdapterUser(users);


        // Attach the adapter to the recyclerview to populate items
        rvUsers.setAdapter(adapterUsers);

        // Set layout manager to position the items
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Crea una lista con los diferentes objetos contact
     */
    private void fillInbox() {
        RecyclerView rvMails = findViewById(R.id.ryv_mails);

        MailsAdapterMail adapterMails = new MailsAdapterMail(mailsInbox, this);

        try {
            new RetrieveFeedTask("MailsInbox", adapterMails).execute(mailsInbox);
        } catch (Exception e){
            e.printStackTrace();
        }

        rvMails.setAdapter(adapterMails);

        rvMails.setLayoutManager(new LinearLayoutManager(this));
    }


    /**
     * Crea una lista con los diferentes objetos contact
     */
    private void fillSent() {
        RecyclerView rvMails = findViewById(R.id.ryv_mails);

        MailsAdapterMail adapterMails = new MailsAdapterMail(mailsSent, this);

        try {
            new RetrieveFeedTask("MailsSent", adapterMails).execute(mailsSent);
        } catch (Exception e){
            e.printStackTrace();
        }
        rvMails.setAdapter(adapterMails);

        rvMails.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Crea una lista con los diferentes objetos contact
     */
    private void fillTasks() {
        RecyclerView rvMails = findViewById(R.id.ryv_mails);


        try {
            tasks = Task.parse(getResources().openRawResource(R.raw.tasks));
        } catch (Exception e){
            e.printStackTrace();
        }
        MailsAdapterTasks adapterTasks = new MailsAdapterTasks(tasks);

        rvMails.setAdapter(adapterTasks);

        rvMails.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Crea una lista con los diferentes objetos contact
     */
    private void fillGPUs() {
        RecyclerView rvMails = findViewById(R.id.ryv_mails);

        MailsAdapterGPU adapterGPUs = new MailsAdapterGPU(gpus, this);

        try {
            new RetrieveFeedTask("GPUs", adapterGPUs).execute(gpus);
        } catch (Exception e){
            e.printStackTrace();
        }

        rvMails.setAdapter(adapterGPUs);

        rvMails.setLayoutManager(new LinearLayoutManager(this));
    }



    /**
     * Crea una lista con los diferentes objetos user
     * @param numContacts Numero de contactos
     * @return Arraylist de contactos
     */
    @SuppressWarnings("SameParameterValue")
    private static ArrayList<User> createUsersList(int numContacts) {
        ArrayList<User> users = new ArrayList<>();

        for (int i = 1; i <= numContacts; i++) {
            User user = new User();
            user.setOnline(new Random().nextBoolean());
            user.setUsername("USER " + i);  //NON-NLS
            users.add(user);
            //TODO leer usuarios de algun lado?
        }

        return users;
    }

    @SuppressWarnings("StaticFieldLeak") // No es relevante en esta situacion: stackoverflow.com/a/46166223
    private class RetrieveFeedTask extends AsyncTask<List, Void, List> {
        private final static String URI = "http://crynet.wunderapp.es/servicios/get";
        RecyclerView.Adapter adapter;
        private String uri;
        ProgressDialog progDailog;

        RetrieveFeedTask(String target, RecyclerView.Adapter adapter){
            this.adapter = adapter;
            uri = target;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog = new ProgressDialog(MailActivity.this);
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        protected final List doInBackground(List... params) {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(URI + uri);
                urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type", "application/" + "json");
                    urlConnection.setRequestProperty("Authorization", "Bearer " + App.token);
                    //urlConnection.setDoOutput(true);
/*
                    OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                    writeStream(out);
*/
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    readStream(params[0], in);

                    return params[0];
                } catch (FileNotFoundException ex){
                    String val = urlConnection.getResponseMessage();
                    Log.e("ROMPEDURA", val);
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception ex) {
                Log.e("ROMPEDURA", ex.getMessage() + ": " + ex.getStackTrace()[1]);

            }
            return null;
        }

        @Override
        protected void onPostExecute(List unused) {
            super.onPostExecute(unused);
            progDailog.dismiss();
            adapter.notifyDataSetChanged();
        }

        @SuppressWarnings("unchecked")
        private void readStream(List lista, InputStream in) throws IOException {
            JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            lista.clear();

            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();

                if (uri.equals("GPUs"))
                    lista.add(GPU.parseJson(reader));
                else
                    lista.add(Mail.parseJson(reader));

                reader.endObject();
            }
            reader.endArray();
        }
    }
}
