package com.itechart.projects.contactDirectory.model.entity;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Contact extends Entity{

    private static final long serialVersionUID = 1L;
    private String name;
    private String surname;
    private String patronymic;
    private Date dob;
    private EnumGender gender;
    private String nationality;
    private EnumFamilyState familyState;
    private String webSite;
    private String job;
    private String country;
    private String city;
    private String streetHouseRoom;
    private String indexNumber;
    private String email;
    private String photoUrl;

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }
    
    public Contact() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public EnumGender getGender() {
        return gender;
    }

    public void setGender(EnumGender gender) {
        this.gender = gender;
    }

    public EnumFamilyState getFamilyState() {
        return familyState;
    }

    public void setFamilyState(EnumFamilyState familyState) {
        this.familyState = familyState;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetHouseRoom() {
        return streetHouseRoom;
    }

    public void setStreetHouseRoom(String streetHouseRoom) {
        this.streetHouseRoom = streetHouseRoom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "Contact{" + "idContact=" + super.getId() + ", name=" + name + ", surname=" + surname + ", patronymic=" + patronymic + ", dob=" + dob + ", gender=" + gender + ", nationality=" + nationality + ", familyState=" + familyState + ", webSite=" + webSite + ", job=" + job + ", country=" + country + ", city=" + city + ", streetHouseRoom=" + streetHouseRoom + ", email=" + email + '}';
    }
    
    
}
