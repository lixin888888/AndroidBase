package com.lixin.xinu.fragments;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.lixin.xinu.broadcasts.AlarmReceiver;
import com.lixin.xinu.constant.Constants;
import com.lixin.xinu.interfaces.OnFragmentInteractionListener;
import com.lixin.xinu.beans.TellMainWhat;
import com.lixin.xinu.R;
import com.lixin.xinu.utils.AlarmUtil;
import java.util.Calendar;


public class MineFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private Button testEventBus,alarmButton;
    private OnFragmentInteractionListener mListener;
    private TellMainWhat tell;

    public MineFragment() {}

    public static MineFragment newInstance(String param1, String param2) {
        return new MineFragment();
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
                calendar.set(Calendar.HOUR_OF_DAY, 9);//小时
                calendar.set(Calendar.MINUTE, 0);//分钟
                calendar.set(Calendar.SECOND, 0);//秒

                AlarmUtil.sendRepeatAlarmBroadCast(this.getContext(), Constants.Repeat_request_code,
                        AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),100, AlarmReceiver.class);
        }
    }
}
