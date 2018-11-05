package com.example.platelminto.betterpocket;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.platelminto.betterpocket.databinding.SimpleListItemBinding;

import java.util.List;

public class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.SimpleViewHolder> {

    private List<Person> mPeople;

    public SimpleListAdapter(List<Person> people){
        mPeople = people;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_list_item, parent, false);
        SimpleViewHolder holder = new SimpleViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        final Person person = mPeople.get(position);
        holder.getBinding().setVariable(com.example.platelminto.betterpocket.BR.person, person);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mPeople.size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        private SimpleListItemBinding listItemBinding;

        public SimpleViewHolder(View v) {
            super(v);
            listItemBinding = DataBindingUtil.bind(v);
        }

        public SimpleListItemBinding getBinding(){
            return listItemBinding;
        }
    }
}
