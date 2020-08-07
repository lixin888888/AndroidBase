package com.lixin.xinu.activities;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.lixin.xinu.R;
import com.lixin.xinu.adapters.SearchItemAdapter;
import com.lixin.xinu.customerView.FilterLayout;
import com.lixin.xinu.dto.SearchGoodsReq;
import com.qiniu.android.utils.StringUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yyydjk.library.DropDownMenu;


public class SearchActivity extends AppCompatActivity implements View.OnClickListener, PopupWindow.OnDismissListener {

    private static final String TAG = "SearchActivity";
    EditText searchInput;
    PopupWindow popupWindow;
    DropDownMenu mDropDownMenu;
    RecyclerView mRecycleView;
    SearchGoodsReq req = new SearchGoodsReq();
    SearchItemAdapter searchItemAdapter;
    RefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayout searchBar;
    private FilterLayout price, distance, sell, all;
    private EditText editText;
    private LinearLayout searchTextbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initImmersionBar();
        //processWindowsBar();
        //hideBottomUIMenu();
        initSelectFilter();
        initRecycleView();
        initData();
    }


    /**
     * 初始化筛选器
     */
    public void initSelectFilter() {
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
     * recycle的初始化
     */
    public void initRecycleView() {
        // 找到recycleView 注入数据和adapter
        mRecyclerView = findViewById(R.id.s_recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        searchItemAdapter = new SearchItemAdapter(this);
        mRecyclerView.setAdapter(searchItemAdapter);

        // 下拉加载
        refreshLayout = (RefreshLayout) findViewById(R.id.s_refreshlayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        // 设置监听器
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                req.pageNum = 0;
                searchItemAdapter.searchGoodsList(req, refreshLayout);
            }
        });
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
     * 初始化 数据
     */
    public void initData() {
        searchTextbar = findViewById(R.id.search_text_bar);
        editText = findViewById(R.id.searchInput);
        searchTextbar.setOnClickListener(this);

        req.keyword = "小米";
        searchItemAdapter.searchGoodsList(req, refreshLayout);
    }

    /**
     * 获取焦点 拉起键盘
     */
    public void onFocus() {
        searchInput.setFocusable(true);
        searchInput.setFocusableInTouchMode(true);
        searchInput.requestFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * 设置组件的监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.s_all:
                showPop(v);
                break;
            case R.id.s_distance:
                searchItemAdapter.clearData();
                req.setKeyword("华为");
                searchItemAdapter.searchGoodsList(req, refreshLayout);
                break;
            case R.id.search_text_bar:
                String keyWord = editText.getText().toString();
                if (!StringUtils.isBlank(keyWord)) {
                    SearchGoodsReq req = new SearchGoodsReq();
                    req.setKeyword(keyWord);
                    searchItemAdapter.clearData();
                    searchItemAdapter.searchGoodsList(req, refreshLayout);
                    Log.i(TAG, keyWord);
                }
                break;
            case R.id.backto:
                break;
        }
    }

    /**
     * 点击筛选条件 popWindow的创建
     */
    public void showPop(View anchor) {

        View view = getLayoutInflater().inflate(R.layout.all_filter_search_pop_layout, null);
        if (popupWindow == null) {

            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setOnDismissListener(this);
            popupWindow.showAsDropDown(anchor, 0, 0);
        }
        if (!popupWindow.isShowing()) {
            popupWindow.showAsDropDown(anchor, 0, 0);
        }


    }

    @Override
    public void onDismiss() {

    }


    /**
     * 重写view事件分发
     * https://blog.csdn.net/ITYDXTOCATTLE/article/details/51003382
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            editText.setCursorVisible(false);
        }
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private void showBottomUIMenu() {
        //恢复普通状态
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.VISIBLE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SCREEN_STATE_OFF;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void processWindowsBar() {
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_container_layout), new OnApplyWindowInsetsListener() {
                @Override
                public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                    return insets.consumeSystemWindowInsets();
                }
            });
        }
    }

}