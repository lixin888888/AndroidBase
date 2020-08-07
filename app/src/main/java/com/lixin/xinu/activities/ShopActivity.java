package com.lixin.xinu.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.lixin.xinu.R;
import com.lixin.xinu.fragments.Recycle_doubleFragment;

public class ShopActivity extends AppCompatActivity {
    private FrameLayout container;
    private Recycle_doubleFragment df;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        df = Recycle_doubleFragment.newInstance("q","w");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.shopFragmentContainer,df,"df").commitAllowingStateLoss();

    }
}
