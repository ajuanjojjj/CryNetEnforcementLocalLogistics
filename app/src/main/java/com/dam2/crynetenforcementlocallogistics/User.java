package com.dam2.crynetenforcementlocallogistics;

import android.graphics.Bitmap;

import java.io.Serializable;

public class User implements Serializable {
    private SerializableBitmap icono;
    private String username;
    private boolean online;
    private String mail;

    public String getUsername() {
        return username;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Bitmap getIcono() {
        return icono.getMapa();
    }

    public void setIcono(Bitmap icono) {
        this.icono = new SerializableBitmap(icono);
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
