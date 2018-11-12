package com.example.platelminto.betterpocket;

import android.media.Image;
import android.text.Html;
import android.text.Spanned;

import java.io.Serializable;

public class Article implements Serializable {

    private String title;
    private String html;
    private String site;
    private int id;
    private Image thumbnail;

    public Article(String title, String html, String site, Image thumbnail) {

        this.title = title;
        this.html = html;
        this.site = site;
        this.thumbnail = thumbnail;
    }

    private Image getImageFromURL(String url) { // TODO

        return null;
    }

    public Spanned getHtmlObject() {

        return Html.fromHtml(html);
    }

    public String getTitle() {
        return title;
    }

    public String getHtml() {
        return html;
    }

    public String getSite() {
        return site;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }
}