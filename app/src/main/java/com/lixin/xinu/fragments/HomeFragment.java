package com.lixin.xinu.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.lixin.xinu.interfaces.OnFragmentInteractionListener;
import com.lixin.xinu.adapters.SimpleFragmentPagerAdapter;
import com.lixin.xinu.adapters.mainAdapter;
import com.lixin.xinu.beans.shopMain;
import com.lixin.xinu.R;
import okhttp3.OkHttpClient;

public class HomeFragment extends Fragment {

    private RecyclerView recy;
    private List<shopMain> shopMains = new ArrayList<>();
    public mainAdapter mAdapter;
    private Context mContext;
    /**
     * 设置TabLayout
     */
    private TabLayout tableLayout;
    private ViewPager viewPager;

    private SimpleFragmentPagerAdapter pageradapter;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {

    }
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        tableLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewpager);
        pageradapter = new SimpleFragmentPagerAdapter(getFragmentManager(),mContext);
        viewPager.setAdapter(pageradapter);
        //connection viewpager and tableLayout
        tableLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
