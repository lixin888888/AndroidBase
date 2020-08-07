package com.lixin.xinu.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.lixin.xinu.beans.MensWear;

public class mensWearAdapter extends RecyclerView.Adapter<mensWearAdapter.mwHolder> {
    private Context mContext;
    private List<MensWear> datas;

    @NonNull
    @Override
    public mwHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull mwHolder mwHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class mwHolder extends RecyclerView.ViewHolder{

        public mwHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
