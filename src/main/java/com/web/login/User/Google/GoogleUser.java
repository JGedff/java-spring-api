package com.web.login.User.Google;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class GoogleUser {
    private String id;
    private String user;
    private String name;
    private String surname;
    private String role;
    private String country;

    public GoogleUser(String id, String user, String name, String surname, String country, String role) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.role = role;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
