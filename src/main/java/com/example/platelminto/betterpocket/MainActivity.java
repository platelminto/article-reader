package com.example.platelminto.betterpocket;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Environment;
import android.os.Looper;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    ArticleHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("Saved Articles");
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        layoutManager = new LinearLayoutManager(this);
        mainActivityBinding.recyclerView.setLayoutManager(layoutManager);

        FileUtil.articleStorage = FileUtil.getPrivateStorageDir(this, "articles");

        listAdapter = new ListAdapter(FileUtil.getArticlesFromStorage());
        mainActivityBinding.recyclerView.setAdapter(listAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ArticleTouchCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, listAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mainActivityBinding.recyclerView);

        mHandler = new ArticleHandler(Looper.getMainLooper(), listAdapter, layoutManager);
    }

    public void doTheDownload(View v) {

        DownloadTask.url = "https://www.wired.com/story/bitcoin-will-burn-planet-down-how-fast/";

        Intent i = new Intent(this, DownloadTask.class);
        i.putExtra(DownloadTask.EXTRA_MESSENGER, new Messenger(mHandler));
        startService(i);
    }

    public void openArticle(View view) {

        Intent intent = new Intent(this, ArticleActivity.class);
        final int itemPosition = mainActivityBinding.recyclerView.getChildLayoutPosition(view);

        final Article article = listAdapter.getArticles().get(itemPosition);

        intent.putExtra(EXTRA_MESSAGE, article);
        startActivity(intent);
    }
}
