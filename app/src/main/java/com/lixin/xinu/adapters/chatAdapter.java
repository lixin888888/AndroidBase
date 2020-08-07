package com.lixin.xinu.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.lixin.xinu.beans.ChatData;
import com.lixin.xinu.R;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.chatHolder> {
    private List<ChatData> datas;
    private Context mContext;
    public chatAdapter(List<ChatData> datas, Context context) {
        this.datas = datas;
        this.mContext = context;
    }

    // 根据不同的
    @NonNull
    @Override
    public chatHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i==1){
            return new chatHolder(LayoutInflater.from(mContext).inflate(R.layout.chat_text,viewGroup,false));
        }else if(i==2){
            return new chatHolder(LayoutInflater.from(mContext).inflate(R.layout.chat_image,viewGroup,false));
        }
        return null;
    }

    // 根据不同的Data结构 返回不同的类型
    @Override
    public int getItemViewType(int position) {
         return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull chatHolder chatHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class chatHolder extends RecyclerView.ViewHolder{

        public chatHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
