package com.lixin.xinu.boos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.LinearLayout;


import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

import com.lixin.xinu.beans.WhatDo;
import com.lixin.xinu.R;

public class ViewImageFragment extends Fragment {
    private ViewPager viewPager;

    //newInstance 执行之后获取的数据
    private ArrayList<Uri> mDataFile = new ArrayList<>();
    private int iWantToDispaly;
    private Context mContext;

    public ViewImageFragment() {
        // Required empty public constructor
    }

    public static ViewImageFragment newInstance(ArrayList<Uri> param, int currentPoint ) {
        ViewImageFragment fragment = new ViewImageFragment();
        int count = param.size();
        Bundle args = new Bundle();
        args.putParcelableArrayList("params",param);
        args.putInt("currentPoint", currentPoint);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDataFile = getArguments().getParcelableArrayList("params");
            iWantToDispaly = getArguments().getInt("currentPoint");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewimage, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.vp);
        viewPager.setAdapter(new MyPagerAdapter(mContext,mDataFile));
        viewPager.setCurrentItem(iWantToDispaly);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    class MyPagerAdapter extends PagerAdapter {
        private Context mContext;
        private List<Uri> mData;
        final MyOnFragmentInteractionListener listener = (MyOnFragmentInteractionListener) getActivity();
        @Override
        public int getCount() {
            return mData.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.layout_viewimage,null);
            final PhotoView pv = (PhotoView) view.findViewById(R.id.viewpager_imageview);
            Glide.with(ViewImageFragment.this).load(mData.get(position)).thumbnail(0.2f).into(pv);
            pv.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    AnimationSet animationset = new AnimationSet(true);
                    AlphaAnimation animation = new AlphaAnimation(1,0);
//                    TranslateAnimation animation1 = new TranslateAnimation(0,0,0,-1000);
//                    ScaleAnimation animation2 = new
//                    ScaleAnimation(1.0f,0,1.0f,0,
//                    Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//                    animation2.setDuration(300);
//                    animation1.setDuration(200);
                    animation.setDuration(300);
                    animationset.addAnimation(animation);
//                    animationset.addAnimation(animation1);
//                    animationset.addAnimation(animation2);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            listener.onFragmentInteraction(new WhatDo("remove",0));
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    LinearLayout parent = (LinearLayout) pv.getParent();
                    parent.setBackgroundResource(R.color.touming);
                    pv.startAnimation(animationset);
                    return false;
                }
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    if (pv.getScale()==2f){
                        pv.setScale(1f,true);
                    }else {
                        pv.setScale(2f,true);
                    }
                    return false;
                }
                @Override
                public boolean onDoubleTapEvent(MotionEvent e) {
                    return false;
                }
            });
            container.addView(view);
            return view;
        }
        public MyPagerAdapter(Context mContext, List<Uri> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
    }


}
