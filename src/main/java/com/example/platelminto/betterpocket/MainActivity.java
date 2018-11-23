package com.example.platelminto.betterpocket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
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
    private WebView browser;
    private String url;
    private boolean javascriptDownload = false;

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

        listAdapter = new ListAdapter(this, FileUtil.getArticlesFromStorage());
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
        javascriptDownload = false; // This prevents the raw HTML from being processed as an article
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

    // Set the browser up to use JavaScript
    @SuppressLint("SetJavaScriptEnabled")
    private void setBrowserUp() {

        // Needs to be an instance variable (as opposed to local) or it will be destroyed
        // after leaving the method
        browser = new WebView(getApplicationContext());

        browser.getSettings().setJavaScriptEnabled(true);
        // Some JavaScript needs to access localstorage or the rest of it will crash
        browser.getSettings().setDomStorageEnabled(true);

        MyJavaScriptInterface myInterface = new MyJavaScriptInterface();
        browser.addJavascriptInterface(myInterface, "HTMLOUT");

        // Once the raw HTML is obtained, run it as JavaScript to get the dynamic elements (images)
        browser.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {

                // Some sites (eg: wired) only load images when scrolling by them, so scroll to
                // the bottom before running the JavaScript to actually get them.
                view.scrollTo(0, view.getContentHeight());
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

            if(javascriptDownload) {
                System.out.println(html);

                com.chimbori.crux.articles.Article article = getCruxArticle(url, html);

                final Article articleObj = new Article(
                        article.title, article.document.html(), article.siteName, article, null);
                articleObj.setId(articleObj.hashCode());
                FileUtil.writeObjectToFile(articleObj);

                //System.out.println(article.document.html());

                for (int i = 0; i < article.images.size(); i++) {

                    System.out.println("HEL:" + article.images.get(i));
                }

                listAdapter.addArticle(articleObj);
                runOnUiThread(() -> layoutManager.scrollToPosition(0));
            } else { // Next time, do add the post-JS HTML.
                javascriptDownload = true;
            }
        }
    }
}