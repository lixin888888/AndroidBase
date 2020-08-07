package com.lixin.xinu.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import com.lixin.xinu.R;

public class leftAdapter extends RecyclerView.Adapter<leftAdapter.leftHolder> {
    private List<String> mLeftDatas = new ArrayList<>();
    private Context mContext;
    private GaosuFragment gs;

    public leftAdapter(List<String> mLeftDatas, Context mContext,GaosuFragment gs) {
        this.mLeftDatas = mLeftDatas;
        this.mContext = mContext;
        this.gs = gs;
    }

    @NonNull
    @Override
    public leftHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View  view =   LayoutInflater.from(mContext).inflate(R.layout.left_layout,viewGroup,false);
      return new leftHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull leftHolder lHolder, int i) {
        String s = mLeftDatas.get(i);
        if(s.equals("add")){
            lHolder.category.setText(mLeftDatas.get(i));
        }else {
            lHolder.category.setText(mLeftDatas.get(i));
            lHolder.itemView.setTag(i);
            lHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gs.setCurrentPosition((Integer) v.getTag());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mLeftDatas.size();
    }

    static class leftHolder extends RecyclerView.ViewHolder{
        public TextView category;
        public leftHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.food_category);
        }
    }
    // 就在这里设置事件
    View.OnClickListener leftListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           gs.setCurrentPosition((int) v.getTag());
        }
    };

    public interface GaosuFragment{
        void setCurrentPosition(int currentPosition);
    }

}
