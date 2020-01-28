package com.lixin.xinu.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lixin.xinu.broadcasts.AlarmReceiver;
import com.lixin.xinu.constant.Constants;
import com.lixin.xinu.interfaces.OnFragmentInteractionListener;
import com.lixin.xinu.beans.TellMainWhat;
import com.lixin.xinu.R;
import com.lixin.xinu.services.AlarmIntentService;
import com.lixin.xinu.services.AlarmService;
import com.lixin.xinu.utils.AlarmUtil;

import java.util.Calendar;


public class MineFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context mContext;
    private Button testEventBus,alarmButton;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TellMainWhat tell;

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        testEventBus = view.findViewById(R.id.testEventBus);
        testEventBus.setOnClickListener(this);
        alarmButton = view.findViewById(R.id.testAlarm);
        alarmButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        tell = (TellMainWhat) context;
        //        if (context instanceof OnFragmentInteractionListener) {
        //            mListener = (OnFragmentInteractionListener) context;
        //        } else {
        //            throw new RuntimeException(context.toString()
        //                    + " must implement OnFragmentInteractionListener");
        //        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.testEventBus:
                tell.tellMain("testEventBus");
                break;
            case R.id.testAlarm:
                // 开启闹钟服务
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 21);//小时
                calendar.set(Calendar.MINUTE, 27);//分钟
                calendar.set(Calendar.SECOND, 0);//秒

                AlarmUtil.sendRepeatAlarmBroadCast(this.getContext(), Constants.Repeat_request_code,
                        AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),100, AlarmReceiver.class);
        }
    }
}