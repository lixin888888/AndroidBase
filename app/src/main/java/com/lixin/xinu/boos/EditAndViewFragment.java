package com.lixin.xinu.boos;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

import com.lixin.xinu.adapters.LeftShowCatogeryAdapter;
import com.lixin.xinu.adapters.MyListener;
import com.lixin.xinu.adapters.RightShowGoodsAdapter;
import com.lixin.xinu.R;
import okhttp3.OkHttpClient;

public class EditAndViewFragment extends Fragment implements View.OnClickListener {

    // 两个recycleView
    OkHttpClient client = new OkHttpClient();//创建client
    private RecyclerView leftRecycle;
    private RecyclerView rightRecycle;
    private Context mContext;

    // 两个Mannager
    private LinearLayoutManager leftManager;
    private LinearLayoutManager rightManager;

    //adapter 适配器
    private LeftShowCatogeryAdapter ladapter;
    private RightShowGoodsAdapter radapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private LinearLayout root;

    private String mParam1;
    private String mParam2;

    private MyOnFragmentInteractionListener  mListener;

    // 两个Data 数据源
    private ArrayList<String> lDatas = new  ArrayList<String>();
    private ArrayList<RGoods> rDatas = new ArrayList<RGoods>();

    // 自定义数据源

    private HashMap<String,ArrayList<RGoods>> customerDatas  =  new HashMap<>();

    private PopupWindow popupWindow;  // 控制popWindow的显示与隐藏

    private boolean popupWindowIsShow;

    public EditAndViewFragment() {

    }
    private EditText editCategory;

    // 当前类别
    private String currentCatogery;

    public static EditAndViewFragment newInstance(String param1, String param2) {
        EditAndViewFragment fragment = new EditAndViewFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_edit_and_view, container, false);
        initData();
        root = view.findViewById(R.id.root);
        leftRecycle =view. findViewById(R.id.myf_left_recycle);
        leftManager = new LinearLayoutManager(mContext);
        ladapter = new LeftShowCatogeryAdapter(lDatas,mContext);
        ladapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow();
            }
        });
        ladapter.setCustomerListener(new CustomerListener() {
            @Override
            public void IWantDo(String msg) {
                currentCatogery=msg;
                if(customerDatas.get(msg)==null){
                    // 如果没有数据 新增一个
                    customerDatas.put(msg , new ArrayList<RGoods>());
                    rDatas= new ArrayList<>();
                    radapter.notifyDataSetChanged();
                }else {
                    rDatas=customerDatas.get(msg);
                    radapter.notifyDataSetChanged();
                }
            }
        });

        leftRecycle.setLayoutManager(leftManager);
        leftRecycle.setAdapter(ladapter);
        // 右边
        rightRecycle = view.findViewById(R.id.myf_right_recycle);
        radapter = new RightShowGoodsAdapter(rDatas,mContext);
        radapter.setListener(new Listener());
        rightManager = new LinearLayoutManager(mContext);
        rightRecycle.setLayoutManager(rightManager);
        rightRecycle.setAdapter(radapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext =context;
        mListener = (MyOnFragmentInteractionListener) mContext;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    void initData(){
        lDatas.add("add");
        RGoods goods = new RGoods();
        goods.setGoodsName("add");
        rDatas.add(goods);
    }
    // 当点击左边的添加内容时 触发
    public void showPopWindow(){
        View contentView = LayoutInflater.from(getActivity())
                .inflate(R.layout.edit,null,false);
        popupWindow = new PopupWindow(contentView,ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,true);
        // 找出控件 并设置监听函数
        editCategory = contentView.findViewById(R.id.editCatogery);
        TextView oktext = contentView.findViewById(R.id.editCotegoryOk);
        oktext.setOnClickListener(this);
        TextView cancel = contentView.findViewById(R.id.editCategoryCancel);
        cancel.setOnClickListener(this);
        //产生背景变暗效果
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.4f;
        getActivity().getWindow().setAttributes(lp);
        //点击外面popupWindow消失
        popupWindow.setOutsideTouchable(false);
        //设置popupWindow消失时的监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.showAtLocation(root,Gravity.CENTER,0,0);
        popupWindowIsShow= true;

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.editCotegoryOk:
                // 得到输入的内容
                String category = editCategory.getText().toString();
                if(category.equals("")){
                    popupWindow.dismiss();
                    return;
                }
                lDatas.add(editCategory.getText().toString());
                ladapter.notifyItemInserted(lDatas.size()-1);
                popupWindow.dismiss();
                break;
            case R.id.editCategoryCancel:
                popupWindow.dismiss();
                break;
        }

    }

    // 一个公共的方法 让 activity调用
    public void addGoods(RGoods goods,String category){
        Log.i("hellooo",category);
        Log.i("hellooo",customerDatas.get(category).size()+"");
        customerDatas.get(category).add(goods);
        radapter.notifyItemInserted(customerDatas.get(category).size());
    }

    class Listener implements MyListener{
        @Override
        public void IWantDo(int i) {
            // 拿到i的值,就知道点击的是哪个商品

        }
    }
    // 主activity获得当前类别
    public String getCurrentCatogery(){
        return currentCatogery;
    }
}
