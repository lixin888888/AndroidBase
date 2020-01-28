package com.lixin.xinu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import com.lixin.xinu.R;
import com.lixin.xinu.boos.CustomerListener;


public class LeftShowCatogeryAdapter extends RecyclerView.Adapter<LeftShowCatogeryAdapter.lscHolder> {

    ArrayList<String> datas = new ArrayList<>();
    Context mContext;
    private View.OnClickListener listenet;
    private CustomerListener customerListener;

    public LeftShowCatogeryAdapter(ArrayList<String> categorys ,Context context) {
            this.datas=categorys;
            this.mContext=context;
    }

    @NonNull
    @Override
    public LeftShowCatogeryAdapter.lscHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View  view =   LayoutInflater.from(mContext).inflate(R.layout.leftshowcatogeryadapter,viewGroup,false);
        return new lscHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LeftShowCatogeryAdapter.lscHolder lscHolder, int i) {
            if(datas.get(i).equals("add")){
                lscHolder.textView.setText("添加类别");
                lscHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listenet.onClick(v);
                    }
                });
            }else {
                lscHolder.textView.setText(datas.get(i));
                lscHolder.itemView.setTag(datas.get(i));
                lscHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customerListener.IWantDo((String) v.getTag());
                    }
                });
            }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static  class lscHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public lscHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.activity_food_category);
        }
    }
    public void setOnItemClickListener(View.OnClickListener listener){
        this.listenet = listener;
    }

    public void setCustomerListener(CustomerListener customerListener){
        this.customerListener = customerListener;
    }

}
