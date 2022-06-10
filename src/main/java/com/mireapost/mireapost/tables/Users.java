package com.mireapost.mireapost.tables;

public class Users {
    private Integer id;
    private String name;
    private String about;
    private String email;
    private String password;
    private String create_date;

    public Users(int id, String name, String about, String email, String password, String create_date) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.email = email;
        this.password = password;
        this.create_date = create_date;
    }

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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}
