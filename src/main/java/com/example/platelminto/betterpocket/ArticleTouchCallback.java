package com.example.platelminto.betterpocket;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.io.File;

public class ArticleTouchCallback extends ItemTouchHelper.SimpleCallback {

    private ListAdapter listAdapter;

    public ArticleTouchCallback(int dragDirs, int swipeDirs, ListAdapter listAdapter) {
        super(dragDirs, swipeDirs);

        this.listAdapter = listAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        if(swipeDir == ItemTouchHelper.LEFT) {
            final int position = viewHolder.getAdapterPosition();
            listAdapter.notifyItemRemoved(position);
            deleteFile(listAdapter.getArticles().remove(position));
        }
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        int dragFlags = 0; // whatever your dragFlags need to be
        int swipeFlags = ItemTouchHelper.LEFT;

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    private void deleteFile(Article article) {

        File articleFile = FileUtil.getArticleFile(article);
        articleFile.delete();
    }
}
