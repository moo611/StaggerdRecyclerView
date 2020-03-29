package com.atech.staggedrv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atech.staggedrv.model.StaggedModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public abstract class StaggedAdapter<T extends StaggedModel> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<T>datas = new ArrayList<>();
    private Context c;
    public StaggedAdapter(Context c){
        this.c = c;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return addViewHolder(viewGroup,i);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        bindView(viewHolder, i);

    }

    @Override
    public int getItemCount() {
         return datas.size();
    }

    /**
     * 下拉刷新
     * @param datas
     */
    public void refresh(List<T> datas){

        this.datas = datas;

        notifyItemRangeChanged(0, datas.size());


    }

    /**
     * 加载更多
     * @param datas
     */
    public void loadMore(List<T> datas){

        int startPos = this.datas.size() - 1;

        notifyItemRangeInserted(startPos, datas.size());

        this.datas.addAll(datas);

    }

    public abstract RecyclerView.ViewHolder addViewHolder(ViewGroup viewGroup, int i);

    public abstract void bindView(RecyclerView.ViewHolder viewHolder, int i);

}
