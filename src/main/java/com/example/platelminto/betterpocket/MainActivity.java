package com.example.platelminto.betterpocket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chimbori.crux.articles.ArticleExtractor;
import com.example.platelminto.betterpocket.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.platelminto.betterpocket.MESSAGE";
    protected MainActivityBinding mainActivityBinding;
    protected RecyclerView.LayoutManager layoutManager;
    protected ListAdapter listAdapter;
    WebView browser;
    private String url;

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

        setBrowserUp();

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

    // Starts downloading an article
    public void downloadURL(String url) {

        this.url = url;
        browser.loadUrl(url);
    }

    // Opens the article view
    public void openArticle(View view) {

        final Intent intent = new Intent(this, ArticleActivity.class);
        final int itemPosition = mainActivityBinding.recyclerView.getChildLayoutPosition(view);

        final Article article = listAdapter.getArticles().get(itemPosition);

        // Opens a new activity, passing through the article object
        intent.putExtra(EXTRA_MESSAGE, article);
        startActivity(intent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setBrowserUp() {

        browser = new WebView(getApplicationContext());

        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setDomStorageEnabled(true);

        /* Register a new JavaScript interface called HTMLOUT */
        MyJavaScriptInterface myInterface = new MyJavaScriptInterface();
        browser.addJavascriptInterface(myInterface, "HTMLOUT");

        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                browser.loadUrl("javascript:HTMLOUT.showHTML" +
                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
            }
        });
    }

    // Get a Crux article object from a given url and its html
    private com.chimbori.crux.articles.Article getCruxArticle(String url, String html) {

        com.chimbori.crux.articles.Article article;

        // article now becomes a Crux article object with metadata & content extracted
        try {
            article = ArticleExtractor.with(
                    url,
                    html).extractContent().extractMetadata().article();
        } catch (IllegalArgumentException e) {
            return null;
        }

        return article;
    }

    // An instance of this class will be registered as a JavaScript interface
    class MyJavaScriptInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void showHTML(String html) {

            System.out.println("yooo");

            com.chimbori.crux.articles.Article article = getCruxArticle(url, html);

            final Article articleObj = new Article(
                    article.title, article.document.html(), article.siteName, article, null);
            articleObj.setId(articleObj.hashCode());
            FileUtil.writeObjectToFile(articleObj);

            for (int i = 0; i < article.images.size(); i++) {

                System.out.println("HEL:" + article.images.get(i));
            }

            System.out.println("THIS IS US: " + html);

            listAdapter.addArticle(articleObj);
            layoutManager.scrollToPosition(0);
        }
    }
}