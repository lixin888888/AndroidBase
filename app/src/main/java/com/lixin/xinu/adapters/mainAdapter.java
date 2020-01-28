package com.lixin.xinu.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.lixin.xinu.beans.shopMain;
import com.lixin.xinu.MainActivity;
import com.lixin.xinu.R;
import com.lixin.xinu.ShopActivity;

/**
 * 给每一个Fragment里面的RecycleView的数据展示控件
 */
public class mainAdapter extends RecyclerView.Adapter<mainAdapter.ShopListHolder> {
    Context mContext;
    List<shopMain> datas;

    public mainAdapter(Context mContext,List<shopMain> datas) {
        this.datas=datas;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ShopListHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_oneimage, parent, false);
        return new ShopListHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListHolder shop, int i) {
        shopMain sm = datas.get(i);
        Glide.with(mContext).load("https://p1.meituan.net/deal/29d02aace1e1951905bd534c66f1c70558212.jpg@214w_120h_1e_1c").into(shop.shopImage);
        shop.shopTitle.setText(sm.getShopTitle());
        shop.salesCount.setText(new StringBuilder("月售").append(String.valueOf(sm.getSalesCount())));
        shop.costTime.setText(new StringBuilder(String.valueOf(sm.getCostTime())).append("分钟"));
        shop.sendLevel.setText(new StringBuilder("起送").append(String.valueOf(sm.getSendLevel())).append("  配送").append(sm.getCostSend()));
        shop.goods1.setText(sm.getGoods1());
        shop.goods2.setText(sm.getGoods2());
        shop.goods3.setText(sm.getGoods3());
        shop.goods4.setText(sm.getGoods4());
        shop.goods5.setText(sm.getGoods5());
        shop.youhui1.setText(sm.getYouhui1());
        shop.youhui2.setText("满100减15");
        shop.shopAddress.setText(sm.getShopAddress());
        shop.itemView.setTag(i);
        shop.itemView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ShopListHolder extends RecyclerView.ViewHolder{
        private ImageView shopImage;
        private TextView shopTitle,salesCount,costTime,sendLevel,goods1,goods2,goods3,goods4,goods5,youhui1,youhui2,shopAddress;

        public ShopListHolder(@NonNull View itemView) {
            super(itemView);
            shopImage = itemView.findViewById(R.id.shopImage);
            shopTitle = itemView.findViewById(R.id.shopTitle);
            salesCount = itemView.findViewById(R.id.salesCount);
            costTime = itemView.findViewById(R.id.costTime);
            sendLevel = itemView.findViewById(R.id.sendLevel);
            goods1 = itemView.findViewById(R.id.goods1);
            goods2 = itemView.findViewById(R.id.goods2);
            goods3 = itemView.findViewById(R.id.goods3);
            goods4 = itemView.findViewById(R.id.goods4);
            goods5 = itemView.findViewById(R.id.goods5);
            youhui1 = itemView.findViewById(R.id.youhui1);
            youhui2 = itemView.findViewById(R.id.youhui2);
            shopAddress = itemView.findViewById(R.id.shopAddress);
        }
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           int i = (int) v.getTag();
           Log.i("click",i+"");
           Intent intent = new Intent((MainActivity)mContext,ShopActivity.class);
           mContext.startActivity(intent);
        }
    };
}
