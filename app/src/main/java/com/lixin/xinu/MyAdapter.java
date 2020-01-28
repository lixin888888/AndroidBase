package com.lixin.xinu;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private List<String> datas;
    private Context context;

    public MyAdapter(Context context,List<String> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public MyAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = LayoutInflater.from(context)
                .inflate(R.layout.testadapter, parent, false);
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyHolder holder, int position) {
        Glide.with(context)
                .load("http://six.ijavascript.club/d234c95beac03adb40ecea9")
                .into(holder.image);
        holder.text.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView text;
        public MyHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.test_image);
            text = itemView.findViewById(R.id.test_text);
        }
    }

}
