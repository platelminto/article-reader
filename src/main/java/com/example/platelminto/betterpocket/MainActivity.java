package com.example.platelminto.betterpocket;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chimbori.crux.articles.ArticleExtractor;
import com.example.platelminto.betterpocket.databinding.MainActivityBinding;

import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding mainActivityBinding;
    private RecyclerView.LayoutManager layoutManager;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("Saved Articles");
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        List<Article> articles = Util.getArticleList();

        layoutManager = new LinearLayoutManager(this);
        mainActivityBinding.recyclerView.setLayoutManager(layoutManager);

        listAdapter = new ListAdapter(articles);
        mainActivityBinding.recyclerView.setAdapter(listAdapter);

        mainActivityBinding.insertFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listAdapter.addArticle(Util.getRandomArticle());
                layoutManager.scrollToPosition(0);
            }
        });

        final String rawHTML = rawHTML(getApplicationContext());

        com.chimbori.crux.articles.Article article = ArticleExtractor.with(
                "https://www.wired.com/story/bitcoin-will-burn-planet-down-how-fast/",
                rawHTML).extractContent().extractMetadata().article();
        }

    private static String rawHTML(Context context) {

        final StringBuilder html = new StringBuilder();

        try(Scanner scan = new Scanner(context.getResources().openRawResource(R.raw.raw_html))) {
            while(scan.hasNextLine()) {
                html.append(scan.nextLine()).append("\n");
            }
        }

        return html.toString();
    }
}
