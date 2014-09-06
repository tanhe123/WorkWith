package com.xiayule.workwithclient.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiayule.workwithclient.R;

public class TrendsFragment extends Fragment {

    public static TrendsFragment newInstance() {
        TrendsFragment fragment = new TrendsFragment();

        return fragment;
    }
    public TrendsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trends, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
