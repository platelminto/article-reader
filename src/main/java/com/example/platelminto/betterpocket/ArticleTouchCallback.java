package com.example.platelminto.betterpocket;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.io.File;

// To handle swiping of the article cards
public class ArticleTouchCallback extends ItemTouchHelper.SimpleCallback {

    private ListAdapter listAdapter;

    public ArticleTouchCallback(int dragDirs, int swipeDirs, ListAdapter listAdapter) {

        super(dragDirs, swipeDirs);

        this.listAdapter = listAdapter;
    }

    // For dragging, ignored
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    // Removes a file from the list & deletes it from storage when swiping left
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

        if(swipeDir == ItemTouchHelper.LEFT) {
            final int position = viewHolder.getAdapterPosition();
            listAdapter.notifyItemRemoved(position);
            deleteFile(listAdapter.getArticles().remove(position));
        }
    }

    private void deleteFile(Article article) {

        File articleFile = FileUtil.getArticleFile(article);
        articleFile.delete();
    }
}
