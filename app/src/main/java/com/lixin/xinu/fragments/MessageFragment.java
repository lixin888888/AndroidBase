package com.lixin.xinu.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lixin.xinu.activities.SearchActivity;
import com.lixin.xinu.adapters.GoodsItemSquareAdapter;
import com.lixin.xinu.interfaces.OnFragmentInteractionListener;
import com.lixin.xinu.R;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;

public class MessageFragment extends Fragment implements View.OnClickListener {


    Banner banner;
    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private LinearLayout searchBar;
    private Context mContext;

    public MessageFragment() {
    }

    public static MessageFragment newInstance(Context context) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mContext = this.getContext();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message, container, false);
        searchBar = view.findViewById(R.id.searchBar);
        searchBar.setOnClickListener(this);
        // 找到recycleView 注入数据和adapter
        mRecyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        GoodsItemSquareAdapter goodsItemSquareAdapter = new GoodsItemSquareAdapter(mContext);
        mRecyclerView.setAdapter(goodsItemSquareAdapter);
        // 下拉加载
        RefreshLayout refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext));
        refreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        refreshLayout.setEnableOverScrollBounce(false);//是否启用越界回弹
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                goodsItemSquareAdapter.getGoodsList(refreshlayout);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void onDetach() {
        super.onDetach();
        // 停止滚动
        banner.stopAutoPlay();
        mListener = null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchBar:
                Intent intent = new Intent(mContext, SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
