package com.lixin.xinu.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.lixin.xinu.R;

public class DoublelistFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    // List
    private ListView Rlistview;
    private ListView Llistview;

    private ArrayAdapter<String> ladapter;
    private ArrayAdapter<String> radapter;

    private List<String> data;
    private Context mContext;

    private OnFragmentInteractionListener mListener;

    public DoublelistFragment() {
    }

    public static DoublelistFragment newInstance(String param1, String param2) {
        DoublelistFragment fragment = new DoublelistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_doublelist, container, false);
       Llistview = view.findViewById(R.id.l_list);
       Rlistview = view.findViewById(R.id.r_list);
       ladapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,data);
       radapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,data);
       Llistview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
       Llistview.setAdapter(ladapter);
       Rlistview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
       Rlistview.setAdapter(radapter);
       return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void getData() {
        data  =new ArrayList<>();
               data.add("北京");
               data.add("上海");
               data.add("深圳");
               data.add("武汉");
               data.add("宜昌");
               data.add("成都");
               data.add("贵阳");
               data.add("杭州");
               data.add("济南");
               data.add("天津");
             }
    }
