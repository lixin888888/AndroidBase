package com.lixin.xinu.testEventBus;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import com.lixin.xinu.beans.userEvent;
import com.lixin.xinu.R;

public class TestEventbusActivity extends AppCompatActivity implements View.OnClickListener {

    private Button textView;
    private TextView showTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_eventbus);
        textView = findViewById(R.id.lixintestEventBus);
        showTextView = findViewById(R.id.showTextView);
        textView.setOnClickListener(this);
        // 注册订阅者
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        // 注销订阅者
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(TestEventbusActivity.this,TestEventbus02Activity.class));
    }


    // 订阅处理接受的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(userEvent ue){
        showTextView.setText(ue.getUserName());
    }
}

/**
 * EventBus 的四种事件模型
 * 01 posting 该事件在哪个线程发出 事件处理就在那个线程处理 尽量避免事件的耗时操作 因为它会阻塞事件的传递 有可能造成APP无响应
 * 02 main 事件处理在UI线程
 * 03 BACKGROUND 如果事件是在UI线程发出来的 该事件的处理就会在新的线程中运行 如果该事件是在子线程中发出的 那么该事件的处理函数 就在还子线程中处理
 *    但是该子线程禁止UI更新
 * 04 ASYNC 无论事件在那个线程发布 该事件的处理函数都会在新建一个线程中执行
 *
 * ————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 * EventBus的粘性事件
 * 使用postSticky发事件 可以先不用注册 也能接收到事件 这个时间不止消费一次就消失 而是一直存在于系统中 只要在register的时候 就可以被检测到
 * 并且执行 订阅的的方法需要添加sticky = ture 的属性
 *
 */
