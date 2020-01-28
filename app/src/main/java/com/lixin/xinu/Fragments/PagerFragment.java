package com.lixin.xinu.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.lixin.xinu.adapters.mainAdapter;
import com.lixin.xinu.beans.WhatDo;
import com.lixin.xinu.beans.shopMain;
import com.lixin.xinu.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.lixin.xinu.utils.OkNet;

/**
 * 这个是由viewpager 里面自动生成的
 */
public class PagerFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int  mPage;
    private String mParam2;
    // recycle 的引用
    private RecyclerView prv;
    // context的引用
    private Context mContext;
    public mainAdapter mAdapter;
    private OkHttpClient client = new OkHttpClient();
    private HomeHandler homeHandler;
    // 数据源
    private List<shopMain> shopMains = new ArrayList<>();

    public PagerFragment() { }
    // 参数进来的入口
    public static PagerFragment newInstance(int  page, String param2) {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, page);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // 创建时可获得塞进来的参数
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getData();
    }

    // 此函数生成布局 在这里可以根据不同的adapter
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        prv = view.findViewById(R.id.page_rv);
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        prv.setLayoutManager(lm);
        mAdapter = new mainAdapter(mContext,shopMains);
        prv.setAdapter(mAdapter);
        // 设置滚动时 不去加载图片
        prv.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState ==RecyclerView.SCROLL_STATE_IDLE){
                    Glide.with(mContext).resumeRequests();
                }else {
                    Glide.with(mContext).pauseRequests();
                }
            }
        });

        homeHandler = new HomeHandler(new WeakReference<PagerFragment>(this));
        return view;
    }

    // 获取网络数据
    void getData(){
        final Request request = OkNet.getRequest(new WhatDo("", 0),
                "http://192.168.43.254:8080/lixin_war_exploded/shop/getfoodsShops");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                    // failed to process
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert request.body() != null;
                String service = request.body().toString();
                Gson gson = new Gson();
                List<shopMain> shops = gson.fromJson(response.body().string(), new TypeToken<List<shopMain>>() {}.getType());
                shopMains.addAll(shops);
                homeHandler.sendEmptyMessage(0x01);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    static class HomeHandler extends Handler {
        WeakReference<PagerFragment> mWeakref;
        public HomeHandler(WeakReference<PagerFragment> mWeakref) {
            this.mWeakref = mWeakref;
        }
        @Override
        public void handleMessage(Message msg) {
            final  PagerFragment fragment = mWeakref.get();
            if(fragment!=null){
                if(msg.what==0x01){
                    fragment.mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

}
