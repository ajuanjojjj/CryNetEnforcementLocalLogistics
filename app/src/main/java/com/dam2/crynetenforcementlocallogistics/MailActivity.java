package com.dam2.crynetenforcementlocallogistics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MailActivity extends AppCompatActivity {
    private List<Mail> mailsInbox;
    private List<Mail> mailsSent;
    private List<Task> tasks;
    private List<GPU> gpus;

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

        try {
            mailsInbox = Mail.parse(getResources().openRawResource(R.raw.mails_inbox), getResources());
        } catch (Exception e){
            e.printStackTrace();
        }

        MailsAdapterMail adapterMails = new MailsAdapterMail(mailsInbox, this);

        rvMails.setAdapter(adapterMails);

        rvMails.setLayoutManager(new LinearLayoutManager(this));
    }


    /**
     * Crea una lista con los diferentes objetos contact
     */
    private void fillSent() {
        RecyclerView rvMails = findViewById(R.id.ryv_mails);

        try {
            mailsSent = Mail.parse(getResources().openRawResource(R.raw.mails_sent), getResources());
        } catch (Exception e){
            e.printStackTrace();
        }
        MailsAdapterMail adapterMails = new MailsAdapterMail(mailsSent, this);

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

        try {
            gpus = GPU.parse(getResources().openRawResource(R.raw.gpus));
        } catch (Exception e){
            e.printStackTrace();
        }

        MailsAdapterGPU adapterGPUs = new MailsAdapterGPU(gpus, this);

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
}
