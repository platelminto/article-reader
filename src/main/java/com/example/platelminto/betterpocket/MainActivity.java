package com.example.platelminto.betterpocket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.chimbori.crux.articles.ArticleExtractor;
import com.example.platelminto.betterpocket.databinding.MainActivityBinding;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.platelminto.betterpocket.MESSAGE";
    protected MainActivityBinding mainActivityBinding;
    protected RecyclerView.LayoutManager layoutManager;
    protected ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("Saved Articles");
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        layoutManager = new LinearLayoutManager(this);
        mainActivityBinding.recyclerView.setLayoutManager(layoutManager);
        // TODO: Fix the layout to have correctly resized text boxes

        // Needs to be set here to get the general private storage directory for the entire app
        FileUtil.articleStorage = FileUtil.getPrivateStorageDir(this, "articles");

        listAdapter = new ListAdapter(FileUtil.getArticlesFromStorage());
        mainActivityBinding.recyclerView.setAdapter(listAdapter);

        // Disables dragging of article cards, and only allows swiping left
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ArticleTouchCallback(0, ItemTouchHelper.LEFT, listAdapter);

        // Attaches the swiping ability to the recyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mainActivityBinding.recyclerView);

        downloadURLFromSharing();
    }

    // Handles sharing a url from a browser to the app
    private void downloadURLFromSharing() {

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        // If the data was sent to the app, and it was plain text, download that url
        if (Intent.ACTION_SEND.equals(action) && type != null && type.equals("text/plain")) {
            String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (sharedText != null) {
                downloadURL(sharedText);
                // Needed to avoid redownloading the same article when recalling onCreate(),
                // which happens, for example, when rotating the screen
                setIntent(new Intent(this, MainActivity.class));
            }
        }
    }

    // Starts downloading an article in the background
    public void downloadURL(String url) {

        new FetchFeedTask().execute(url);
    }

    // Opens the article view
    public void openArticle(View view) {

        Intent intent = new Intent(this, ArticleActivity.class);
        final int itemPosition = mainActivityBinding.recyclerView.getChildLayoutPosition(view);

        final Article article = listAdapter.getArticles().get(itemPosition);

        // Opens a new activity, passing through the article object
        intent.putExtra(EXTRA_MESSAGE, article);
        startActivity(intent);
    }

    // Background task to download a url's html in the background
    @SuppressLint("StaticFieldLeak")
    private class FetchFeedTask extends AsyncTask<String, Void, com.chimbori.crux.articles.Article> {

        @Override
        protected void onPreExecute() {}

        // Downloads the html in the background and returns a Crux object
        @Override
        protected com.chimbori.crux.articles.Article doInBackground(String... urls) {

            StringBuilder content = new StringBuilder();
            URLConnection connection;

            try {
                connection =  new URL(urls[0]).openConnection();
                Scanner scanner = new Scanner(connection.getInputStream());
                while(scanner.hasNextLine()) {
                    final String s = scanner.nextLine();
                    content.append(s).append("\n");
                }
            } catch (Exception ex ) {
                ex.printStackTrace();
            }

            return getCruxArticle(urls[0], content.toString());
        }

        // Generates an Article object from the Crux object and adds it, then scrolls to the start
        @Override
        protected void onPostExecute(com.chimbori.crux.articles.Article article) {

            final Article articleObj = new Article(
                    article.title, article.document.html(), article.siteName, null);
            articleObj.setId(articleObj.hashCode());
            FileUtil.writeObjectToFile(articleObj);

            listAdapter.addArticle(articleObj);
            layoutManager.scrollToPosition(0);
        }

        // Get a Crux article object from a given url and its html
        private com.chimbori.crux.articles.Article getCruxArticle(String url, String html) {

            com.chimbori.crux.articles.Article article;

            // article now becomes a Crux article object with metadata & content extracted
            try {
                article = ArticleExtractor.with(
                        url,
                        html).extractContent().extractMetadata().article();
            } catch(IllegalArgumentException e) {
                return null;
            }

            return article;
        }
    }
}