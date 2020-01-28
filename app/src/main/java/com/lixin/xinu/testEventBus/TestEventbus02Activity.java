
package com.lixin.xinu.testEventBus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import com.lixin.xinu.beans.userEvent;
import com.lixin.xinu.R;

public class TestEventbus02Activity extends AppCompatActivity  implements View.OnClickListener {

    private Button sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_eventbus02);
        sendMessage = findViewById(R.id.postEvent);
        sendMessage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.postEvent:
                EventBus.getDefault().post(new userEvent("lixin","188"));
                break;
        }
    }
}
