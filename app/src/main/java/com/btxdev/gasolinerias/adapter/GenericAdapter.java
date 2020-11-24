package com.btxdev.gasolinerias.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GenericAdapter <T extends GenericViewHolder> extends RecyclerView.Adapter<T> {

    private Context context;
    private InstanceCallback callback;
    private int layoutResource;

    public GenericAdapter(Context context, int layoutResource, InstanceCallback<T> callback) {
        this.context = context;
        this.layoutResource = layoutResource;
        this.callback = callback;
    }


    @NonNull
    @Override
    public T onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return (T) callback.onCreateViewHolder(LayoutInflater.from(context).inflate(layoutResource, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull T holder, int position) {
        callback.onBindViewHolder(holder,position);
    }

    public interface InstanceCallback<T extends GenericViewHolder>{
        T onCreateViewHolder(View view);
        void onBindViewHolder(@NonNull T viewHolder, int i);
        int getItemCount();
    }

    @Override
    public int getItemCount() {
        return callback.getItemCount();
    }


    public void setOn(RecyclerView recyclerView, boolean withDivider){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if(withDivider){
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }
        recyclerView.setAdapter(this);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setOnGrid(RecyclerView recyclerView, int spanCount){
        GridLayoutManager layoutManager = new GridLayoutManager(context,spanCount);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(this);
    }
}
