package com.lixin.xinu.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.List;
import com.lixin.xinu.beans.Goods;
import com.lixin.xinu.beans.WhatDo;
import com.lixin.xinu.R;
import com.lixin.xinu.boos.MyOnFragmentInteractionListener;

public class rightAdapter extends RecyclerView.Adapter<rightAdapter.rightHolder> {

    private List<Goods> mRightDatas;
    private Context mContext;
    // 在这里完成数据的初始化
    public rightAdapter(List<Goods> mLeftDatas, Context mContext) {
        this.mRightDatas = mLeftDatas;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public rightAdapter.rightHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View  view =   LayoutInflater.from(mContext).inflate(R.layout.editgoods,viewGroup,false);
        return new rightHolder(view);
    }

    // 点击之后 跳转到另一个页面 返回的时候 需要添加一项 // 需要指出是哪一类别
    public void addRightData(Goods goods,String category){
        int index = 0;
        int count = mRightDatas.size();
        boolean isLooked = false;
        for (int i = 0; i < count; i++) {
            if (mRightDatas.get(i).getGoodscategory().equals(category)){
                isLooked = true;
            }else {
                if(isLooked){
                    index = i;
                }
            }
        }
        mRightDatas.add(index,goods);
        notifyItemInserted(index);
    }

    @Override
    public void onBindViewHolder(@NonNull rightAdapter.rightHolder rHolder, int i) {
        Goods goods = mRightDatas.get(i);
        if (goods.getGoodsName().equals("add")){
            rHolder.goodsName.setText("");
            rHolder.mainGoodsImage.setImageResource(R.drawable.ic_add);
            rHolder.xiaJiaGoods.setVisibility(View.INVISIBLE);
            rHolder.editGoods.setVisibility(View.GONE);
            rHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyOnFragmentInteractionListener listener = (MyOnFragmentInteractionListener) mContext;
                    listener.onFragmentInteraction(new WhatDo());
                }
            });
        }else {
            rHolder.goodsName.setText(goods.getGoodsName());
            Glide.with(mContext).load(goods.getGoodsImages().get(0)).into(rHolder.mainGoodsImage);
        }
        // 把类别信息贴入标签
        rHolder.itemView.setTag(goods.getGoodscategory());
    }

    @Override
    public int getItemCount() {
        return mRightDatas.size();
    }

    // 设置每一个holder
    static class rightHolder extends RecyclerView.ViewHolder{
        private TextView goodsName;
        private ImageView mainGoodsImage,editGoods,xiaJiaGoods;
        public rightHolder(@NonNull View itemView) {
            super(itemView);
            goodsName = itemView.findViewById(R.id.edit_goods_goods_name);
            mainGoodsImage = itemView.findViewById(R.id.main_goods_image);
            editGoods = itemView.findViewById(R.id.edit_goods);
            xiaJiaGoods = itemView.findViewById(R.id.xiajia_goods);
        }
    }
}
