package com.dam2.crynetenforcementlocallogistics;

import android.util.JsonReader;

import java.io.IOException;
import java.io.Serializable;

public class Departament implements Serializable {
    private static final String DEPT_ID = "id";
    private static final String DEPT_NAME = "name";
    private static final String DEPT_DESC = "description";
    private static final String DEPT_PHOTO = "photo";

    private int id;
    private String name;
    private String description;
    private SerializableBitmap photo;

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public SerializableBitmap getPhoto() {
        return photo;
    }

    private void setPhoto(SerializableBitmap photo) {
        this.photo = photo;
    }

    public static Departament parseJson(JsonReader json) {
        Departament dept = new Departament();
        try {
            while(json.hasNext()){
                switch (json.nextName()) {
                    case DEPT_ID:
                        dept.setId(json.nextInt());
                        break;
                    case DEPT_NAME:
                        dept.setName(json.nextString());
                        break;
                    case DEPT_DESC:
                        dept.setDescription(json.nextString());
                        break;
                    case DEPT_PHOTO:
                        String photo = json.nextString();
                        dept.setPhoto(SerializableBitmap.parseBase64(photo));
                        break;
                }
            }
            return dept;
        } catch (IOException ex){
            return null;
        }
    }
}
