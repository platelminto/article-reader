package com.example.platelminto.betterpocket;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.RecyclerView;

import com.chimbori.crux.articles.ArticleExtractor;

public class ArticleHandler extends Handler {

    private ListAdapter listAdapter;
    RecyclerView.LayoutManager layoutManager;

    public ArticleHandler(Looper looper, ListAdapter listAdapter, RecyclerView.LayoutManager layoutManager) {

        super(looper);

        this.listAdapter = listAdapter;
        this.layoutManager = layoutManager;
    }

    @Override
    public void handleMessage(Message inputMessage) {

        DownloadTask downloadTask = (DownloadTask) inputMessage.obj;
        String html = downloadTask.html;

        com.chimbori.crux.articles.Article article = null;

        try {
            article = ArticleExtractor.with(
                    DownloadTask.url,
                    html).extractContent().extractMetadata().article();
        } catch(IllegalArgumentException e) {
            return;
        }

        Article articleObj = new Article(article.title, article.document.html(), article.siteName, null);
        articleObj.setId(articleObj.hashCode());
        FileUtil.writeObjectToFile(articleObj);

        listAdapter.addArticle(articleObj);
        layoutManager.scrollToPosition(0);
    }
}
