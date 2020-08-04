package com.lixin.xinu.activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.lixin.xinu.R;
import com.lixin.xinu.adapters.GoodsItemSquareAdapter;
import com.lixin.xinu.customerView.FilterLayout;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yyydjk.library.DropDownMenu;




public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    EditText searchInput;
    private RecyclerView mRecyclerView;
    private LinearLayout searchBar;
    private FilterLayout price,distance,sell,all;

    DropDownMenu mDropDownMenu;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initImmersionBar();

        mRecyclerView = findViewById(R.id.search_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        GoodsItemSquareAdapter goodsItemSquareAdapter = new GoodsItemSquareAdapter(this);
        mRecyclerView.setAdapter(goodsItemSquareAdapter);

        searchInput = findViewById(R.id.searchInput);
        searchInput.setFocusable(true);//让EditText失去焦点，然后获取点击事件
        onFocus();

        // 下拉加载
        RefreshLayout refreshLayout = findViewById(R.id.search_refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        refreshLayout.setEnableOverScrollBounce(false);//是否启用越界回弹
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                goodsItemSquareAdapter.getGoodsList(refreshlayout);
            }
        });


    }

    /**
     * 初始化筛选器
     */
    public void initSelectFilter(){
        price = findViewById(R.id.s_price);
        price.setOnclick(this);
        distance = findViewById(R.id.s_distance);
        distance.setOnclick(this);
        sell = findViewById(R.id.s_sell);
        sell.setOnclick(this);
        all = findViewById(R.id.s_all);
        all.setOnclick(this);
    }



    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        //设置共同沉浸式样式
        ImmersionBar.with(this).statusBarColor(R.color.wangyiyun).fitsSystemWindows(true).init();    //状态栏颜色，不写默认透明色.init();
    }



    /**
     * 获取焦点 拉起键盘
     */
    public void onFocus(){
        searchInput.setFocusable(true);
        searchInput.setFocusableInTouchMode(true);
        searchInput.requestFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    /**
     * 点击筛选条件 popWindow的创建
     */
    public void  showPop(){
        //getLayoutInflater().inflate(R.layout.all_filter_search_pop_layout,)

    }
}