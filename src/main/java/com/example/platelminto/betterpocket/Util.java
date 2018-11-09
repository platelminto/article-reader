package com.example.platelminto.betterpocket;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {

    public static List<Article> getArticleList() {

        Article article1 = new Article("Hfsd", "Lfre", "tbgfb", null);
        Article article2 = new Article("Hfsd", "Lfre", "tbgfb", null);
        Article article3 = new Article("Hfsd", "Lfre", "tbgfb", null);
        Article article4 = new Article("Hfsd", "Lfre", "tbgfb", null);
        Article article5 = new Article("Hfsd", "Lfre", "tbgfb", null);
        Article article6 = new Article("Hfsd", "Lfre", "tbgfb", null);
        Article article7 = new Article("Hfsd", "Lfre", "tbgfb", null);
        Article article8 = new Article("Hfsd", "Lfre", "tbgfb", null);
        Article article9 = new Article("Hfsd", "Lfre", "tbgfb", null);
        Article article10 = new Article("I wonde rhow a real question would actually work with words and stuff that can wrap and are recognised and shit yknow", "Lfre", "tbgfb", null);
        Article article11 = new Article("Hfsd", "Lfre", "tbgfb", null);
        Article article12 = new Article("Hfsd", "Lfre", "tbgfb", null);
        Article article13 = new Article("Hfsd", "Lfre", "tbgfb", null);
        Article article14 = new Article("Hfsd", "Lfre", "tbgfb", null);
        Article article15 = new Article("Hfsd", "Lfre", "tbgfb", null);
        Article article16 = new Article("https://www.wired.com/story/bitcoin-will-burn-planet-down-how-fast/");

        return new ArrayList<>(Arrays.asList(article1, article2, article3, article4, article5, article6, article7, article8, article9, article10, article11, article12, article13, article14, article15, article16));
    }

    public static Article getRandomArticle() {

        return new Article("random", "L", "t", null);
    }
}
