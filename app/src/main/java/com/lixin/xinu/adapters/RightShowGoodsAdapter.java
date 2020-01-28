package com.lixin.xinu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.lixin.xinu.beans.WhatDo;
import com.lixin.xinu.R;
import com.lixin.xinu.boos.MyOnFragmentInteractionListener;
import com.lixin.xinu.boos.RGoods;

public class RightShowGoodsAdapter extends RecyclerView.Adapter<RightShowGoodsAdapter.rsgHolder> {

    ArrayList<RGoods> datas = new ArrayList<>();
    Context context;
    MyListener listener;


    public RightShowGoodsAdapter(ArrayList<RGoods> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }
    public void setListener(MyListener listener){
        this.listener=listener;
    }

    @NonNull
    @Override
    public rsgHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View  view =   LayoutInflater.from(context).inflate(R.layout.editgoods,viewGroup,false);
        return new rsgHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rsgHolder rsgHolder, int i) {
        RGoods goods = datas.get(i);
        if (goods.getGoodsName().equals("add")){
            rsgHolder.goodsName.setText("");
            rsgHolder.mainGoodsImage.setImageResource(R.drawable.ic_add);
            rsgHolder.xiaJiaGoods.setVisibility(View.INVISIBLE);
            rsgHolder.editGoods.setVisibility(View.GONE);
            rsgHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyOnFragmentInteractionListener listener = (MyOnFragmentInteractionListener) context;
                    listener.onFragmentInteraction(new WhatDo("goods",1));
                }
            });
        }else {
            rsgHolder.goodsName.setText(goods.getGoodsName());
            rsgHolder.itemView.setTag(i);
            rsgHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.IWantDo((int) v.getTag());
                }
            });
            Glide.with(context).load(goods.getGoodsImage()).into(rsgHolder.mainGoodsImage);
        }
        // 把类别信息贴入标签
        rsgHolder.itemView.setTag(goods.getGoodsName());
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class rsgHolder extends RecyclerView.ViewHolder{
        private TextView goodsName;
        private ImageView mainGoodsImage,editGoods,xiaJiaGoods;
        public rsgHolder(@NonNull View itemView) {
            super(itemView);
            goodsName = itemView.findViewById(R.id.edit_goods_goods_name);
            mainGoodsImage = itemView.findViewById(R.id.main_goods_image);
            editGoods = itemView.findViewById(R.id.edit_goods);
            xiaJiaGoods = itemView.findViewById(R.id.xiajia_goods);
        }
    }


}

