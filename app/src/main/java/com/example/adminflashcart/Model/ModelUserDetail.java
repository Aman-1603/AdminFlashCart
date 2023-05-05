package com.example.adminflashcart.Model;

public class ModelUserDetail {

    String name,email,Phone,city,Online;

    public ModelUserDetail(){

    }

    public ModelUserDetail(String name, String email, String phone, String citym, String online) {
        this.name = name;
        this.email = email;
        Phone = phone;
        this.city = citym;
        Online = online;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCitym(String city) {
        this.city = city;
    }

    public String getOnline() {
        return Online;
    }

    public void setOnline(String online) {
        Online = online;
    }
}
