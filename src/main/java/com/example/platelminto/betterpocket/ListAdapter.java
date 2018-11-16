package com.example.platelminto.betterpocket;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.platelminto.betterpocket.databinding.CardListItemBinding;

import java.util.List;

// Class to handle the list of articles in the RecyclerView
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CardViewHolder> {

    private List<Article> articles;

    ListAdapter(List<Article> articles) {

        this.articles = articles;
    }

    List<Article> getArticles() {

        return articles;
    }

    // Generates the View from the card list items (probably)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int type) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_item, parent, false);

        return new CardViewHolder(v);
    }

    // Sets the article variable used in the layout for every binding (every article)
    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {

        final Article article = articles.get(position);
        holder.getBinding().setVariable(BR.article, article);
        holder.getBinding().executePendingBindings();
    }

    // Adds article to the recyclerView
    void addArticle(Article article) {

        articles.add(0, article);
        notifyItemInserted(0);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    // Not sure what this does, binds a class to the bindings that are generated?
    static class CardViewHolder extends RecyclerView.ViewHolder {

        private CardListItemBinding listItemBinding;

        CardViewHolder(View v) {
            super(v);
            listItemBinding = DataBindingUtil.bind(v);
        }

        CardListItemBinding getBinding(){
            return listItemBinding;
        }
    }
}
