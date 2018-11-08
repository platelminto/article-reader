package com.example.platelminto.betterpocket;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {

    public static List<Article> getArticleList() {

        Article article1 = new Article("Hfsd", "Lfre", "tbgfb", "xss");
        Article article2 = new Article("Hfsd", "Lfre", "tbgfb", "xss");
        Article article3 = new Article("Hfsd", "Lfre", "tbgfb", "xss");
        Article article4 = new Article("Hfsd", "Lfre", "tbgfb", "xss");
        Article article5 = new Article("Hfsd", "Lfre", "tbgfb", "xss");
        Article article6 = new Article("Hfsd", "Lfre", "tbgfb", "xss");
        Article article7 = new Article("Hfsd", "Lfre", "tbgfb", "xss");
        Article article8 = new Article("Hfsd", "Lfre", "tbgfb", "xss");
        Article article9 = new Article("Hfsd", "Lfre", "tbgfb", "xss");
        Article article10 = new Article("Hfsd", "Lfre", "tbgfb", "xss");
        Article article11 = new Article("Hfsd", "Lfre", "tbgfb", "xss");
        Article article12 = new Article("Hfsd", "Lfre", "tbgfb", "xss");
        Article article13 = new Article("Hfsd", "Lfre", "tbgfb", "xss");
        Article article14 = new Article("Hfsd", "Lfre", "tbgfb", "xss");
        Article article15 = new Article("Hfsd", "Lfre", "tbgfb", "xss");


        return new ArrayList<>(Arrays.asList(article1, article2, article3, article4, article5, article6, article7, article8, article9, article10, article11, article12, article13, article14, article15));
    }

    public static Article getRandomArticle() {

        return new Article("random", "L", "t", "s");
    }
}
