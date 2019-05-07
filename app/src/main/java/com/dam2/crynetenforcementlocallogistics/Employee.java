package com.dam2.crynetenforcementlocallogistics;

import java.io.Serializable;
import java.util.Date;

class Employee implements Serializable {
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

    public Employee() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Departament getDepartament() {
        return departament;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
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

    public void setPhoto(SerializableBitmap photo) {
        this.photo = photo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPayGrade() {
        return payGrade;
    }

    public void setPayGrade(String payGrade) {
        this.payGrade = payGrade;
    }

    public String getWorkZone() {
        return workZone;
    }

    public void setWorkZone(String workZone) {
        this.workZone = workZone;
    }
}
