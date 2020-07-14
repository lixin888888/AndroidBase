package com.lixin.xinu;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;

import com.gyf.immersionbar.ImmersionBar;
import com.lixin.xinu.fragments.CartFragment;
import com.lixin.xinu.fragments.HomeFragment;
import com.lixin.xinu.fragments.MessageFragment;
import com.lixin.xinu.fragments.MineFragment;
import com.lixin.xinu.interfaces.OnFragmentInteractionListener;
import com.lixin.xinu.beans.TellMainWhat;
import com.lixin.xinu.testEventBus.TestEventbusActivity;

public class MainActivity extends AppCompatActivity  implements OnFragmentInteractionListener, TellMainWhat {
    private android.support.v4.app.Fragment CurrentFragment;
    private LinearLayout tabbarContainer;
    private String lastflag="";
    private FragmentManager fm = getSupportFragmentManager();
    private int[] selects = new int[]{R.drawable.ic_home_fill,R.drawable.ic_community_fill,R.drawable.ic_cart_fill,
            R.drawable.ic_my_fill};
    private int[] noselects = new int[]{R.drawable.ic_home,R.drawable.ic_community,R.drawable.ic_cart,R.drawable.ic_my};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabbarContainer = findViewById(R.id.tabbarContainer);
        initListener(R.id.f_home);
        initListener(R.id.f_message);
        initListener(R.id.f_cart);
        initListener(R.id.f_mine);
        initFragment("home");
        changeIcon("home");
        //初始化沉浸式
        initImmersionBar();
    }

    @Override
    public void onFragmentInteraction(Uri uri) { }

    void initFragment(String tag){
        Fragment object = fm.findFragmentByTag(tag);
        if(object!=null){
            if(object==CurrentFragment){
            //      noting to do
            }else {
                fm.beginTransaction().show(object).hide(CurrentFragment).commitAllowingStateLoss();
                CurrentFragment = object;
            }
        }else {
            android.support.v4.app.Fragment fragment = HomeFragment.newInstance();
            switch (tag){
                case "home":
                    fragment= MessageFragment.newInstance(this.getApplicationContext());
                    break;
                case "message":
                    fragment = HomeFragment.newInstance();
                    break;
                case "cart":
                    fragment = CartFragment.newInstance("q","w");
                    break;
                case "mine":
                    fragment = MineFragment.newInstance("q","w");
                    break;
            }
            if(CurrentFragment==null){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container,fragment,tag).commitAllowingStateLoss();
            }else {
                getSupportFragmentManager().beginTransaction().hide(CurrentFragment).
                        add(R.id.fragment_container,fragment,tag).commitAllowingStateLoss();
            }
            CurrentFragment = fragment;
        }
    }
    void initListener(int id){
        LinearLayout view = (LinearLayout) findViewById(id);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String flag = v.getTag().toString();
                Object tag = v.getTag();
                initFragment(flag);
                changeIcon(flag);
            }
        });
    }
    void changeIcon(String flag){
        if(flag.equals(lastflag)){

        }else {
            switch (flag){
                case "home":
                    ImageView img = findViewById(R.id.home_icon);
                    img.setImageResource(selects[0]);
                    break;
                case "message":
                    ImageView img1 = findViewById(R.id.comment_icon);
                    img1.setImageResource(selects[1]);
                    break;
                case "cart":
                    ImageView img2 = findViewById(R.id.cart_icon);
                    img2.setImageResource(selects[2]);
                    break;
                case "mine":
                    ImageView img3 = findViewById(R.id.mine_icon);
                    img3.setImageResource(selects[3]);
                    break;
            }
            if(lastflag==null){

            }else {
                switch (lastflag){
                    case "home":
                        ImageView img = findViewById(R.id.home_icon);
                        img.setImageResource(noselects[0]);
                        break;
                    case "message":
                        ImageView img1 = findViewById(R.id.comment_icon);
                        img1.setImageResource(noselects[1]);
                        break;
                    case "cart":
                        ImageView img2 = findViewById(R.id.cart_icon);
                        img2.setImageResource(noselects[2]);
                        break;
                    case "mine":
                        ImageView img3 = findViewById(R.id.mine_icon);
                        img3.setImageResource(noselects[3]);
                        break;
                }
            }
            lastflag = flag;
        }
    }
    // fragment与MainActivity交互的接口
    @Override
    public void tellMain(String message) {
        switch (message){
            case "testEventBus":
            startActivity(new Intent(MainActivity.this,TestEventbusActivity.class));
            break;
        }
    }

    static class MainHandler extends Handler {
        WeakReference<MainActivity> mWeakref;
        public MainHandler(WeakReference<MainActivity> mWeakref) {
            this.mWeakref = mWeakref;
        }
        @Override
        public void handleMessage(Message msg) {
            final  MainActivity activity = mWeakref.get();
            if(activity!=null){
                if(msg.what==0x01){

                }
            }
        }
    }

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        //设置共同沉浸式样式
        ImmersionBar.with(this).statusBarColor(R.color.wangyiyun).fitsSystemWindows(true).init();    //状态栏颜色，不写默认透明色.init();
    }

}
