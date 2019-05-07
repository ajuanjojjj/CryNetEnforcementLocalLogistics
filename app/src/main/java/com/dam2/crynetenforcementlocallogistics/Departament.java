package com.dam2.crynetenforcementlocallogistics;

import java.io.Serializable;

public class Departament implements Serializable {
    private int id;
    private String name;
    private String description;
    private SerializableBitmap photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SerializableBitmap getPhoto() {
        return photo;
    }

    public void setPhoto(SerializableBitmap photo) {
        this.photo = photo;
    }
}
