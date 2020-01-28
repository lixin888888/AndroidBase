package com.lixin.xinu.boos;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.lixin.xinu.beans.WhatDo;
import com.lixin.xinu.R;

/**
 * 这个activity 是用来控制所有商家的项目
 */

public class BossEditActivity extends AppCompatActivity implements MyOnFragmentInteractionListener{
    private FragmentManager fm;
    private EditGoodsFragment egf;
    private CreatShopFragment csf;
    private EditAndViewFragment eav;
    // 保存的是裁剪后的地址集合
    private ArrayList<Uri> cropImageAddress= new ArrayList<>(4);
    // 压缩后的文件
    private File compressFile;
    private Toast toast;
    private String[] fileSavePath;
    private String mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.jpeg";
    private WhatDo currentWhatDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InitFilePath();
        csf = CreatShopFragment.newInstance();
        //egf = EditGoodsFragment.newInstance("q","w");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_edit);
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.boss_fragment_container,csf,"shop").commitAllowingStateLoss();
        //fm.beginTransaction().add(R.id.boss_fragment_container,egf,"edit").commitAllowingStateLoss();
    }
    void InitFilePath(){
        fileSavePath = new String[4];
        for (int i = 0; i < 4; i++) {
            fileSavePath[i] = SystemClock.currentThreadTimeMillis()+i+".jpg";
        }
    }
    // 当选择图片的时候 会去调用该方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 23 && resultCode == RESULT_OK) {
            List<Uri> uris = Matisse.obtainResult(data);
            egf.doGalleryWork(uris);
        }
        // 剪切后的处理方式
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            cropImageAddress.add(resultUri);
            // 这里就是裁剪后的图片地址
            egf.addImage(resultUri,cropImageAddress.size()-1);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            // final Throwable cropError = UCrop.getError(data);
        }
    }
    // 需要将两个参数 uri 和 position
    @Override
    public void onFragmentInteraction(WhatDo  wd) {
//        保存当前的状态
        currentWhatDo = wd;
        if(wd.getWantDo().equals("show")){
            fm.beginTransaction().add(R.id.boss_fragment_container,
            ViewImageFragment.newInstance(egf.getImageAddress(),wd.getCurrentPosition()),"vf").commitAllowingStateLoss();
        }else if(wd.getWantDo().equals("remove")) {
            fm.beginTransaction().remove(fm.findFragmentByTag("vf")).commitAllowingStateLoss();
            // 展示两个编辑商品的页面
        }else if(wd.getWantDo().equals("edit")){
            eav = EditAndViewFragment.newInstance("q","w");
            fm.beginTransaction().add(R.id.boss_fragment_container, eav,"editf").commitAllowingStateLoss();
        }else if(wd.getWantDo().equals("goods")){
            egf = EditGoodsFragment.newInstance(eav.getCurrentCatogery(),"w");
            fm.beginTransaction().hide(csf).commitAllowingStateLoss();
            fm.beginTransaction().add(R.id.boss_fragment_container,egf,"goods").commitAllowingStateLoss();
        }else if(wd.getWantDo().equals("backEditGoods")){
            fm.beginTransaction().remove(egf).commitAllowingStateLoss();
            fm.beginTransaction().show(eav).commitAllowingStateLoss();
        }
    }

    // 添加goods 到 另一个页面 让editGoods页面调用
    public void addGoodsInEditAndView(RGoods goods,String catogery){
         eav.addGoods(goods,catogery);
    }
}
