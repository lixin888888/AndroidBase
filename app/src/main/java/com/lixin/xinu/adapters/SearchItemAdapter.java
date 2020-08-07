package com.lixin.xinu.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.lixin.xinu.R;
import com.lixin.xinu.dto.SearchGoodsReq;
import com.lixin.xinu.dto.EsGoods;
import com.lixin.xinu.entities.CommonPage;
import com.lixin.xinu.entities.CommonResult;
import com.lixin.xinu.netServices.GoodsService;
import com.lixin.xinu.netServices.NetServicePrefixAddress;
import com.lixin.xinu.netServices.RetrofitManager;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.SItemHolder> {


    Context mContext;
    LayoutInflater inflater;
    GoodsService goodsService;
    ArrayList<EsGoods> mDatas;
    private static final String TAG = "SearchItemAdapter";

    public SearchItemAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        goodsService = RetrofitManager.createService(GoodsService.class, NetServicePrefixAddress.ES);
        mDatas = new ArrayList<>();
        // 初始化 历史查询

    }

    public void searchGoodsList(
            SearchGoodsReq req,
            RefreshLayout refreshLayout

    ) {
        Call<CommonResult<CommonPage<EsGoods>>> commonResultCall =
                goodsService.searchGoodsByES(req.getBrandId(), req.getKeyword(), req.getProductCategoryId(), req.getSort(), req.getPageNum(), req.getPageSize());
        commonResultCall.enqueue(new Callback<CommonResult<CommonPage<EsGoods>>>() {
            @Override
            public void onResponse(Call<CommonResult<CommonPage<EsGoods>>> call, Response<CommonResult<CommonPage<EsGoods>>> response) {
                mDatas.addAll(response.body().getData().getList());
                notifyDataSetChanged();
                Log.i(TAG,response.body().getData().toString());
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onFailure(Call<CommonResult<CommonPage<EsGoods>>> call, Throwable t) {
                Log.e(TAG,t.toString());
            }
        });
    }

    public void clearData(){
        mDatas.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_goods_layout, parent, false);
        return new SItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SItemHolder holder, int position) {
        EsGoods searchShopRes = mDatas.get(position);
        holder.sg_shopAddress.setText(searchShopRes.getShopAddress());
        holder.sg_keywords.setText(searchShopRes.getSubTitle());
        holder.sg_sale.setText(R.string.month_sale + searchShopRes.getSale());
        holder.sg_name.setText(searchShopRes.getName());
        Glide.with(mContext).load(searchShopRes.getPic()).into(holder.sg_pic);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public static class SItemHolder extends RecyclerView.ViewHolder {
        public TextView sg_name;
        public TextView sg_sale;
        public TextView sg_keywords;
        public TextView sg_shopAddress;
        public ImageView sg_pic;

        public SItemHolder(@NonNull View itemView) {
            super(itemView);
            sg_name = itemView.findViewById(R.id.sg_name);
            sg_sale = itemView.findViewById(R.id.sg_sale);
            sg_keywords = itemView.findViewById(R.id.sg_keywords);
            sg_shopAddress = itemView.findViewById(R.id.sg_shopAddress);
            sg_pic = itemView.findViewById(R.id.sg_pic);
        }
    }
}
