package com.example.platelminto.betterpocket;

import android.media.Image;
import android.os.AsyncTask;

import com.chimbori.crux.articles.ArticleExtractor;

import java.util.concurrent.ExecutionException;

public class Article {

    private String title;
    private String html;
    private String site;
    private Image thumbnail;

    public Article(String url) {

        AsyncTask<String, Void, String> job = new NetworkUtil().execute(url);

        String html = null;
        try {
            html = job.get(); // TODO get this working in the background
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        com.chimbori.crux.articles.Article article = ArticleExtractor.with(
                url,
                html).extractContent().extractMetadata().article();

        this.title = article.title;
        this.html = article.document.html();
        this.site = article.siteName;
        this.thumbnail = null;
    }

    public Article(String title, String html, String site, Image thumbnail) {

        this.title = title;
        this.html = html;
        this.site = site;
        this.thumbnail = thumbnail;
    }

    private Image getImageFromURL(String url) { // TODO

        return null;
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
}