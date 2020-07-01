package com.lixin.xinu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixin.xinu.beans.Goods;

import java.util.ArrayList;
import java.util.List;

public class GoodsItemSquareAdapter extends RecyclerView.Adapter<GoodsItemSquareAdapter.GoodsItemSquareHoder> {

    private final Context mContext;

    private List<Goods> mDatas;

    private LayoutInflater inflater;

    public GoodsItemSquareAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        mDatas = new ArrayList<>();
    }

    @NonNull
    @Override
    public GoodsItemSquareHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsItemSquareHoder goodsItemSquareHoder, int i) {

    }

    @Override
    public int getItemCount() {

        return 0;
    }

    static class  GoodsItemSquareHoder extends RecyclerView.ViewHolder{

       public GoodsItemSquareHoder(@NonNull View itemView) {

           super(itemView);
       }
   }
}
