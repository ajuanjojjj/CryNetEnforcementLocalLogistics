package com.dam2.crynetenforcementlocallogistics;

import android.util.JsonReader;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

class Employee implements Serializable {
    private static final String EMPL_ID = "id"; //NON-NLS
    private static final String FIRST_NAME = "fistName"; //NON-NLS
    private static final String LAST_NAME = "lastName"; //NON-NLS
    private static final String EMPL_MAIL = "email"; //NON-NLS
    private static final String EMPL_DEPT = "dept"; //NON-NLS
    private static final String EMPL_RANK = "rank"; //NON-NLS
    private static final String EMPL_PHOTO = "photo"; //NON-NLS
    private static final String CITY = "city"; //NON-NLS
    private static final String PAY_GRADE = "payGrade"; //NON-NLS
    private static final String WORK_ZONE = "workZone"; //NON-NLS

    private String id;
    private String fistName;
    private String lastName;
    private String email;
    private Departament departament;
    private String rank;
    private Date birthDate;
    private Date hireDate;
    private SerializableBitmap photo;
    private String city;
    private String payGrade;
    private String workZone;

    private Employee() {
    }

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

    public SerializableBitmap getPhoto() {
        return photo;
    }

    private void setPhoto(SerializableBitmap photo) {
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
                switch (json.nextName()){
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
                        Departament dept = Departament.parseJson(json);
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
                        e.setWorkZone(json.nextString());
                        break;
                    case EMPL_PHOTO:
                        SerializableBitmap foto = SerializableBitmap.parseBase64(json.nextString());
                        e.setPhoto(foto);
                        break;
                }
            }
            return e;
        } catch (IOException ex) {
            return null;
        }
    }
}
