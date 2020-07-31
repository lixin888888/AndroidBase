package com.lixin.xinu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.lixin.xinu.R;
import com.lixin.xinu.dto.SearchGoodsQueryParam;
import com.lixin.xinu.dto.SearchRecommandParam;
import com.lixin.xinu.entities.CommonPage;
import com.lixin.xinu.entities.CommonResult;
import com.lixin.xinu.entities.Goods;
import com.lixin.xinu.netServices.GoodsService;
import com.lixin.xinu.utils.GlideImageLoader;
import com.lixin.xinu.utils.RetrofitManager;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoodsItemSquareAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_1 = 0;
    public static final int TYPE_2 = 1;
    public static final int TYPE_3 = 2;

    private final Context mContext;

    private List<Goods> mDatas;

    private LayoutInflater inflater;

    GoodsService goodsService ;

    public GoodsItemSquareAdapter(Context mContext) {

        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);

        goodsService = RetrofitManager.createService(GoodsService.class);
        mDatas = new ArrayList<>();
        // 初始化加载数据
        getGoodsList(null);

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_1;
        }
        return TYPE_2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        if (viewType == TYPE_1){
            View view = inflater.inflate(R.layout.banner_item, viewGroup, false);
            BannerHolder bannerHolder = new BannerHolder((view));
            return bannerHolder;
        }

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.goods_item_square, viewGroup, false);
        GoodsItemSquareHolder holder = new GoodsItemSquareHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof GoodsItemSquareHolder){
            Goods l_goods = mDatas.get(2*i);
            Goods r_goods = mDatas.get(2 * i + 1);
            GoodsItemSquareHolder gsh = (GoodsItemSquareHolder) viewHolder;
            Glide.with(mContext).load(l_goods.getPic()).into(gsh.l_image);
            Glide.with(mContext).load(r_goods.getPic()).into(gsh.r_image);
            gsh.l_name.setText(l_goods.getName().substring(0,6));
            gsh.r_name.setText(r_goods.getName().substring(0,6));
            gsh.l_price.setText(l_goods.getPrice().toString());
            gsh.r_price.setText(r_goods.getPrice().toString());
            gsh.l_origin_price.setText(l_goods.getOriginalPrice().toString());
            gsh.r_origin_price.setText(r_goods.getOriginalPrice().toString());
            gsh.l_sale.setText(l_goods.getSale().toString());
            gsh.r_sale.setText(l_goods.getSale().toString());
        }else if (viewHolder instanceof BannerHolder){
            //设置banner样式
            Banner banner = ((BannerHolder) viewHolder).banner;
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(createImages());
            //设置轮播时间
            banner.setDelayTime(5000);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        }
    }



    @Override
    public int getItemCount() {
        return mDatas.size()/2;
    }

    static class  GoodsItemSquareHolder extends RecyclerView.ViewHolder{

        ImageView l_image,r_image;
        TextView l_name , r_name;
        TextView l_price,r_price;
        TextView l_origin_price,r_origin_price;
        TextView l_sale,r_sale;

       public GoodsItemSquareHolder(@NonNull View itemView) {
           super(itemView);
           l_image = itemView.findViewById(R.id.l_image);
           r_image = itemView.findViewById(R.id.r_image);
           l_name = itemView.findViewById(R.id.l_name);
           r_name = itemView.findViewById(R.id.r_name);
           l_name = itemView.findViewById(R.id.l_name);
           l_price = itemView.findViewById(R.id.l_price);
           r_price = itemView.findViewById(R.id.r_price);
           l_origin_price = itemView.findViewById(R.id.l_origin_price);
           r_origin_price = itemView.findViewById(R.id.r_origin_price);
           l_sale = itemView.findViewById(R.id.l_sale);
           r_sale = itemView.findViewById(R.id.r_sale);
       }
   }

    static class  BannerHolder extends RecyclerView.ViewHolder{
        Banner banner;
        public BannerHolder(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
        }
    }

   // 获取网络数据进行加载
    public void getGoodsList(RefreshLayout refreshlayout){
        Call<CommonResult<CommonPage<com.lixin.xinu.entities.Goods>>> commonPageCommonResult =
                goodsService.searchGoods(1,1,10);

        commonPageCommonResult.enqueue(new Callback<CommonResult<CommonPage<com.lixin.xinu.entities.Goods>>>() {
            @Override
            public void onResponse(Call<CommonResult<CommonPage<com.lixin.xinu.entities.Goods>>> call,
                                   Response<CommonResult<CommonPage<com.lixin.xinu.entities.Goods>>> response) {
                mDatas.addAll(response.body().getData().getList());
                notifyDataSetChanged();
                if (refreshlayout!=null){
                    refreshlayout.finishLoadMore(true);//传入false表示加载失败
                }
            }

            @Override
            public void onFailure(Call<CommonResult<CommonPage<com.lixin.xinu.entities.Goods>>> call, Throwable t) {
                if (refreshlayout!=null){
                    refreshlayout.finishLoadMore(false);//传入false表示加载失败
                }
                Log.e("err",t.getMessage());
            }
        });
    }

    private List<Integer> createImages(){
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.img_banner01);
        images.add(R.drawable.img_banner02);
        images.add(R.drawable.img_banner03);
        images.add(R.drawable.img_banner04);
        return images;
    }
}
