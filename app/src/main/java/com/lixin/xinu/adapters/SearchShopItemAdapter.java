package com.lixin.xinu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixin.xinu.dto.SearchShopRes;

import java.util.List;

public class SearchShopItemAdapter extends RecyclerView.Adapter<SearchShopItemAdapter.SearchShopItemHolder> {

    Context mContext;

    LayoutInflater inflater;

    List<SearchShopRes> datas;

    @NonNull
    @Override
    public SearchShopItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchShopItemHolder searchShopItemHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }



    static  class  SearchShopItemHolder extends RecyclerView.ViewHolder{

        public SearchShopItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
