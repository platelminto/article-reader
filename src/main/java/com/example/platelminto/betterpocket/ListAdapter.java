package com.example.platelminto.betterpocket;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.platelminto.betterpocket.databinding.CardListItemBinding;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.SimpleViewHolder> {

    private List<Article> articles;

    ListAdapter(List<Article> articles) {

        this.articles = articles;
    }

    public List<Article> getArticles() {

        return articles;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int type) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_item, parent, false);

        return new SimpleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {

        final Article article = articles.get(position);
        holder.getBinding().setVariable(BR.article, article);
        holder.getBinding().executePendingBindings();
    }

    public void addArticle(Article article) {

        articles.add(0, article);
        notifyItemInserted(0);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {

        private CardListItemBinding listItemBinding;

        SimpleViewHolder(View v) {
            super(v);
            listItemBinding = DataBindingUtil.bind(v);
        }

        CardListItemBinding getBinding(){
            return listItemBinding;
        }
    }
}
