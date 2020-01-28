package com.lixin.xinu.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.lixin.xinu.adapters.leftAdapter;
import com.lixin.xinu.adapters.rightAdapter;
import com.lixin.xinu.beans.Goods;
import com.lixin.xinu.R;

public class Recycle_doubleFragment extends Fragment implements leftAdapter.GaosuFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    // 两个recycleView
    private RecyclerView leftRecycle;
    private RecyclerView rightRecycle;

    // 两个Mannager
    private LinearLayoutManager leftManager;
    private LinearLayoutManager rightManager;

    // context
    private Context mContext;

    //adapter 适配器
    private leftAdapter ladapter;
    private rightAdapter radapter;

    // indicator 标识 left的position
    private int leftCurrentPosition;

    private List<String> leftDatas = new ArrayList<>();
    private List<Goods>  rightDatas = new ArrayList<>();

    // 要跳转的 目标是否在最后一个可见view 之后
    private boolean mSholudScrool;
    // 记录目标项的位置
    private int ToPosition;
    public Recycle_doubleFragment() {
        // Required empty public constructor
    }

    public static Recycle_doubleFragment newInstance(String param1, String param2) {
        Recycle_doubleFragment fragment = new Recycle_doubleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // 设置左右两边的布局的数据
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_recycle_double, container, false);

        getData();
        // 左边

        leftRecycle = view.findViewById(R.id.left_recycle);

        leftManager = new LinearLayoutManager(mContext);

        ladapter = new leftAdapter(leftDatas,mContext,this);

        leftRecycle.setLayoutManager(leftManager);

        leftRecycle.setAdapter(ladapter);

        // 右边
        rightRecycle = view.findViewById(R.id.right_recycle);

        radapter = new rightAdapter(rightDatas,mContext);

        rightManager = new LinearLayoutManager(mContext);

        rightRecycle.setLayoutManager(rightManager);

        rightRecycle.setAdapter(radapter);

        rightRecycle.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState ==RecyclerView.SCROLL_STATE_DRAGGING && mSholudScrool){
                    mSholudScrool = false;
                    recyclerView.smoothScrollToPosition(ToPosition);
                }
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 得到第一个显示商品的分类
                int firstCompletelyPosition = rightManager.findFirstCompletelyVisibleItemPosition();
                if (firstCompletelyPosition!=-1){
                    changeLeftPosition(firstCompletelyPosition);
                }
            }
        });
        return view;
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

    void getData(){
        // 左边的商品初始化
        leftDatas.add("add");
        // 右边的商品初始化
        Goods goods = new Goods();
        goods.setGoodsName("add");
        rightDatas.add(goods);
    }

    // 改变 left的位置
    void changeLeftPosition(int newPosition){
        // 如果没有变化
        if(newPosition == leftCurrentPosition){
            // nothing to do
        }else {
            View newView = leftManager.findViewByPosition(newPosition);
            View oldView = leftManager.findViewByPosition(leftCurrentPosition);
            if (newView!=null){
                // 将新的颜色改变
                newView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
                // 上一个view的颜色回复原来的颜色
                oldView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
            // 更新 当前的position
            leftCurrentPosition = newPosition;
        }
    }
    // 点击左边的 view 右边的view自动滚动到顶部
    // 如果跳转的位置 在第一个可见位置之前 smoothScrollToPosition() 可以直接跳转
    // 如果跳转的位置在第一个可见项之前 ,最后一个可见项之后 ,就是这个view就在屏幕上,那么就不会跳转,需要smmothSroolBy
    // 如果跳转的位置 在最后一个可见项之后 那么 smoothScroolToPositon 将要跳转的位置滚动到可见位置 ,调用smoothMovetoPOsitioon
    void scrollToPosition(final int position){
            int firstPosition = rightManager.findFirstVisibleItemPosition();
            int lastPosition = rightManager.findLastVisibleItemPosition();
            if (position<firstPosition) {
                rightRecycle.smoothScrollToPosition(position);
            }else if(position<=lastPosition){
                // 第二种可能
                int movePosition = position - firstPosition;
                if(movePosition >= 0 && movePosition < rightRecycle.getChildCount()){
                    int top = rightRecycle.getChildAt(movePosition).getTop();
                    rightRecycle.smoothScrollBy(0,top);
                }
            }else {
                rightRecycle.smoothScrollToPosition(position);
                ToPosition = position;
                mSholudScrool = true;
            }
    }
    void scrollToPositionNoSmoomth(int position){
         if(position!=-1){
             rightRecycle.scrollToPosition(position);
             rightManager.scrollToPositionWithOffset(position,0);
         }
    }

    @Override
    public void setCurrentPosition(int currentPosition) {
        // 改变当前的颜色
        changeLeftPosition(currentPosition);
        // 右边需要滚动到相应的位置
        scrollToPositionNoSmoomth(currentPosition);
    }


}
