package com.dam2.crynetenforcementlocallogistics;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Date;

public class Employee implements Serializable {
    private static final String ID = "id"; //NON-NLS
    private static final String EMPL_ID = "identificador"; //NON-NLS
    private static final String FIRST_NAME = "first_name"; //NON-NLS
    private static final String LAST_NAME = "last_name"; //NON-NLS
    private static final String EMPL_MAIL = "email"; //NON-NLS
    private static final String EMPL_DEPT = "departament"; //NON-NLS
    private static final String EMPL_RANK = "rank"; //NON-NLS
    private static final String EMPL_PHOTO = "photo"; //NON-NLS
    private static final String CITY = "city"; //NON-NLS
    private static final String PAY_GRADE = "paygrade"; //NON-NLS
    private static final String WORK_ZONE = "workzone"; //NON-NLS

    private String id;
    private String fistName;
    private String lastName;
    private String email;
    private Departament departament;
    private String rank;
    private Date birthDate;
    private Date hireDate;
    private String photo;
    private String city;
    private String payGrade;
    private String workZone;

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getFistName() {
        return fistName;
    }

    private void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public Departament getDepartament() {
        return departament;
    }

    private void setDepartament(Departament departament) {
        this.departament = departament;
    }

    public String getRank() {
        return rank;
    }

    private void setRank(String rank) {
        this.rank = rank;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }


    public String getPhoto() {
        return photo;
    }

    private void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCity() {
        return city;
    }

    private void setCity(String city) {
        this.city = city;
    }

    public String getPayGrade() {
        return payGrade;
    }

    private void setPayGrade(String payGrade) {
        this.payGrade = payGrade;
    }

    public String getWorkZone() {
        return workZone;
    }

    private void setWorkZone(String workZone) {
        this.workZone = workZone;
    }

    public static Employee parseJson(JsonReader json){
        try {
            Employee e = new Employee();
            while(json.hasNext()){
                String name = json.nextName();
                switch (name){
                    case EMPL_ID:
                        e.setId(json.nextString());
                        break;
                    case FIRST_NAME:
                        e.setFistName(json.nextString());
                        break;
                    case LAST_NAME:
                        e.setLastName(json.nextString());
                        break;
                    case EMPL_MAIL:
                        e.setEmail(json.nextString());
                        break;
                    case EMPL_DEPT:
                        json.beginObject();
                        Departament dept = Departament.parseJson(json);
                        json.endObject();
                        e.setDepartament(dept);
                        break;
                    case EMPL_RANK:
                        e.setRank(json.nextString());
                        break;
                    case CITY:
                        e.setCity(json.nextString());
                        break;
                    case PAY_GRADE:
                        e.setPayGrade(json.nextString());
                        break;
                    case WORK_ZONE:
                        json.beginObject();
                        json.skipValue();
                        json.skipValue();
                        json.skipValue();
                        e.setWorkZone(json.nextString());
                        json.endObject();
                        break;
                    case EMPL_PHOTO:
                        e.setPhoto(json.nextString());
                        break;
                    default:
                        json.skipValue();
                        break;
                }
            }
            return e;
        } catch (IOException ex) {
            return null;
        }
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private WeakReference<ImageView> bmImage;

        DownloadImageTask(ImageView bmImage) {
            this.bmImage = new WeakReference<>(bmImage);
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                return BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                return null;
            }
        }

        protected void onPostExecute(Bitmap result) {
            if (bmImage!=null){
                if (result == null){
                    Resources res = bmImage.get().getResources();
                    Drawable drawable = res.getDrawable(R.drawable.no_image);
                    bmImage.get().setImageDrawable(drawable);
                }
                else
                    bmImage.get().setImageBitmap(result);
            }
            bmImage = null;
        }
    }
}
