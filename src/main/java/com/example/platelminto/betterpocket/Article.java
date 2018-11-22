package com.example.platelminto.betterpocket;

import android.media.Image;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import java.io.Serializable;

public class Article implements Serializable {

    private String title;
    private String html;
    private String site;
    private int id;
    private transient com.chimbori.crux.articles.Article cruxArticle;
    private Image thumbnail;

    public Article(String title, String html, String site, com.chimbori.crux.articles.Article cruxArticle, Image thumbnail) {

        this.title = title;
        this.html = html;
        this.site = site;
        this.thumbnail = thumbnail;
        this.cruxArticle = cruxArticle;
    }

    private Image getImageFromURL(String url) { // TODO

        return null;
    }

    public Spanned getHtmlObject() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
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

    // Usually set to the HashCode of the object on creation
    public void setId(int id) {

        this.id = id;
    }

    public com.chimbori.crux.articles.Article getCruxArticle() {
        return cruxArticle;
    }
}