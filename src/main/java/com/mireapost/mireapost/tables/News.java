package com.mireapost.mireapost.tables;

public class News {
    private Integer id;
    private String title;
    private String content;
    private String created_date;
    private Boolean is_private;
    private Integer user_id;

    public News(Integer id, String title, String content, String created_date, Boolean is_private, Integer user_id) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.created_date = created_date;
        this.is_private = is_private;
        this.user_id = user_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public Boolean getIs_private() {
        return is_private;
    }

    public void setIs_private(Boolean is_private) {
        this.is_private = is_private;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
