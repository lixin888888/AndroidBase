package com.lixin.xinu.customerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.xinu.R;

/**
 * 筛选器
 */
public class FilterLayout extends FrameLayout {


    public TextView filterTextView;
    public ImageView imageView;
    private Context mContext;
    private OnClickListener listener;
    private View mView;


    public FilterLayout(@NonNull Context context) {
        this(context,null);
    }

    public FilterLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FilterLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }



    private void init(Context context, AttributeSet  attrs){
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mView = inflater.inflate(R.layout.filter_view, this, true);

        filterTextView = mView.findViewById(R.id.filter_text);

        TypedArray attr = mContext.obtainStyledAttributes(attrs, R.styleable.FilterLayout);
        String text = attr.getString(R.styleable.FilterLayout_filter_text);
        filterTextView.setText(text);
    }

    public void setOnclick(OnClickListener listener){
        this.listener = listener;
        mView.setOnClickListener(listener);
    }
}
