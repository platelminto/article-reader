package com.example.platelminto.betterpocket;

import android.media.Image;

public class Article {

    private String title;
    private String html;
    private String company;
    private Image thumbnail;

    public Article(String url) {

        // TODO: set current object to a new Article with private constructor
    }

    public Article(String title, String html, String company, Image thumbnail) {

        this.title = title;
        this.html = html;
        this.company = company;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getHtml() {
        return html;
    }

    public String getCompany() {
        return company;
    }

    public Image getThumbnail() {
        return thumbnail;
    }
}