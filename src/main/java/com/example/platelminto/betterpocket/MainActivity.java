package com.example.platelminto.betterpocket;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Looper;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.platelminto.betterpocket.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.platelminto.betterpocket.MESSAGE";
    protected MainActivityBinding mainActivityBinding;
    protected RecyclerView.LayoutManager layoutManager;
    protected ListAdapter listAdapter;
    ArticleHandler articleHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("Saved Articles");
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        layoutManager = new LinearLayoutManager(this);
        mainActivityBinding.recyclerView.setLayoutManager(layoutManager);

        // Needs to be set here to get the general private storage directory for the entire app
        FileUtil.articleStorage = FileUtil.getPrivateStorageDir(this, "articles");

        listAdapter = new ListAdapter(FileUtil.getArticlesFromStorage());
        mainActivityBinding.recyclerView.setAdapter(listAdapter);

        // Disables dragging of article cards, and only allows swiping left
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ArticleTouchCallback(0, ItemTouchHelper.LEFT, listAdapter);

        // Attaches the swiping-ability to the recyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mainActivityBinding.recyclerView);

        // Initialises the handler for newly downloaded articles
        articleHandler = new ArticleHandler(Looper.getMainLooper(), listAdapter, layoutManager);

        downloadURLFromSharing();
    }

    // Handles sharing a url from a browser to the app
    private void downloadURLFromSharing() {

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        // If the data was sent to the app, and it was plain text, download that url
        if(action.equals(Intent.ACTION_SEND) && type != null && type.equals("text/plain")) {
            String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
            if(sharedText != null) {

                downloadURL(sharedText);
            }
        }
    }

    // Starts downloading an article in the background
    public void downloadURL(String url) {

        DownloadTask.url = url;

        Intent i = new Intent(this, DownloadTask.class);
        i.putExtra(DownloadTask.EXTRA_MESSENGER, new Messenger(articleHandler));
        startService(i);
    }

    public void openArticle(View view) {

        Intent intent = new Intent(this, ArticleActivity.class);
        final int itemPosition = mainActivityBinding.recyclerView.getChildLayoutPosition(view);

        final Article article = listAdapter.getArticles().get(itemPosition);

        // Opens a new activity, passing through the article object
        intent.putExtra(EXTRA_MESSAGE, article);
        startActivity(intent);
    }
}
