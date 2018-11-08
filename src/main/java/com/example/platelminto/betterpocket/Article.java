package com.example.platelminto.betterpocket;

import android.content.Context;

import com.chimbori.crux.articles.ArticleExtractor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Article {

    private String name;
    private String surname;
    private String role;
    private String description;

    public Article(String name, String surname, String role, String description) {

        this.name = name;
        this.surname = surname;
        this.role = role;
        this.description = description;
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

    public String getFullName() {
        return name + " " + surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}