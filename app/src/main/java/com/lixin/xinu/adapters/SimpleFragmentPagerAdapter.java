package com.lixin.xinu.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lixin.xinu.Fragments.PagerFragment;

/**
 * 数据转换成fragment的适配器
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private static  final String[] mTitles = {"美食","茶饮","鲜果","美发","电脑","手机","汽车","建材","男装","女装","母婴","家装"};

    public SimpleFragmentPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.mContext = context;
    }

    // 这个是一个回调方法 当TableLayout 改变时，去生成不同的fragment
    @Override
    public Fragment getItem(int i) {
        return PagerFragment.newInstance(i+1,"l");
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    // 当屏幕旋转或者配置改变时需要保存当前的状态
}
