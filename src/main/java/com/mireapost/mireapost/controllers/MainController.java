package com.mireapost.mireapost.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mireapost.mireapost.tables.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.DateFormat;
import java.util.*;

@Controller
public class MainController {
    public Connection conn;
    public Statement statmt;
    public ResultSet resSet;
    public String Auth = "";
    public Integer id_user = -1;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Post.");
        model.addAttribute("auth", Auth);
        return "index";
    }

    @GetMapping("/posts")
    public String posts(Model model) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:./blogs.sqlite");
        statmt = conn.createStatement();
        resSet = statmt.executeQuery("SELECT * FROM news");
        List<News> list = new ArrayList<News>();
        while (resSet.next()) {
            Integer id = resSet.getInt("id");
            String title = resSet.getString("title");
            String content = resSet.getString("content");
            String created_date = resSet.getString("created_date");
            Boolean is_private = resSet.getBoolean("is_private");
            Integer user_id = resSet.getInt("user_id");
            News x = new News(id, title, content, created_date, is_private, user_id);
            list.add(x);
        }
        model.addAttribute("title", "Post.");
        model.addAttribute("auth", Auth);
        model.addAttribute("id_user", id_user);
        model.addAttribute("list1", list);
        conn.close();
        return "posts";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Post.");
        model.addAttribute("auth", Auth);
        model.addAttribute("error", false);
        return "login";
    }

    @PostMapping("/login")
    public String logining(String email, String password, Model model) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:./blogs.sqlite");
        statmt = conn.createStatement();
        resSet = statmt.executeQuery("SELECT * FROM users WHERE (email = \"" + email + "\") AND (password = \"" + password + "\")");
        if (!resSet.next()) {
            model.addAttribute("title", "Post.");
            model.addAttribute("auth", Auth);
            model.addAttribute("error", true);
            return "login";
        }
        Auth = resSet.getString("name");
        id_user = resSet.getInt("id");
        model.addAttribute("title", "Post.");
        model.addAttribute("auth", Auth);
        conn.close();
        return "index";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        Auth = "";
        id_user = -1;
        model.addAttribute("title", "Post.");
        model.addAttribute("auth", Auth);
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Post.");
        model.addAttribute("auth", Auth);
        model.addAttribute("error", false);
        return "register";
    }

    @PostMapping("/register")
    public String reg(String email, String password, String password_again, String name, String about, Model model) throws ClassNotFoundException, SQLException {
        if (!Objects.equals(password, password_again)) {
            model.addAttribute("title", "Post.");
            model.addAttribute("auth", Auth);
            model.addAttribute("error", true);
            return "register";
        }
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:./blogs.sqlite");
        statmt = conn.createStatement();
        Locale local = new Locale("ru", "RU");
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, local);
        Date currentDate = new Date();
        statmt.execute("INSERT INTO users (name, about, email, password, created_date) VALUES ('" + name + "', '" + about + "', '" + email + "', '" + password + "', '" + df.format(currentDate) + "'); ");
        model.addAttribute("title", "Post.");
        model.addAttribute("auth", Auth);
        model.addAttribute("error", false);
        conn.close();
        return "login";
    }

    @GetMapping("/news")
    public String news(Model model) {
        model.addAttribute("title", "Post.");
        model.addAttribute("title_page", "Добавление поста");
        model.addAttribute("auth", Auth);
        model.addAttribute("error", false);
        return "news";
    }

    @PostMapping("/news")
    public String add_news(String title_news, String content, Boolean is_private, Model model) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:./blogs.sqlite");
        statmt = conn.createStatement();
        Locale local = new Locale("ru", "RU");
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, local);
        Date currentDate = new Date();
        int pr;
        if (is_private != null) {
            pr = 1;
        } else {
            pr = 0;
        }
        statmt.execute("INSERT INTO news (title, content, created_date, is_private, user_id) VALUES ('" + title_news + "', '" + content + "', '" + df.format(currentDate) + "', '" + pr + "', " + id_user + "); ");
        conn.close();
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:./blogs.sqlite");
        statmt = conn.createStatement();
        resSet = statmt.executeQuery("SELECT * FROM news");
        List<News> list = new ArrayList<News>();
        while (resSet.next()) {
            Integer id = resSet.getInt("id");
            String title = resSet.getString("title");
            String contents = resSet.getString("content");
            String created_date = resSet.getString("created_date");
            Boolean is_privates = resSet.getBoolean("is_private");
            Integer user_id = resSet.getInt("user_id");
            News x = new News(id, title, contents, created_date, is_privates, user_id);
            list.add(x);
        }
        model.addAttribute("title", "Post.");
        model.addAttribute("auth", Auth);
        model.addAttribute("id_user", id_user);
        model.addAttribute("list1", list);
        return "posts";
    }

    @GetMapping("/news_delete")
    public String del_news(Integer orderId, Model model) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:./blogs.sqlite");
        statmt = conn.createStatement();
        statmt.execute("DELETE from news WHERE id = " + orderId);
        conn.close();
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:./blogs.sqlite");
        statmt = conn.createStatement();
        resSet = statmt.executeQuery("SELECT * FROM news");
        List<News> list = new ArrayList<News>();
        while (resSet.next()) {
            Integer id = resSet.getInt("id");
            String title = resSet.getString("title");
            String contents = resSet.getString("content");
            String created_date = resSet.getString("created_date");
            Boolean is_privates = resSet.getBoolean("is_private");
            Integer user_id = resSet.getInt("user_id");
            News x = new News(id, title, contents, created_date, is_privates, user_id);
            list.add(x);
        }
        model.addAttribute("title", "Post.");
        model.addAttribute("auth", Auth);
        model.addAttribute("id_user", id_user);
        model.addAttribute("list1", list);
        return "posts";
    }

    @GetMapping("/profile")
    public String profile(Integer orderId, Model model) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:./blogs.sqlite");
        statmt = conn.createStatement();
        resSet = statmt.executeQuery("SELECT * FROM news WHERE user_id = " + orderId);
        List<News> list = new ArrayList<News>();
        while (resSet.next()) {
            Integer id = resSet.getInt("id");
            String title = resSet.getString("title");
            String content = resSet.getString("content");
            String created_date = resSet.getString("created_date");
            Boolean is_private = resSet.getBoolean("is_private");
            Integer user_id = resSet.getInt("user_id");
            News x = new News(id, title, content, created_date, is_private, user_id);
            list.add(x);
        }
        conn.close();
        conn = DriverManager.getConnection("jdbc:sqlite:./blogs.sqlite");
        statmt = conn.createStatement();
        resSet = statmt.executeQuery("SELECT * FROM users WHERE id = " + orderId);
        Integer id = resSet.getInt("id");
        String name = resSet.getString("name");
        String about = resSet.getString("about");
        String email = resSet.getString("email");
        String password = resSet.getString("password");
        String created_date = resSet.getString("created_date");
        Users x = new Users(id, name, about, email, password, created_date);
        conn.close();
        model.addAttribute("title", "Post.");
        model.addAttribute("auth", Auth);
        model.addAttribute("id_user", id_user);
        model.addAttribute("list1", list);
        model.addAttribute("user", x);
        return "profile";
    }
}
